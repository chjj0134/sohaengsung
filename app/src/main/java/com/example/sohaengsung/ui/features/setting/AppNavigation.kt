package com.example.sohaengsung.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavigation(
    startGoogleLogin: () -> Unit,
    startKakaoLogin: () -> Unit,
    loginSuccess: Boolean
) {
    val navController = rememberNavController()

    // 로그인 성공하면 map으로 이동
    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            navController.navigate("map") {
                popUpTo("login") { inclusive = true }
            }
        }
    }

    NavHost(navController = navController, startDestination = "login") {

        composable("login") {
            LogInScreen(
                onClickGoogleLogin = startGoogleLogin,
                onClickKakaoLogin = startKakaoLogin
            )
        }

        composable("place-recommend") { PlaceRecommendScreen() }
        composable("path-recommend") { PathRecommendScreen() }
        composable("setting") { SettingScreen() }
        composable("map") { MapScreen() }
    }
}
