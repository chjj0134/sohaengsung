package com.example.sohaengsung.ui.features.coupon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sohaengsung.data.model.Coupon
import com.example.sohaengsung.ui.dummy.userExample
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CouponViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(CouponScreenUiState())
    val uiState: StateFlow<CouponScreenUiState> = _uiState.asStateFlow()

    private val _events = MutableStateFlow<CouponScreenEvent.Navigation?>(null)
    val events: StateFlow<CouponScreenEvent.Navigation?> = _events.asStateFlow()

    init {
        loadUserData()
        loadCouponData()
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
                    uid = "u1939"
                ),
                isLoading = false
            )
        }
    }

    private fun loadCouponData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            // TODO: 실제 쿠폰 데이터 로드 (Repository에서 가져오기)
            // 현재는 더미 데이터 사용
            _uiState.value = _uiState.value.copy(
                coupon = Coupon(
                    "c15355",
                    "u1939",
                    "2q623y",
                    4,
                    false
                ),
                isLoading = false
            )
        }
    }

    fun onEvent(event: CouponScreenEvent) {
        viewModelScope.launch {
            when (event) {
                // 클릭 시 사용처 안내 화면으로 이동
                CouponScreenEvent.onNavigateToVoucherScreen -> {
                    _events.value = CouponScreenEvent.Navigation.NavigateToVoucherScreen
                }

                is CouponScreenEvent.Navigation -> {
                    /* do nothing */
                }
            }
        }
    }

    fun clearEvent() {
        _events.value = null
    }
}