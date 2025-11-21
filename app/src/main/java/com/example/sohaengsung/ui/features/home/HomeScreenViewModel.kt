package com.example.sohaengsung.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sohaengsung.data.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeScreenUiState(
    val user: User = User(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed class HomeScreenEvent {
    object NavigateToPlaceRecommend : HomeScreenEvent()
    object NavigateToPathRecommend : HomeScreenEvent()
    object NavigateToBookmark : HomeScreenEvent()
    object NavigateToCoupon : HomeScreenEvent()
    object NavigateToEvent : HomeScreenEvent()
    object NavigateToSetting : HomeScreenEvent()
}

class HomeScreenViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    private val _events = MutableStateFlow<HomeScreenEvent?>(null)
    val events: StateFlow<HomeScreenEvent?> = _events.asStateFlow()

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
                user = com.example.sohaengsung.ui.dummy.userExample,
                isLoading = false
            )
        }
    }

    fun onEvent(event: HomeScreenEvent) {
        viewModelScope.launch {
            _events.value = event
        }
    }

    fun clearEvent() {
        _events.value = null
    }
}
