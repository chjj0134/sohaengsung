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
import com.example.sohaengsung.ui.features.pathRecommend.PathRecommendViewModel
import com.example.sohaengsung.ui.features.pathRecommend.PathRecommendViewModelFactory
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
    private var loggedInUid by mutableStateOf<String?>(null)

    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)

            try {
                val account = task.getResult(ApiException::class.java) ?: return@registerForActivityResult
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)

                auth.signInWithCredential(credential)
                    .addOnSuccessListener { authResult ->
                        val firebaseUser = authResult.user ?: return@addOnSuccessListener

                        saveUserToFirestore(
                            uid = firebaseUser.uid,
                            nickname = firebaseUser.displayName,
                            profilePic = firebaseUser.photoUrl?.toString()
                        )
                    }

            } catch (_: ApiException) {
            }
        }

    private fun startKakaoLogin() {
        UserApiClient.instance.loginWithKakaoAccount(this) { _, error ->
            if (error != null) return@loginWithKakaoAccount
            fetchKakaoUser()
        }
    }

    private fun fetchKakaoUser() {
        UserApiClient.instance.me { user, error ->
            if (error != null || user == null) return@me

            saveUserToFirestore(
                uid = "kakao_${user.id}",
                nickname = user.kakaoAccount?.profile?.nickname,
                profilePic = user.kakaoAccount?.profile?.profileImageUrl
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
                nickname = nickname ?: "Guest",
                profilePic = profilePic
            )

            userRepository.createUserIfNotExists(user)

            withContext(Dispatchers.Main) {
                loggedInUid = uid
                loginSuccess = true
            }
        }
    }

    private val placeRepository = PlaceRepository()
    private val locationService: LocationService? = null

    private val placeRecommendViewModel: PlaceRecommendViewModel by viewModels {
        PlaceRecommendViewModelFactory(
            uid = FirebaseAuth.getInstance().currentUser?.uid ?: "guest",
            placeRepository = placeRepository,
            locationService = locationService
        )
    }

    private val pathRecommendViewModel: PathRecommendViewModel by viewModels {
        PathRecommendViewModelFactory(
            uid = FirebaseAuth.getInstance().currentUser?.uid ?: "guest"
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
                    loggedInUid = loggedInUid,
                    placeRecommendViewModel = placeRecommendViewModel,
                    pathRecommendViewModel = pathRecommendViewModel
                )
            }
        }
    }
}
