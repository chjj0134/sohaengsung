package com.example.sohaengsung.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sohaengsung.ui.features.bookmarked.BookmarkScreenEvent
import com.example.sohaengsung.ui.features.bookmarked.BookmarkedScreen
import com.example.sohaengsung.ui.features.coupon.CouponScreen
import com.example.sohaengsung.ui.features.coupon.CouponScreenEvent
import com.example.sohaengsung.ui.features.event.EventScreen
import com.example.sohaengsung.ui.features.home.HomeScreen
import com.example.sohaengsung.ui.features.home.HomeScreenEvent
import com.example.sohaengsung.ui.features.level.LevelScreen
import com.example.sohaengsung.ui.features.level.LevelScreenEvent
import com.example.sohaengsung.ui.features.logIn.LogInScreen
import com.example.sohaengsung.ui.features.map.MapScreen
import com.example.sohaengsung.ui.features.mapPathRecommend.MapPathRecommendScreen
import com.example.sohaengsung.ui.features.pathRecommend.PathRecommendScreen
import com.example.sohaengsung.ui.features.pathRecommend.PathRecommendScreenEvent
import com.example.sohaengsung.ui.features.pathRecommend.PathRecommendViewModel
import com.example.sohaengsung.ui.features.placeRecommend.PlaceRecommendScreen
import com.example.sohaengsung.ui.features.placeRecommend.PlaceRecommendScreenEvent
import com.example.sohaengsung.ui.features.placeRecommend.PlaceRecommendViewModel
import com.example.sohaengsung.ui.features.review.ReviewScreen
import com.example.sohaengsung.ui.features.review.ReviewScreenEvent
import com.example.sohaengsung.ui.features.setting.SettingScreen
import com.example.sohaengsung.ui.features.setting.SettingScreenEvent
import com.example.sohaengsung.ui.navigation.ScreenRoute

@Composable
fun AppNavigation(
    startGoogleLogin: () -> Unit,
    startKakaoLogin: () -> Unit,
    loginSuccess: Boolean,
    loggedInUid: String?,
    placeRecommendViewModel: PlaceRecommendViewModel,
    pathRecommendViewModel: PathRecommendViewModel
) {
    val navController = rememberNavController()

    // 로그인 성공하면 home으로 이동
    LaunchedEffect(loginSuccess) {
        if (loginSuccess) {
            navController.navigate("home") {
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

        composable("home") {
            HomeScreen(
                onNavigate = { navigationEvent ->
                    val route = when (navigationEvent) {
                        HomeScreenEvent.Navigation.NavigateToPlaceRecommend
                            -> ScreenRoute.PLACE_RECOMMEND

                        HomeScreenEvent.Navigation.NavigateToPathRecommend
                            -> ScreenRoute.PATH_RECOMMEND

                        HomeScreenEvent.Navigation.NavigateToBookmark
                            -> ScreenRoute.BOOKMARK

                        HomeScreenEvent.Navigation.NavigateToCoupon
                            -> ScreenRoute.COUPON

                        HomeScreenEvent.Navigation.NavigateToEvent
                            -> ScreenRoute.EVENT

                        HomeScreenEvent.Navigation.NavigateToSetting
                            -> ScreenRoute.SETTING
                    }
                    navController.navigate(route)
                }
            )
        }

        // 해시태그 관련 로직 제외 완료
        composable("place-recommend") {
            PlaceRecommendScreen(
                viewModel = placeRecommendViewModel,
                onNavigate = { navigationEvent ->
                    when (navigationEvent) {
                        is PlaceRecommendScreenEvent.Navigation.NavigateToReview -> {
                            navController.navigate("${ScreenRoute.REVIEW}/${navigationEvent.placeId}")
                        }

                        else -> {

                        }
                    }
                }
            )
        }

        // 완료
        composable("path-recommend") {
            PathRecommendScreen(
                viewModel = pathRecommendViewModel,
                onNavigate = { navigationEvent ->
                    val route = when (navigationEvent) {
                        is PathRecommendScreenEvent.Navigation.NavigateToPathCompose -> {
                            val idString = navigationEvent.placeIds.joinToString(separator = ",")
                            "${ScreenRoute.MAP_PATH_RECOMMEND}/$idString"
                        }
                    }
                    navController.navigate(route)
                }
            )
        }

        composable("bookmark") {
            BookmarkedScreen(
                onNavigate = { navigationEvent ->
                    when (navigationEvent) {
                        // 향후 로직 추가 시 여기에 작성
                        else -> {

                        }
                    }
                }
            )
        }

        composable("setting") {
            SettingScreen(
                uid = loggedInUid ?: "guest",
                onNavigate = { navigationEvent ->
                    val route = when (navigationEvent) {
                        SettingScreenEvent.Navigation.NavigateToLevel
                            -> ScreenRoute.LEVEL

                        SettingScreenEvent.Navigation.NavigateToAccountManagement
                            -> ScreenRoute.ACCOUNT_MANAGEMENT

                        SettingScreenEvent.Navigation.NavigateToThemeChange
                            -> ScreenRoute.THEME_CHANGE

                        SettingScreenEvent.Navigation.NavigateToTerms
                            -> ScreenRoute.TERMS

                        SettingScreenEvent.Navigation.NavigateToNotice
                            -> ScreenRoute.NOTICE
                    }
                    navController.navigate(route)
                }
            )
        }

        composable("level") {
            LevelScreen(
                onNavigate = { navigationEvent ->
                    val route = when (navigationEvent) {
                        LevelScreenEvent.Navigation.NavigateToLevelInfo
                            -> ScreenRoute.LEVEL_INFO
                    }
                    navController.navigate(route)
                }
            )
        }

        composable("coupon") {
            CouponScreen(
                onNavigate = { navigationEvent ->
                    val route = when (navigationEvent) {
                        CouponScreenEvent.Navigation.NavigateToVoucherScreen
                            -> ScreenRoute.VOUCHER
                    }
                    navController.navigate(route)
                }
            )
        }

        composable("event") { EventScreen() }

        composable("map") { MapScreen() }

        composable("map-path-recommend/{placeIds}") { backStackEntry ->
            val placeIdsString = backStackEntry.arguments?.getString("placeIds") ?: ""
            val placeIds = if (placeIdsString.isNotBlank()) {
                placeIdsString.split(",").filter { it.isNotBlank() }
            } else {
                emptyList()
            }
            MapPathRecommendScreen(placeIds = placeIds)
        }

        composable(
            route = "${ScreenRoute.REVIEW}/{placeId}", // "review/{placeId}" 형태가 됩니다.
            arguments = listOf(
                navArgument("placeId") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            // 2. ReviewScreen 호출
            ReviewScreen(
                onNavigate = { navigationEvent ->
                    when (navigationEvent) {
                        ReviewScreenEvent.Navigation.NavigateBack -> {
                            navController.popBackStack()
                        }
                    }
                }
            )
        }
    }
}
