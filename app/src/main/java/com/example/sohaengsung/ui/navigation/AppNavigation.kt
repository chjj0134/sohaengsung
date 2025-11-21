package com.example.sohaengsung.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sohaengsung.ui.screens.LogInScreen
import com.example.sohaengsung.ui.screens.MapScreen
import com.example.sohaengsung.ui.screens.PathRecommendScreen
import com.example.sohaengsung.ui.screens.PlaceRecommendScreen
import com.example.sohaengsung.ui.screens.SettingScreen

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
        navController.navigate(ScreenRoute.MAP) {
            popUpTo(ScreenRoute.LOGIN) { inclusive = true }
        }
    }

    NavHost(navController = navController, startDestination = ScreenRoute.LOGIN) {
        composable(ScreenRoute.LOGIN) { LogInScreen() }
        composable(ScreenRoute.PLACE_RECOMMEND) { PlaceRecommendScreen() }
        composable(ScreenRoute.PATH_RECOMMEND) { PathRecommendScreen() }
        composable(ScreenRoute.SETTING) { SettingScreen() }
        composable(ScreenRoute.MAP) { MapScreen() }
    }
}