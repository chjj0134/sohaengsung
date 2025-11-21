package com.example.sohaengsung.ui.features.logIn

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LogInViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LogInScreenUiState())
    val uiState: StateFlow<LogInScreenUiState> = _uiState.asStateFlow()

    private val _events = MutableStateFlow<LogInScreenEvent.Navigation?>(null)
    val events: StateFlow<LogInScreenEvent.Navigation?> = _events.asStateFlow()

    fun onEvent(event: LogInScreenEvent) {
        viewModelScope.launch {
            when (event) {
                // 버튼 클릭 시 이메일 로그인 화면으로 이동 요청
                LogInScreenEvent.onEmailLoginClick -> {
                    // 추후 이메일 로그인 화면이 따로 있다면 해당 화면으로 이동 요청해야 함
                }

                LogInScreenEvent.onKakaoLoginClick -> {
                    handleKakaoLogin()
                }

                // 회원가입 이벤트 핸들링
                LogInScreenEvent.onSignUpClick -> {
                    _events.value = LogInScreenEvent.Navigation.NavigateToSignUp
                }

                is LogInScreenEvent.Navigation -> {
                    /* do nothing */
                }
            }
        }
    }

    // (미완성) 이메일 로그인 이벤트 핸들링 로직
    private fun handleEmailLogin() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            // 더미 로직
            delay(1000)

            // 로그인 성공 가정
            val success = true

            if (success) {
                // 이메일 로그인 화면으로 이동 요청
                // _events.value = LogInScreenEvent.Navigation.NavigateToEmailLogin

                // 이메일 로그인 화면 및 홈 화면 미완성이므로, 우선 '내 주변 장소 추천' 화면으로 이동
                _events.value = LogInScreenEvent.Navigation.NavigateToHome
            } else {
                // 로그인 실패 시
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "에러 메시지"
                )
            }
        }
    }

    // (미완성) 카카오 로그인 이벤트 핸들링 로직
    private fun handleKakaoLogin() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            // 실제 카카오 로그인 SDK 호출 및 인증 처리 로직 작성 필요

            // 더미 로직
            delay(1000)

            // 로그인 성공 가정
            val success = true

            if (success) {
                // 홈 화면으로 이동 요청
                // 홈 화면 미완성이므로, 우선 '내 주변 장소 추천' 화면으로 이동
                _events.value = LogInScreenEvent.Navigation.NavigateToHome
            } else {
                // 로그인 실패 시
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "에러 메시지"
                )
            }
        }
    }

    fun clearEvent() {
        _events.value = null
    }
}