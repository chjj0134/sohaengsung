package com.example.sohaengsung.ui.components.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sohaengsung.ui.theme.SohaengsungTheme

@Composable
fun CustomTopBar(contentText: String) {

    // 그라데이션 색상 정의
    val textGradientBrush = Brush.horizontalGradient(
        listOf(
            MaterialTheme.colorScheme.primary,   // 시작 색상
            MaterialTheme.colorScheme.tertiary // 끝 색상
        )
    )

    // 커스텀 TopBar - 높이를 낮게 설정
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp), // SmallTopAppBar와 유사한 높이
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(start = 20.dp, top = 8.dp),
            contentAlignment = Alignment.TopStart,
        ) {
            Text(
                text = contentText,
                style = MaterialTheme.typography.titleLarge.copy(
                    brush = textGradientBrush,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CustomTopBarPreview() {
    SohaengsungTheme {
        CustomTopBar("태그헌터")
    }
}
