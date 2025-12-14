package com.example.sohaengsung.ui.features.bookmarked

import com.example.sohaengsung.data.model.User
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.ui.features.pathRecommend.PlaceWithDistance
import com.example.sohaengsung.ui.features.placeRecommend.PlaceRecommendScreenEvent

data class BookmarkedScreenUiState(
    val place: List<Place> = listOf(),
    val user: User = User(),
    val bookmarkedPlaces: List<PlaceWithDistance> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

sealed class BookmarkScreenEvent {
    data class onDropDownClick(val sortCriteria: String) : BookmarkScreenEvent()

    // 필터링 드롭다운 로직 -> (완료)
    data class onDeleteClick(val placeWithDistance: PlaceWithDistance) : BookmarkScreenEvent()

    sealed class Navigation : BookmarkScreenEvent() {

    }
}