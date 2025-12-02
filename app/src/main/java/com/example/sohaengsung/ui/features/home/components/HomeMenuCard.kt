package com.example.sohaengsung.ui.features.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sohaengsung.ui.theme.CardBackgroundWhite
import androidx.compose.ui.graphics.ColorFilter

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
            .width(180.dp)
            .height(180.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp),
        color = CardBackgroundWhite,
        border = BorderStroke(1.dp, Color(0xFFABABAB)),  // stroke 색상 ABABAB, weight 1dp
        shadowElevation = 4.dp
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // 제목 (왼쪽 상단)
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 22.sp,
                    lineHeight = 28.sp,  // 줄 간격 추가
                    fontWeight = androidx.compose.ui.text.font.FontWeight.SemiBold
                ),
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .align(Alignment.TopStart)
            )
            
            // 아이콘 (하단 오른쪽, 아이콘 외곽선에 그라데이션 적용)
            // TODO: 그라데이션 적용이 안 되고 네모 박스 형태로 나타나는 관계로, 임시 주석 처리 후 단색으로 변경함
            // 기말 끝난 이후 점검해 볼 것
            Icon(
                imageVector = icon,
                contentDescription = title,
                modifier = Modifier
                    .size(64.dp)
                    .align(Alignment.BottomEnd),
                //.drawWithContent {
                    //drawContent()
                //drawRect(
                        // brush = iconGradientBrush,
                        // blendMode = BlendMode.SrcIn
                    //)
                //},
                tint = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

