package com.example.sohaengsung.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import com.example.sohaengsung.ui.components.common.CustomTopBar
import com.example.sohaengsung.ui.theme.SohaengsungTheme
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng

@Preview
@Composable
fun MapScreen() {
    SohaengsungTheme {
        Scaffold(
            topBar = {
                CustomTopBar(contentText = "지도")
            }
        ) { innerPadding ->
            val startLocation = LatLng(37.5665, 126.9780) // 서울 기본 좌표
            val cameraPositionState = rememberCameraPositionState {
                position = CameraPosition.fromLatLngZoom(startLocation, 15f)
            }

            GoogleMap(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                cameraPositionState = cameraPositionState
            )
        }
    }
}
