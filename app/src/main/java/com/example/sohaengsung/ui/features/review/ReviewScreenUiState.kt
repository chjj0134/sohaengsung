package com.example.sohaengsung.ui.features.review

data class ReviewScreenUiState(
    val rating: Int = 0, // 0-5 별점
    val reviewText: String = "",
    val selectedThemeTag: String? = null, // 장소 테마 태그
    val selectedAtmosphereTag: String? = null, // 분위기 태그
    val selectedConvenienceTag: String? = null, // 편의사항 태그
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed class ReviewScreenEvent {
    // 사용자 입력/액션 이벤트
    object onRatingClick : ReviewScreenEvent()
    object onReviewTextChange : ReviewScreenEvent()
    object onTagSelect : ReviewScreenEvent()
    object onSubmitReview : ReviewScreenEvent()
    object onBackClick : ReviewScreenEvent()

    // ViewModel이 UI에게 특정 동작을 요청하는 단일 이벤트
    sealed class Navigation : ReviewScreenEvent() {
        object NavigateBack : Navigation()
    }
}

