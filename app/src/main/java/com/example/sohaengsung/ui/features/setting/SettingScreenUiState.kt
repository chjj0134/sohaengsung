package com.example.sohaengsung.ui.features.setting

import com.example.sohaengsung.data.model.User

data class SettingScreenUiState(
    val user: User = User(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed class SettingScreenEvent {
    object NavigateToAccountManagement : SettingScreenEvent()
    object NavigateToThemeChange : SettingScreenEvent()
    object NavigateToTerms : SettingScreenEvent()
    object NavigateToNotice : SettingScreenEvent()
    object NavigateToLevelDetail : SettingScreenEvent()
    object EditProfilePicture : SettingScreenEvent()
}