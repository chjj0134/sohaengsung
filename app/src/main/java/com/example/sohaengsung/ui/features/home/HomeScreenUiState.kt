package com.example.sohaengsung.ui.features.home

import com.example.sohaengsung.data.model.User

data class HomeScreenUiState(
    val user: User = User(),
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

sealed class HomeScreenEvent {
    object onPlaceRecommendClick : HomeScreenEvent()
    object onPathRecommendClick : HomeScreenEvent()
    object onBookmarkClick : HomeScreenEvent()
    object onCouponClick : HomeScreenEvent()
    object onEventClick : HomeScreenEvent()
    object onSettingClick : HomeScreenEvent()

    sealed class Navigation : HomeScreenEvent() {
        object NavigateToPlaceRecommend : Navigation()
        object NavigateToPathRecommend : Navigation()
        object NavigateToBookmark : Navigation()
        object NavigateToCoupon : Navigation()
        object NavigateToEvent : Navigation()
        object NavigateToSetting : Navigation()
    }
}