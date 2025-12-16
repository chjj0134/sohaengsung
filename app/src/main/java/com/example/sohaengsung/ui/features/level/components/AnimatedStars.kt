package com.example.sohaengsung.ui.features.level.components

import android.R.attr.level
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.Dp

@Composable
fun AnimatedStars(
    userLevel: Int,
    totalOrbits: Int = 5,
    maxRadius: Dp,
    starSize: Dp
) {
    val radiusStep = maxRadius / totalOrbits

    val rotation = remember { Animatable(0f) }

    LaunchedEffect(Unit) {
        while (true) {
            rotation.animateTo(
                targetValue = 360f,
                animationSpec = tween(
                    durationMillis = 6000,
                    easing = LinearEasing
                )
            )
            rotation.snapTo(0f)
        }
    }

    // 개수에 맞게 별만 생성
    (1..userLevel).forEach { level ->
        val currentRadius = radiusStep * level

        // 회전 애니메이션 상태 저장
        /*val infiniteTransition = rememberInfiniteTransition(label = "starRotation")

        // 애니메이션 시작
        val rotation by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = (6000 - level * 500).coerceAtLeast(3000),
                    easing = LinearEasing
                ),
                repeatMode = RepeatMode.Restart
            ),
            label = "rotation"
        )*/

        // 별 컴포넌트
        StarComponent(
            level = level,
            radius = currentRadius,
            rotation = rotation.value + (level * 25f),
            starSize = starSize
        )
    }
}
