package com.example.sohaengsung.ui.features.coupon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sohaengsung.data.model.Coupon
import com.example.sohaengsung.data.repository.CouponRepository
import com.example.sohaengsung.data.repository.UserRepository
import com.example.sohaengsung.ui.dummy.userExample
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CouponViewModel : ViewModel() {

    private val userRepository = UserRepository()
    private val couponRepository = CouponRepository()

    private val _uiState = MutableStateFlow(CouponScreenUiState())
    val uiState: StateFlow<CouponScreenUiState> = _uiState.asStateFlow()

    private val _events = MutableStateFlow<CouponScreenEvent.Navigation?>(null)
    val events: StateFlow<CouponScreenEvent.Navigation?> = _events.asStateFlow()

    private var couponListener: ListenerRegistration? = null

    init {
        loadUserData()
        observeCouponData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, errorMessage = null) }

                val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
                val user = userRepository.getUser(uid)

                if (user != null) {
                    _uiState.update { it.copy(user = user, isLoading = false) }
                } else {
                    _uiState.update { it.copy(errorMessage = "사용자 정보를 찾을 수 없습니다.", isLoading = false) }
                }

            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message, isLoading = false) }
            }
        }
    }

    /*
    private fun loadCouponData() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, errorMessage = null) }

                val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
                val coupons = couponRepository.getUserCoupons(uid)

                val coupon = coupons.firstOrNull()

                if (coupon != null) {
                    _uiState.update { it.copy(coupon = coupon, isLoading = false) }
                } else {
                    _uiState.update { it.copy(errorMessage = "쿠폰 정보가 없습니다.", isLoading = false) }
                }

            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message, isLoading = false) }
            }
        }
    }*/

    private fun observeCouponData() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        couponListener = couponRepository.listenUserCoupon(uid) { stampCount ->
            _uiState.update { state ->
                state.copy(
                    coupon = state.coupon.copy(stampCount = stampCount)
                )
            }
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