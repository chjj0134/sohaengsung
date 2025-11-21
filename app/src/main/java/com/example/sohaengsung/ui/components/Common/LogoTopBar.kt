package com.example.sohaengsung.ui.components.Common

import androidx.compose.foundation.clickable
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
fun LogoTopBar(
    onProfileClick: () -> Unit = {},
    profileContent: @Composable () -> Unit
) {
    // 그라데이션 색상 정의
    val textGradientBrush = Brush.horizontalGradient(
        listOf(
            MaterialTheme.colorScheme.primary,   // 시작 색상
            MaterialTheme.colorScheme.tertiary // 끝 색상
        )
    )

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            // 왼쪽: 로고 텍스트
            Text(
                text = "소행성*",
                style = MaterialTheme.typography.titleLarge.copy(
                    brush = textGradientBrush,
                    fontSize = 35.sp,
                    fontWeight = FontWeight.Bold
                ),
                modifier = Modifier.padding(top = 20.dp)  // 위쪽 여백으로 텍스트를 아래로 이동
            )
            
            // 오른쪽: 프로필 이미지
            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .clickable { onProfileClick() },
                contentAlignment = Alignment.Center
            ) {
                profileContent()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LogoTopBarPreview() {
    SohaengsungTheme {
        LogoTopBar(
            onProfileClick = {},
            profileContent = {
                // 프로필 이미지 프리뷰
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .padding(8.dp)
                ) {
                    Text("프로필")
                }
            }
        )
    }
}

