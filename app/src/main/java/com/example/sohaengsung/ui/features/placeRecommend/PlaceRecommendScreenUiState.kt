package com.example.sohaengsung.ui.features.placeRecommend

import com.example.sohaengsung.data.model.Hashtag
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.ui.features.pathRecommend.PathRecommendScreenEvent
import com.example.sohaengsung.ui.features.pathRecommend.PathRecommendScreenEvent.Navigation

data class PlaceRecommendScreenUiState(
    val place: List<Place> = listOf(),
    val hashtag: List<Hashtag> = listOf(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val selectedPlaceId: String? = null,
    val currentLat: Double = 37.5665,
    val currentLng: Double = 126.9780
)

sealed class PlaceRecommendScreenEvent {
    // 사용자 입력/액션 이벤트
    object onDropDownClick : PlaceRecommendScreenEvent()
    object onHashtagClick : PlaceRecommendScreenEvent()
    object onBookmarkClick : PlaceRecommendScreenEvent()
    object onCouponClick : PlaceRecommendScreenEvent()
    object onReviewClick : PlaceRecommendScreenEvent()

    // ViewModel이 UI에게 특정 동작을 요청하는 단일 이벤트
    sealed class Navigation : PlaceRecommendScreenEvent() {
        object NavigateToCoupon : Navigation()
        object NavigateToReview : Navigation()
    }
}