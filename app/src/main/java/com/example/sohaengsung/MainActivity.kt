package com.example.sohaengsung

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.*
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.sohaengsung.ui.screens.HomeScreen
import com.example.sohaengsung.ui.screens.LogInScreen
import com.example.sohaengsung.ui.screens.MapScreen
import com.example.sohaengsung.ui.screens.PathRecommendScreen
import com.example.sohaengsung.ui.screens.PlaceRecommendScreen
import com.example.sohaengsung.ui.screens.SettingScreen
import com.example.sohaengsung.ui.theme.SohaengsungTheme
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : ComponentActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient

    private val googleSignInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                val credential = GoogleAuthProvider.getCredential(account.idToken, null)
                auth.signInWithCredential(credential).addOnSuccessListener {
                    navigateToMap?.invoke()
                }
            } catch (_: ApiException) { }
        }

    private var navigateToMap: (() -> Unit)? = null

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
                AppNavigation(
                    startGoogleLogin = {
                        googleSignInLauncher.launch(googleSignInClient.signInIntent)
                    },
                    setNavCallback = { callback ->
                        navigateToMap = callback
                    }
                )
            }
        }
    }
}

@Composable
fun AppNavigation(
    startGoogleLogin: () -> Unit,
    setNavCallback: ((() -> Unit) -> Unit)
) {
    val navController = rememberNavController()

    LaunchedEffect(Unit) {
        startGoogleLogin()
    }

    setNavCallback {
        navController.navigate("map") {
            popUpTo("login") { inclusive = true }
        }
    }

    NavHost(navController = navController, startDestination = "home") {
        composable("login") { LogInScreen() }
        composable("home") {
            HomeScreen(
                onNavigateToPlaceRecommend = {
                    navController.navigate("place-recommend")
                },
                onNavigateToPathRecommend = {
                    navController.navigate("path-recommend")
                },
                onNavigateToBookmark = {
                    navController.navigate("place-recommend") // 북마크는 장소 추천 화면으로
                },
                onNavigateToCoupon = {
                    navController.navigate("coupon")
                },
                onNavigateToEvent = {
                    navController.navigate("event")
                },
                onNavigateToSetting = {
                    navController.navigate("setting")
                }
            )
        }
        composable("place-recommend") { PlaceRecommendScreen() }
        composable("path-recommend") { PathRecommendScreen() }
        composable("setting") { SettingScreen() }
        composable("map") { MapScreen() }
        // TODO: 추후 구현 예정
        composable("coupon") { 
            // CouponScreen() - 추후 구현
            PlaceRecommendScreen() // 임시
        }
        composable("event") { 
            // EventScreen() - 추후 구현
            PlaceRecommendScreen() // 임시
        }
    }
}
