package com.example.sohaengsung.ui.features.pathRecommend

import com.example.sohaengsung.data.model.Hashtag
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.ui.features.placeRecommend.PlaceRecommendScreenEvent.Navigation

data class PathRecommendScreenUiState(
    val place: List<PlaceWithDistance> = listOf(),
    val hashtag: List<Hashtag> = listOf(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val selectedPlaceId: String? = null,
    val currentLocation: Pair<Double, Double>? = Pair(37.5459, 126.9649)
)

sealed class PathRecommendScreenEvent {
    // 체크박스 클릭 시 로직: (완료)
    data class onCheckboxClick(val place: Place) : PathRecommendScreenEvent()

    // 드롭다운 클릭 시 로직: (완료)
    data class onDropDownClick(val sortCriteria: String) : PathRecommendScreenEvent()

    // 체크된 장소만 모아 다음 페이지로 넘겨 주도록 해 두었으나, 경로 추천 2번째 페이지 아직 데이터 연결 x 상태
    object onPathComposeClick : PathRecommendScreenEvent()

    sealed class Navigation : PathRecommendScreenEvent() {
        data class NavigateToPathCompose(val placeIds: List<String>) : Navigation()
    }
}