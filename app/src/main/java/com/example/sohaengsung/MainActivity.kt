package com.example.sohaengsung

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.sohaengsung.ui.theme.SohaengsungTheme

import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

import com.kakao.sdk.user.UserApiClient
import com.example.sohaengsung.data.model.User
import com.example.sohaengsung.data.repository.UserRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    // Firestore 저장용 repo
    private val userRepository = UserRepository()

    // 로그인 성공 여부 → true면 MapScreen으로 이동
    private var loginSuccess by mutableStateOf(false)

    // 구글 로그인 Launcher
    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)

                auth.signInWithCredential(credential)
                    .addOnSuccessListener {
                        val uid = auth.currentUser?.uid ?: return@addOnSuccessListener
                        saveUserToFirestore(uid)
                    }

            } catch (_: ApiException) { }
        }

    // 카카오 로그인 처리
    private fun startKakaoLogin() {
        val context = this

        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null) {
                UserApiClient.instance.loginWithKakaoAccount(context) { token2, _ ->
                    if (token2 != null) {
                        onKakaoLoginSuccess()
                    }
                }
            } else if (token != null) {
                onKakaoLoginSuccess()
            }
        }
    }

    private fun onKakaoLoginSuccess() {
        val uid = "kakao_" + System.currentTimeMillis()
        saveUserToFirestore(uid)
    }

    // Firestore 저장 처리
    private fun saveUserToFirestore(uid: String) {
        CoroutineScope(Dispatchers.IO).launch {

            val user = User(
                uid = uid,
                nickname = "Guest",
                profilePic = null
            )

            userRepository.createUserIfNotExists(user)
            loginSuccess = true
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        setContent {
            SohaengsungTheme {
                SohaengsungRoot(
                    startGoogleLogin = {
                        googleSignInLauncher.launch(googleSignInClient.signInIntent)
                    },
                    startKakaoLogin = {
                        startKakaoLogin()
                    },
                    loginSuccess = loginSuccess
                )
            }
        }
    }
}
