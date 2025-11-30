package com.example.sohaengsung.ui.features.bookmarked

import com.example.sohaengsung.data.model.User

data class BookmarkedScreenUiState(
    val user: User = User(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed class BookmarkScreenEvent {
    object onDeleteClick : BookmarkScreenEvent()

    sealed class Navigation : BookmarkScreenEvent() {

    }
}