package com.example.sohaengsung

import androidx.compose.runtime.Composable
import com.example.sohaengsung.ui.screens.AppNavigation

@Composable
fun SohaengsungRoot(
    startGoogleLogin: () -> Unit,
    startKakaoLogin: () -> Unit,
    loginSuccess: Boolean
) {
    AppNavigation(
        startGoogleLogin = startGoogleLogin,
        startKakaoLogin = startKakaoLogin,
        loginSuccess = loginSuccess
    )
}
