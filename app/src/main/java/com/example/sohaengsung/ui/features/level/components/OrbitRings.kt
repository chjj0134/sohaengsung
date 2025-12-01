package com.example.sohaengsung.ui.features.level.components

import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.Dp

@Composable
fun OrbitRings(count: Int, maxRadius: Dp) {
    Canvas(
        modifier = Modifier
            .size(maxRadius * 2)
    ) {
        val center = Offset(size.width / 2, size.height / 2)
        val radiusStep = maxRadius.toPx() / count

        // 5개의 궤도 링 그리기
        repeat(count) { index ->
            val radius = (index + 1) * radiusStep
            drawCircle(
                color = Color.LightGray.copy(alpha = 0.5f), // 투명한 회색
                center = center,
                radius = radius,
                style = Stroke(width = 1.dp.toPx())
            )
        }
    }
}