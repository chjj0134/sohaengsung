package com.example.sohaengsung.ui.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomDivider(
    color: Color
) {
    HorizontalDivider(
        Modifier
            .fillMaxWidth(), // 가로 전체를 채웁니다.
        1.dp,
        color = color
    )
}