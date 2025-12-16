package com.example.sohaengsung

import androidx.compose.runtime.Composable
import com.example.sohaengsung.ui.features.pathRecommend.PathRecommendViewModel
import com.example.sohaengsung.ui.features.placeRecommend.PlaceRecommendViewModel
import com.example.sohaengsung.ui.screens.AppNavigation

@Composable
fun SohaengsungRoot(
    startGoogleLogin: () -> Unit,
    startKakaoLogin: () -> Unit,
    loginSuccess: Boolean,
    loggedInUid: String?,
    placeRecommendViewModel: PlaceRecommendViewModel,
    pathRecommendViewModel: PathRecommendViewModel
) {
    AppNavigation(
        startGoogleLogin = startGoogleLogin,
        startKakaoLogin = startKakaoLogin,
        loginSuccess = loginSuccess,
        loggedInUid = loggedInUid,
        placeRecommendViewModel = placeRecommendViewModel,
        pathRecommendViewModel = pathRecommendViewModel
    )
}
