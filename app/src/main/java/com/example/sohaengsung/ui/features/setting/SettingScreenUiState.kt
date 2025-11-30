package com.example.sohaengsung.ui.features.setting

import com.example.sohaengsung.data.model.User

data class SettingScreenUiState(
    val user: User = User(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed class SettingScreenEvent {
    object onAccountManagementClick : SettingScreenEvent()
    object onThemeChangeClick : SettingScreenEvent()
    object onTermsClick : SettingScreenEvent()
    object onNoticeClick : SettingScreenEvent()
    object onLevelClick : SettingScreenEvent()
    object EditProfilePicture : SettingScreenEvent()

    sealed class Navigation : SettingScreenEvent() {
        object NavigateToAccountManagement : Navigation()
        object NavigateToThemeChange : Navigation()
        object NavigateToTerms : Navigation()
        object NavigateToNotice : Navigation()
        object NavigateToLevel : Navigation()
    }
}