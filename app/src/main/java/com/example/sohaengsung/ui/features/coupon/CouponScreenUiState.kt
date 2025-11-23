package com.example.sohaengsung.ui.features.coupon

import com.example.sohaengsung.data.model.Coupon
import com.example.sohaengsung.data.model.User

data class CouponScreenUiState (
    val user: User = User(),
    val coupon: Coupon = Coupon(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed class CouponScreenEvent {
    // 사용자 입력/액션 이벤트
    object onNavigateToVoucherScreen : CouponScreenEvent()

    // ViewModel이 UI에게 특정 동작을 요청하는 단일 이벤트
    sealed class Navigation : CouponScreenEvent() {
        object NavigateToVoucherScreen : Navigation()
    }
}