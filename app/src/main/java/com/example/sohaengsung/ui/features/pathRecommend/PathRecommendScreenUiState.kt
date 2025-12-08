package com.example.sohaengsung.ui.features.pathRecommend

import com.example.sohaengsung.data.model.Hashtag
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.ui.features.placeRecommend.PlaceRecommendScreenEvent.Navigation

data class PathRecommendScreenUiState(
    val place: List<Place> = listOf(),
    val hashtag: List<Hashtag> = listOf(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val selectedPlaceId: String? = null,
)

sealed class PathRecommendScreenEvent {
    data class onCheckboxClick(val place: Place) : PathRecommendScreenEvent()
    data class onDropDownClick(val sortCriteria: String) : PathRecommendScreenEvent()
    object onPathComposeClick : PathRecommendScreenEvent()

    sealed class Navigation : PathRecommendScreenEvent() {
        data class NavigateToPathCompose(val placeIds: List<String>) : Navigation()
    }
}