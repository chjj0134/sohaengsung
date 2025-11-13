package com.example.sohaengsung.ui.components.PlaceRecommend

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun CustomBottomSheet(
    showSheet: Boolean, // 열림/닫힘 상태를 외부에서 받아 옴
    onDismiss: () -> Unit, // 닫힘 이벤트를 외부로 전달
    content: @Composable () -> Unit, // 내부 콘텐츠를 슬롯으로 받아 옴
) {
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    if (showSheet) { // true일 때만 렌더링
        ModalBottomSheet(
            onDismissRequest = onDismiss, // 닫힘 요청 시 외부에서 전달받은 onDismiss를 호출
            sheetState = sheetState,
            modifier = Modifier
                .fillMaxWidth()
                .background(color = MaterialTheme.colorScheme.background)
        ) {
            // 외부에서 전달된 content 컴포저블을 여기에 표시함
            Column(modifier = Modifier
                .fillMaxWidth()
                .height(700.dp)
                .padding(horizontal = 20.dp, vertical = 32.dp)
                .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                content() // CustomBottomSheet의 content 슬롯
            }
        }
    }
}