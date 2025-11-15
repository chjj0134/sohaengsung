package com.example.sohaengsung.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sohaengsung.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

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
                user = com.example.sohaengsung.ui.dummy.userExample.copy(
                    nickname = "카공탐험가",
                    level = 5
                ),
                isLoading = false
            )
        }
    }

    fun onEvent(event: SettingScreenEvent) {
        viewModelScope.launch {
            _events.value = event
        }
    }

    fun clearEvent() {
        _events.value = null
    }
}

