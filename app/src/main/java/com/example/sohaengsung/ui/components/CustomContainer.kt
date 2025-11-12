package com.example.sohaengsung.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CustomContainer(
    content: @Composable () -> Unit // 내부 콘텐츠를 슬롯으로 받아 옴
) {
    // 기존 바텀 시트 -> 일반 컨테이너
    Surface(
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp), // 상단 모서리만 둥글게
        color = MaterialTheme.colorScheme.background,
        shadowElevation = 8.dp // 그림자 효과
    ) {
        content()
    }
}