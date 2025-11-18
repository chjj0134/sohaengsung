package com.example.sohaengsung.ui.components.Home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun HomeMenuCard(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    // 그라데이션 색상 정의
    val iconGradientBrush = Brush.horizontalGradient(
        listOf(
            MaterialTheme.colorScheme.primary,   // 시작 색상
            MaterialTheme.colorScheme.tertiary // 끝 색상
        )
    )

    Surface(
        modifier = modifier
            .fillMaxWidth()
            .height(120.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.surface,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 아이콘 (그라데이션 색상 적용)
            Box(
                modifier = Modifier.size(48.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    modifier = Modifier
                        .size(48.dp)
                        .drawWithContent {
                            drawContent()
                            drawRect(
                                brush = iconGradientBrush,
                                blendMode = BlendMode.SrcAtop
                            )
                        },
                    tint = Color.Unspecified
                )
            }

            // 제목
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }
}

