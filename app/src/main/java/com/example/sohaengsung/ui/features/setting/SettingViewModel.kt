package com.example.sohaengsung.ui.features.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sohaengsung.ui.dummy.userExample
import com.example.sohaengsung.ui.features.home.HomeScreenEvent
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import com.example.sohaengsung.data.model.User
import com.example.sohaengsung.data.repository.UserRepository

class SettingViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SettingScreenUiState())
    val uiState: StateFlow<SettingScreenUiState> = _uiState.asStateFlow()

    private val _events = MutableStateFlow<SettingScreenEvent?>(null)
    val events: StateFlow<SettingScreenEvent?> = _events.asStateFlow()

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

            val uid = FirebaseAuth.getInstance().currentUser?.uid

            if (uid == null) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = "로그인된 사용자 없음",
                    isLoading = false
                )
                return@launch
            }

            val userData = userRepository.getUser(uid)

            _uiState.value = _uiState.value.copy(
                user = userData ?: User(uid = uid),
                isLoading = false
            )
        }
    }

    fun onEvent(event: SettingScreenEvent) {
        viewModelScope.launch {
            when (event) {
                SettingScreenEvent.onAccountManagementClick -> {
                    _events.value = SettingScreenEvent.Navigation.NavigateToAccountManagement
                }

                SettingScreenEvent.onThemeChangeClick -> {
                    _events.value = SettingScreenEvent.Navigation.NavigateToThemeChange
                }

                SettingScreenEvent.onTermsClick -> {
                    _events.value = SettingScreenEvent.Navigation.NavigateToTerms
                }

                SettingScreenEvent.onNoticeClick -> {
                    _events.value = SettingScreenEvent.Navigation.NavigateToNotice
                }

                SettingScreenEvent.onLevelClick -> {
                    _events.value = SettingScreenEvent.Navigation.NavigateToLevel
                }

                is SettingScreenEvent.Navigation -> {
                    /* do nothing */
                }
            }
        }
    }

    fun clearEvent() {
        _events.value = null
    }
}

