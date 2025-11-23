package com.example.sohaengsung.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sohaengsung.ui.features.coupon.CouponScreen
import com.example.sohaengsung.ui.features.coupon.CouponScreenEvent
import com.example.sohaengsung.ui.features.event.EventScreen
import com.example.sohaengsung.ui.features.logIn.LogInScreen
import com.example.sohaengsung.ui.features.map.MapScreen
import com.example.sohaengsung.ui.features.pathRecommend.PathRecommendScreen
import com.example.sohaengsung.ui.features.placeRecommend.PlaceRecommendScreen
import com.example.sohaengsung.ui.features.setting.SettingScreen
import com.example.sohaengsung.ui.navigation.ScreenRoute

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
        composable("coupon") {
            CouponScreen(
                onNavigate = { navigationEvent ->
                    val route = when (navigationEvent) {
                        CouponScreenEvent.Navigation.NavigateToVoucherScreen ->  ScreenRoute.VOUCHER // 가정
                    }
                }
            )
        }
        composable ("event") { EventScreen() }
    }
}
