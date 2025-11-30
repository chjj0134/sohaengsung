package com.example.sohaengsung.ui.features.mapPathRecommend

import com.example.sohaengsung.data.model.Place

data class MapPathRecommendScreenUiState(
    val currentLocation: Pair<Double, Double>? = null, // (latitude, longitude)
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

