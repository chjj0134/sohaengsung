package com.example.sohaengsung.ui.components.PlaceRecommend

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.ui.components.Common.CustomDivider

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

            // 1. 장소 상세 정보 컨테이너에 장소 객체 할당해서 가져 옴
            PlaceDetailContainer(place)

            // 2. Divider
            CustomDivider(MaterialTheme.colorScheme.secondary)

            // 3. ReviewContainer
            // 임시 리뷰 영역으로 대체
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(MaterialTheme.colorScheme.secondary),
            )
        }
    }
}