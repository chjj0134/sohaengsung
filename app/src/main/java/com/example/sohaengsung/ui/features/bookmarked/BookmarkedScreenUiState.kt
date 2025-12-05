package com.example.sohaengsung.ui.features.bookmarked

import com.example.sohaengsung.data.model.User
import com.example.sohaengsung.data.model.Place

data class BookmarkedScreenUiState(
    val user: User = User(),
    val bookmarkedPlaces: List<Place> = emptyList(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
)

sealed class BookmarkScreenEvent {
    object onDeleteClick : BookmarkScreenEvent()

    sealed class Navigation : BookmarkScreenEvent() {

    }
}