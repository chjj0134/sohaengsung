package com.example.sohaengsung.ui.features.logIn

data class LogInScreenUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed class LogInScreenEvent {
    // 사용자 입력/액션 이벤트
    object onEmailLoginClick : LogInScreenEvent()
    object onKakaoLoginClick : LogInScreenEvent()
    object onSignUpClick : LogInScreenEvent()

    // ViewModel이 UI에게 특정 동작을 요청하는 단일 이벤트
    sealed class Navigation : LogInScreenEvent() {
        object NavigateToEmailLogin : Navigation()
        object NavigateToKakaoLogin : Navigation()
        object NavigateToSignUp : Navigation()
        object NavigateToHome : Navigation() // 로그인 성공 시 홈 화면으로 이동
    }
}