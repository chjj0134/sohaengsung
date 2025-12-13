package com.example.sohaengsung.ui.features.bookmarked

import com.example.sohaengsung.data.model.User
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.ui.features.pathRecommend.PlaceWithDistance

data class BookmarkedScreenUiState(
    val place: List<Place> = listOf(),
    val user: User = User(),
    val bookmarkedPlaces: List<PlaceWithDistance> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

sealed class BookmarkScreenEvent {
    data class onDeleteClick(val placeWithDistance: PlaceWithDistance) : BookmarkScreenEvent()

    sealed class Navigation : BookmarkScreenEvent() {

    }
}