package com.example.sohaengsung.ui.features.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sohaengsung.ui.dummy.userExample
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
            // onEvent 호출 시, 바로 _events에 값을 설정하고
            // UI에서 해당 값을 소비한 후 clearEvent()를 호출해야 함
            _events.value = event
        }
    }

    fun clearEvent() {
        _events.value = null
    }
}

