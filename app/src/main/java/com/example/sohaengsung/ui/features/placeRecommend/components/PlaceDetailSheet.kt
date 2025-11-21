package com.example.sohaengsung.ui.features.placeRecommend.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.ui.common.CustomDivider

@Composable
fun PlaceDetailSheet(
    place: Place,
    isSheetOpen: Boolean,
    onSheetDismiss: () -> Unit
) {

    Column {

        CustomBottomSheet(
            showSheet = isSheetOpen, // 현재 상태 전달
            onDismiss = onSheetDismiss // 닫힘 이벤트 처리
        ) {

            // 장소 상세 정보
            PlaceDetailContainer(place)

            // 디바이더
            CustomDivider(MaterialTheme.colorScheme.secondary)

            // 임시 리뷰 컨테이너
            ReviewContainer()
        }
    }
}