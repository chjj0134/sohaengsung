package com.example.sohaengsung

import androidx.compose.runtime.Composable
import com.example.sohaengsung.ui.navigation.AppNavigation

@Composable
fun SohaengsungRoot(
    startGoogleLogin: () -> Unit,
    setNavCallback: ((() -> Unit) -> Unit)
) {
    AppNavigation(
        startGoogleLogin = startGoogleLogin,
        setNavCallback = setNavCallback
    )
}
