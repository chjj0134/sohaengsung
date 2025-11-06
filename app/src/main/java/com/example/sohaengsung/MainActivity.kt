package com.example.sohaengsung

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import com.naver.maps.map.compose.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NaverMapScreen()
        }
    }
}
@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun NaverMapScreen() {
    NaverMap(
        properties = MapProperties(
            mapType = MapType.Basic
        ),
        uiSettings = MapUiSettings(isLocationButtonEnabled = true)
    )
}
