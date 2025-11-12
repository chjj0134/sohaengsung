package com.example.sohaengsung.ui.screens

import android.R.attr.fontWeight
import androidx.compose.animation.core.withInfiniteAnimationFrameMillis
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.data.model.PlaceDetail
import com.example.sohaengsung.ui.components.CustomBottomSheet
import com.example.sohaengsung.ui.components.CustomDivider
import com.example.sohaengsung.ui.components.PlaceDetailContainer


// 스크린이 아닌 컴포저블 형태로 PlaceRecommendScreen 안에 배치해야 할 수도?
@Preview(showBackground = false)
@Composable
fun PlaceDetailScreen() {

    // 바텀 시트의 열림/닫힘 상태
    var isSheetOpen by remember { mutableStateOf(false) }
    val PlaceExample = Place(
        "00",
        "올웨이즈어거스트제작소",
        "망원동 00길 00번지",
        hashtags = listOf("공부하기좋은", "따뜻한"),
        details = PlaceDetail(
            true,
            true,
            true,
            "크림 라떼"
        )
    )

    Column(modifier = Modifier.padding(16.dp)) {

        // 임시 바텀 시트 열기 버튼
        // 추후 이전 페이지에서 장소 컴포넌트 클릭 시 열도록 수정 예정
        Button(onClick = { isSheetOpen = true }) {
            Text("바텀 시트 열기")
        }

        CustomBottomSheet(
            showSheet = isSheetOpen, // 현재 상태 전달
            onDismiss = { isSheetOpen = false } // 닫힘 이벤트 처리
        ) {

            // 실제 내부에 들어갈 컨텐츠

            // 1. 장소 상세 정보 컨테이너에 장소 객체 할당해서 가져 옴
            PlaceDetailContainer(PlaceExample)

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