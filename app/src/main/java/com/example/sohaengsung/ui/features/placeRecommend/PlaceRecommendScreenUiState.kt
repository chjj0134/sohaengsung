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

    // 정렬 드롭다운 로직 -> (완료)
    data class onDropDownClick(val sortCriteria: String) : PlaceRecommendScreenEvent()

    // 필터링 드롭다운 로직 -> (완료)
    data class onTypeFilterClick(val placeType: String) : PlaceRecommendScreenEvent()
    object onHashtagClick : PlaceRecommendScreenEvent()

    // 북마크 로직 -> 로직 구현은 완료했으나, 화면에 북마크 상태 반영 안 됨
    data class onBookmarkClick(val place: Place) : PlaceRecommendScreenEvent()

    // 쿠폰 페이지로 이동 -> (완료)
    object onCouponClick : PlaceRecommendScreenEvent()

    // 리뷰 작성 페이지로 이동 -> (완료)
    object onReviewClick : PlaceRecommendScreenEvent()

    // ViewModel이 UI에게 특정 동작을 요청하는 단일 이벤트
    sealed class Navigation : PlaceRecommendScreenEvent() {
        object NavigateToCoupon : Navigation()
        object NavigateToReview : Navigation()
    }
}