package com.example.sohaengsung.ui.features.placeRecommend.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.ui.common.CustomDivider
import com.example.sohaengsung.ui.features.placeRecommend.PlaceRecommendViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState


@Composable
fun PlaceDetailSheet(
    place: Place,
    isSheetOpen: Boolean,
    onSheetDismiss: () -> Unit,
    viewModel: PlaceRecommendViewModel
) {
    //장소 리뷰 로드
    LaunchedEffect(isSheetOpen, place.placeId) {
        if (isSheetOpen) {
            viewModel.loadReviews(place.placeId)
        }
    }
    val reviewList = viewModel.reviews.collectAsState().value

    Column {

        CustomBottomSheet(
            showSheet = isSheetOpen, // 현재 상태 전달
            onDismiss = onSheetDismiss // 닫힘 이벤트 처리
        ) {

            // 장소 상세 정보
            PlaceDetailContainer(place = place,
                viewModel = viewModel)

            // 디바이더
            CustomDivider(MaterialTheme.colorScheme.secondary)

            // 임시 리뷰 컨테이너
            reviewList.forEach { review ->
                ReviewContainer(review = review)
                CustomDivider(MaterialTheme.colorScheme.secondary)
            }
        }
    }
}