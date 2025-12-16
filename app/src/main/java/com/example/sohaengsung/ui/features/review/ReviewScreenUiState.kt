package com.example.sohaengsung.ui.features.review

data class ReviewScreenUiState(
    val rating: Int = 0, // 0-5 별점
    val reviewText: String = "",
    val selectedThemeTag: String? = null, // 장소 테마 태그
    val selectedAtmosphereTag: String? = null, // 분위기 태그
    val selectedConvenienceTag: String? = null, // 편의사항 태그
    val isLoading: Boolean = false,
    val showSuccessModal: Boolean = false,
    val errorMessage: String? = null
)

sealed class ReviewScreenEvent {
    data class OnRatingClick(val rating: Int) : ReviewScreenEvent()
    data class OnReviewTextChange(val text: String) : ReviewScreenEvent()
    data class OnTagSelect(val tagType: TagType, val tag: String) : ReviewScreenEvent()
    object OnSubmitReview : ReviewScreenEvent()
    object OnBackClick : ReviewScreenEvent()

    enum class TagType { THEME, ATMOSPHERE, CONVENIENCE }

    sealed class Navigation : ReviewScreenEvent() {
        object NavigateBack : Navigation()
    }
}

