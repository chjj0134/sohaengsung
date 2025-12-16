package com.example.sohaengsung.ui.features.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sohaengsung.data.model.User
import com.example.sohaengsung.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingViewModel(
    private val uid: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingScreenUiState())
    val uiState: StateFlow<SettingScreenUiState> = _uiState.asStateFlow()

    private val _events = MutableStateFlow<SettingScreenEvent.Navigation?>(null)
    val events: StateFlow<SettingScreenEvent.Navigation?> = _events.asStateFlow()

    private val userRepository = UserRepository()

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            val userData = userRepository.getUser(uid)

            _uiState.value = _uiState.value.copy(
                user = userData ?: User(uid = uid),
                isLoading = false
            )
        }
    }

    fun onEvent(event: SettingScreenEvent) {
        when (event) {
            SettingScreenEvent.onAccountManagementClick ->
                _events.value = SettingScreenEvent.Navigation.NavigateToAccountManagement

            SettingScreenEvent.onThemeChangeClick ->
                _events.value = SettingScreenEvent.Navigation.NavigateToThemeChange

            SettingScreenEvent.onTermsClick ->
                _events.value = SettingScreenEvent.Navigation.NavigateToTerms

            SettingScreenEvent.onNoticeClick ->
                _events.value = SettingScreenEvent.Navigation.NavigateToNotice

            SettingScreenEvent.onLevelClick ->
                _events.value = SettingScreenEvent.Navigation.NavigateToLevel

            is SettingScreenEvent.Navigation -> {
                /* do nothing */
            }
        }
    }

    fun clearEvent() {
        _events.value = null
    }
}
