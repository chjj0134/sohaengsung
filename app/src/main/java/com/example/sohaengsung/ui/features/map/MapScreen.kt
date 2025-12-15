package com.example.sohaengsung.ui.features.map

import PlaceMarker
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.example.sohaengsung.data.model.Place
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.rememberCameraPositionState

@SuppressLint("MissingPermission")
@Composable
fun MapScreen(
    latitude: Double = 37.5459,
    longitude: Double = 126.9649,
    zoom: Float = 15f,
    places: List<Place> = emptyList(), // 필터링된 장소 리스트
    onMarkerClick: (Place) -> Unit = {}
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
        cameraPositionState = cameraPositionState,
        uiSettings = MapUiSettings(zoomControlsEnabled = false)
    ) {
        // 리스트에 있는 장소들을 지도에 표시
        places.forEach { place ->
            PlaceMarker(
                place = place,
                onClick = onMarkerClick
            )
        }
    }
}