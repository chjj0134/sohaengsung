package com.example.sohaengsung.ui.features.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sohaengsung.ui.dummy.userExample
import com.example.sohaengsung.ui.features.home.HomeScreenEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SettingScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(SettingScreenUiState())
    val uiState: StateFlow<SettingScreenUiState> = _uiState.asStateFlow()

    private val _events = MutableStateFlow<SettingScreenEvent?>(null)
    val events: StateFlow<SettingScreenEvent?> = _events.asStateFlow()

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )
            
            // TODO: 실제 사용자 데이터 로드 (Repository에서 가져오기)
            // 현재는 더미 데이터 사용
            _uiState.value = _uiState.value.copy(
                user = userExample.copy(
                    nickname = "카공탐험가",
                    level = 5
                ),
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

                SettingScreenEvent.onLevelDetailClick -> {
                    _events.value = SettingScreenEvent.Navigation.NavigateToLevelDetail
                }

                SettingScreenEvent.EditProfilePicture -> {
                    // 정의
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

