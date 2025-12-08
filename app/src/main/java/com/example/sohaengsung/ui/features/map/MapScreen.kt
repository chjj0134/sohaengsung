package com.example.sohaengsung.ui.features.map

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

@SuppressLint("MissingPermission")
@Composable
fun MapScreen(
    latitude: Double = 37.5665,
    longitude: Double = 126.9780,
    zoom: Float = 15f
) {
    val targetLocation = remember(latitude, longitude) {
        LatLng(latitude, longitude)
    }

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(targetLocation, zoom)
    }

    LaunchedEffect(targetLocation) {
        if (cameraPositionState.position.target != targetLocation) {
            cameraPositionState.animate(
                update = CameraUpdateFactory.newLatLngZoom(targetLocation, zoom),
                durationMs = 800
            )
        }
    }

    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    )
}

//@SuppressLint("MissingPermission")
//@Composable
//fun MapScreen(
//    latitude: Double = 37.5665,
//    longitude: Double = 126.9780,
//    zoom: Float = 15f
//) {
//    val startLocation = LatLng(latitude, longitude)
//
//    val cameraPositionState = rememberCameraPositionState {
//        position = CameraPosition.fromLatLngZoom(startLocation, zoom)
//    }
//
//    GoogleMap(
//        modifier = Modifier.fillMaxSize(),
//        cameraPositionState = cameraPositionState
//    )
//}