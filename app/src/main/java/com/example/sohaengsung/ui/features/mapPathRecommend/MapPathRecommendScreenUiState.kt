package com.example.sohaengsung.ui.features.mapPathRecommend

import com.example.sohaengsung.data.model.Place

// 위치 정보를 가져올 수 없을 때 사용할 기본 좌표
val DEFAULT_LOCATION = Pair(37.5459, 126.9649) // (latitude, longitude)

data class MapPathRecommendScreenUiState(
    val currentLocation: Pair<Double, Double>? = DEFAULT_LOCATION, // (latitude, longitude)
    val sortedPlaces: List<PlaceWithOrder> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

/**
 * 경로에 포함된 장소와 순서 정보
 */
data class PlaceWithOrder(
    val place: Place,
    val order: Int, // 1, 2, 3...
    val distanceFromPrevious: Double? = null // 이전 장소로부터의 거리 (미터)
)

