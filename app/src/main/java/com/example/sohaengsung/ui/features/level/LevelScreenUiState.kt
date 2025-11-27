package com.example.sohaengsung.ui.features.level

import com.example.sohaengsung.data.model.User

data class LevelScreenUiState(
    val user: User = User(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed class LevelScreenEvent {
    object onLevelInfoClick : LevelScreenEvent()

    sealed class Navigation : LevelScreenEvent() {
        object NavigateToLevelInfo : Navigation()
    }
}