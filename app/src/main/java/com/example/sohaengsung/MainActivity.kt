package com.example.sohaengsung

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.sohaengsung.data.model.User
import com.example.sohaengsung.data.repository.PlaceRepository
import com.example.sohaengsung.data.repository.UserRepository
import com.example.sohaengsung.data.util.LocationService
import com.example.sohaengsung.ui.features.placeRecommend.PlaceRecommendViewModel
import com.example.sohaengsung.ui.features.placeRecommend.PlaceRecommendViewModelFactory
import com.example.sohaengsung.ui.theme.SohaengsungTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth

    private lateinit var googleSignInClient: GoogleSignInClient

    private val userRepository = UserRepository()

    private var loginSuccess by mutableStateOf(false)

    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

            try {
                val account = task.getResult(ApiException::class.java) ?: return@registerForActivityResult

                val credential = GoogleAuthProvider.getCredential(account.idToken, null)

                auth.signInWithCredential(credential)
                    .addOnSuccessListener { authResult ->
                        val firebaseUser = authResult.user ?: return@addOnSuccessListener

                        val uid = firebaseUser.uid
                        val nickname = firebaseUser.displayName
                        val profilePic = firebaseUser.photoUrl?.toString()

                        saveUserToFirestore(
                            uid = uid,
                            nickname = nickname,
                            profilePic = profilePic
                        )
                    }
                    .addOnFailureListener {
                        // TODO: 필요하면 에러 처리 UI 연동
                    }

            } catch (_: ApiException) {
                // TODO: 필요하면 에러 처리
            }
        }

    private fun startKakaoLogin() {
        val context = this

        UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
            if (error != null || token == null) {

                UserApiClient.instance.loginWithKakaoAccount(context) { token2, error2 ->
                    if (error2 == null && token2 != null) {
                        onKakaoLoginSuccess()
                    }
                }
            } else {
                onKakaoLoginSuccess()
            }
        }
    }

    private fun onKakaoLoginSuccess() {
        UserApiClient.instance.me { user, error ->
            if (error != null || user == null) return@me

            val uid = user.id.toString()
            val nickname = user.kakaoAccount?.profile?.nickname
            val profilePic = user.kakaoAccount?.profile?.profileImageUrl

            saveUserToFirestore(
                uid = uid,
                nickname = nickname,
                profilePic = profilePic
            )
        }
    }

    private fun saveUserToFirestore(
        uid: String,
        nickname: String?,
        profilePic: String?
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = User(
                uid = uid,
                nickname = nickname ?: "Guest",   // 닉네임 없으면 Guest
                profilePic = profilePic
            )

            userRepository.createUserIfNotExists(user)

            withContext(Dispatchers.Main) {
                loginSuccess = true
            }
        }
    }

    private val placeRepository = PlaceRepository()
    private val locationService: LocationService? = null

    private val currentFirebaseUser = FirebaseAuth.getInstance().currentUser
    private val userId: String = currentFirebaseUser?.uid ?: "guest"

    private val placeRecommendViewModel: PlaceRecommendViewModel by viewModels {
        PlaceRecommendViewModelFactory(
            uid = userId,
            placeRepository = placeRepository,
            locationService = locationService
        )
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
                    loginSuccess = loginSuccess,
                    placeRecommendViewModel
                )
            }
        }
    }
}
