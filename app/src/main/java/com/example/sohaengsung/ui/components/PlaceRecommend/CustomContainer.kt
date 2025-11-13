package com.example.sohaengsung.ui.components.PlaceRecommend

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomContainer(
    content: @Composable () -> Unit // 내부 콘텐츠를 슬롯으로 받아 옴
) {
    // 기존 바텀 시트 -> 일반 컨테이너
    Surface(
        color = MaterialTheme.colorScheme.background,
        shadowElevation = 8.dp, // 그림자 효과
    ) {
        Column (
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 32.dp)
                .height(500.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            content()
        }
    }
}