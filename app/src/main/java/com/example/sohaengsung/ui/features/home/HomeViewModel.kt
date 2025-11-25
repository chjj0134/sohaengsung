package com.example.sohaengsung.ui.features.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


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
            when (event) {
                HomeScreenEvent.onPlaceRecommendClick -> {
                    _events.value = HomeScreenEvent.Navigation.NavigateToPlaceRecommend
                }

                HomeScreenEvent.onPathRecommendClick -> {
                    _events.value = HomeScreenEvent.Navigation.NavigateToPathRecommend
                }

                HomeScreenEvent.onBookmarkClick -> {
                    _events.value = HomeScreenEvent.Navigation.NavigateToBookmark
                }

                HomeScreenEvent.onCouponClick -> {
                    _events.value = HomeScreenEvent.Navigation.NavigateToCoupon
                }

                HomeScreenEvent.onEventClick -> {
                    _events.value = HomeScreenEvent.Navigation.NavigateToEvent
                }

                HomeScreenEvent.onSettingClick -> {
                    _events.value = HomeScreenEvent.Navigation.NavigateToSetting
                }

                is HomeScreenEvent.Navigation -> {
                    /* do nothing */
                }
            }
        }
    }

    fun clearEvent() {
        _events.value = null
    }
}
