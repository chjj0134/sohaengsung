package com.example.sohaengsung.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.sohaengsung.ui.features.coupon.CouponScreen
import com.example.sohaengsung.ui.features.coupon.CouponScreenEvent
import com.example.sohaengsung.ui.features.logIn.LogInScreen
import com.example.sohaengsung.ui.features.logIn.LogInScreenEvent
import com.example.sohaengsung.ui.features.map.MapScreen
import com.example.sohaengsung.ui.features.pathRecommend.PathRecommendScreen
import com.example.sohaengsung.ui.features.placeRecommend.PlaceRecommendScreen
import com.example.sohaengsung.ui.features.setting.SettingScreen

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

    NavHost(navController = navController, startDestination = ScreenRoute.COUPON) {
        composable(ScreenRoute.LOGIN) { LogInScreen(
            onNavigate = { navigationEvent ->
                val route = when (navigationEvent) {
                    LogInScreenEvent.Navigation.NavigateToEmailLogin -> ScreenRoute.EMAIL_LOGIN // 가정
                    LogInScreenEvent.Navigation.NavigateToKakaoLogin -> ScreenRoute.KAKAO_LOGIN // 가정
                    LogInScreenEvent.Navigation.NavigateToSignUp -> ScreenRoute.SIGN_UP // 가정
                    LogInScreenEvent.Navigation.NavigateToHome -> ScreenRoute.PLACE_RECOMMEND // 홈(메인) 화면으로 이동
                }

                navController.navigate(route) {
                    // 로그인 후 뒤로 가기로 돌아오지 않도록 스택에서 제거함
                    if (navigationEvent is LogInScreenEvent.Navigation.NavigateToHome) {
                        popUpTo(ScreenRoute.LOGIN) { inclusive = true }
                    }
                }
            }
        ) }
        composable(ScreenRoute.COUPON) {
            CouponScreen(
                onNavigate = { navigationEvent ->
                    val route = when (navigationEvent) {
                        CouponScreenEvent.Navigation.NavigateToVoucherScreen ->  ScreenRoute.VOUCHER // 가정
                    }
                }
            )
        }
        composable(ScreenRoute.PLACE_RECOMMEND) { PlaceRecommendScreen() }
        composable(ScreenRoute.PATH_RECOMMEND) { PathRecommendScreen() }
        composable(ScreenRoute.SETTING) { SettingScreen() }
        composable(ScreenRoute.MAP) { MapScreen() }
    }
}