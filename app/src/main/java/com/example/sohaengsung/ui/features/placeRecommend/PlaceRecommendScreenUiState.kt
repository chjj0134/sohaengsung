package com.example.sohaengsung.ui.features.placeRecommend

import com.example.sohaengsung.data.model.Hashtag
import com.example.sohaengsung.data.model.Place

data class PlaceRecommendScreenUiState(
    val place: List<Place> = listOf(),
    val hashtag: List<Hashtag> = listOf(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed class PlaceRecommendScreenEvent {
    // 사용자 입력/액션 이벤트
    object onDropDownClick : PlaceRecommendScreenEvent()
    object onHashtagClick : PlaceRecommendScreenEvent()
    object onBookmarkClick : PlaceRecommendScreenEvent()
    object onNavigateToReview : PlaceRecommendScreenEvent()

    // ViewModel이 UI에게 특정 동작을 요청하는 단일 이벤트
    sealed class Navigation : PlaceRecommendScreenEvent() {
        object NavigateToReview : Navigation()
    }
}