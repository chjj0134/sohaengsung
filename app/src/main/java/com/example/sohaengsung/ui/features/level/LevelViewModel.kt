package com.example.sohaengsung.ui.features.level

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sohaengsung.ui.dummy.userExample
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LevelViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LevelScreenUiState())
    val uiState: StateFlow<LevelScreenUiState> = _uiState.asStateFlow()

    private val _events = MutableStateFlow<LevelScreenEvent?>(null)
    val events: StateFlow<LevelScreenEvent?> = _events.asStateFlow()

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
                    level = 5,
                    activityScore = 4,
                ),
                isLoading = false
            )
        }
    }

    fun onEvent(event: LevelScreenEvent) {
        viewModelScope.launch {
            when (event) {
                LevelScreenEvent.onLevelInfoClick -> {
                    _events.value = LevelScreenEvent.Navigation.NavigateToLevelInfo
                }

                is LevelScreenEvent.Navigation -> {
                    /* do nothing */
                }
            }
        }
    }

    fun clearEvent() {
        _events.value = null
    }
}