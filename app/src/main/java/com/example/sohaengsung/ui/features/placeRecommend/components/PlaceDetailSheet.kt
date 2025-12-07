package com.example.sohaengsung.ui.features.placeRecommend.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Redeem
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.ui.common.CustomDivider
import com.example.sohaengsung.ui.features.placeRecommend.PlaceRecommendViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sohaengsung.ui.common.BottomActionButtton
import com.example.sohaengsung.ui.features.placeRecommend.PlaceRecommendScreenEvent


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

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.BottomEnd
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    // 쿠폰 확인 버튼
                    BottomActionButtton(
                        onClickAction = {
                            viewModel.onEvent(
                                PlaceRecommendScreenEvent.onCouponClick
                            )
                        },
                        icon = Icons.Filled.Redeem,
                        text = "쿠폰 확인",
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // 리뷰 작성 버튼
                    BottomActionButtton(
                        onClickAction = {
                            viewModel.onEvent(
                                PlaceRecommendScreenEvent.onReviewClick
                            )
                        },
                        icon = Icons.Filled.Create,
                        text = "리뷰 작성",
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }

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