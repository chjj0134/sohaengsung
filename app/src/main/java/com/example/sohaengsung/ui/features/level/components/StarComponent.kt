package com.example.sohaengsung.ui.features.level.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.text.style.TextAlign

@Composable
fun StarComponent(
    level: Int,
    radius: Dp,
    rotation: Float,
    starSize: Dp
) {

    // TODO: 별 위치 하드코딩 -> 수정 필요
    val yOffset = when (level) {
        1 -> 30.dp
        2 -> 60.dp
        3 -> 90.dp
        4 -> 120.dp
        5 -> 150.dp
        else -> 0.dp
    }

    Box(
        modifier = Modifier
            .size(radius * 2 + starSize)
            .rotate(rotation)
    ) {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = "Level $level Star",
            tint = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier
                .size(starSize)
                .offset(x = radius, y = yOffset) // y 오프셋
                .align(Alignment.CenterStart)
        )

        // 별 위에 레벨 텍스트 표시
        Text(
            text = "$level",
            color = Color.White,
            fontSize = 10.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .size(starSize)
                .offset(x = radius, y = yOffset) // 별과 같은 위치에 오프셋
                .align(Alignment.CenterStart)
                .wrapContentSize(Alignment.Center) // 텍스트를 별 중앙에 배치
        )
    }
}
