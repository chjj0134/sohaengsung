package com.example.sohaengsung.ui.features.mapPathRecommend.components

import android.annotation.SuppressLint
import android.graphics.Color as AndroidColor
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color as ComposeColor
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.ui.theme.RouteLineBlack
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.Dash
import com.google.android.gms.maps.model.Gap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PatternItem
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@SuppressLint("MissingPermission")
@Composable
fun PathMapView(
    currentLocation: Pair<Double, Double>?,
    sortedPlaces: List<Place>,
    modifier: Modifier = Modifier
) {
    if (currentLocation == null || sortedPlaces.isEmpty()) {
        // 기본 위치 (서울)
        val defaultLocation = LatLng(37.5665, 126.9780)
        val cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(defaultLocation, 15f)
        }

        GoogleMap(
            modifier = modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        )
        return
    }

    val startLocation = LatLng(currentLocation.first, currentLocation.second)

    // 카메라 위치 계산 (모든 마커를 포함하도록)
    val allPoints = listOf(startLocation) + sortedPlaces.map { place ->
        LatLng(place.latitude, place.longitude)
    }

    val bounds = calculateBounds(allPoints)
    val centerLat = (bounds.first.first + bounds.second.first) / 2
    val centerLon = (bounds.first.second + bounds.second.second) / 2
    val centerLocation = LatLng(centerLat, centerLon)

    // 줌 레벨 계산
    val zoom = calculateZoomLevel(bounds)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(centerLocation, zoom)
    }

    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
    ) {
        // 경로 점선 (현재 위치 → 1번 → 2번 → 3번)
        val pathPoints = listOf(startLocation) + sortedPlaces.map { place ->
            LatLng(place.latitude, place.longitude)
        }

        if (pathPoints.size > 1) {
            // RouteLineBlack을 그대로 사용 (이미 androidx.compose.ui.graphics.Color 타입)
            Polyline(
                points = pathPoints,
                color = RouteLineBlack,  // Theme의 색상 사용
                width = 5f,
                pattern = listOf<PatternItem>(
                    Dash(20f),
                    Gap(10f)
                )
            )
        }

        // 현재 위치 마커 (선택 사항)
        // Marker(
        //     state = rememberMarkerState(position = startLocation),
        //     title = "현재 위치"
        // )

        // 장소 마커들 (번호 포함)
        sortedPlaces.forEachIndexed { index, place ->
            val markerPosition = LatLng(place.latitude, place.longitude)
            Marker(
                state = rememberMarkerState(position = markerPosition),
                title = "${index + 1}. ${place.name}",
                snippet = place.address
            )
        }
    }
}

/**
 * 모든 포인트를 포함하는 경계 계산
 */
private fun calculateBounds(points: List<LatLng>): Pair<Pair<Double, Double>, Pair<Double, Double>> {
    if (points.isEmpty()) {
        return Pair(Pair(37.5665, 126.9780), Pair(37.5665, 126.9780))
    }

    var minLat = points[0].latitude
    var maxLat = points[0].latitude
    var minLon = points[0].longitude
    var maxLon = points[0].longitude

    points.forEach { point ->
        minLat = minOf(minLat, point.latitude)
        maxLat = maxOf(maxLat, point.latitude)
        minLon = minOf(minLon, point.longitude)
        maxLon = maxOf(maxLon, point.longitude)
    }

    return Pair(Pair(minLat, minLon), Pair(maxLat, maxLon))
}

/**
 * 경계에 맞는 줌 레벨 계산
 */
private fun calculateZoomLevel(
    bounds: Pair<Pair<Double, Double>, Pair<Double, Double>>
): Float {
    val latDiff = bounds.second.first - bounds.first.first
    val lonDiff = bounds.second.second - bounds.first.second
    val maxDiff = maxOf(latDiff, lonDiff)

    return when {
        maxDiff > 0.1 -> 10f
        maxDiff > 0.05 -> 12f
        maxDiff > 0.01 -> 14f
        maxDiff > 0.005 -> 15f
        else -> 16f
    }
}

