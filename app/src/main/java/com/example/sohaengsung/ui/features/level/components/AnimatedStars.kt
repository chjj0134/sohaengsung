package com.example.sohaengsung.ui.features.level.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
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

    // 개수에 맞게 별만 생성
    (1..userLevel).forEach { level ->
        val currentRadius = radiusStep * level

        // 회전 애니메이션 상태 저장
        val rotation = remember { Animatable(0f) }

        // 애니메이션 시작
        LaunchedEffect(key1 = level) {
            rotation.animateTo(
                targetValue = 360f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = (6000 - level * 500).coerceAtLeast(3000), // 레벨이 높을수록 조금 빠르게 회전
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart
                )
            )
        }

        // 별 컴포넌트
        StarComponent(
            level = level,
            radius = currentRadius,
            rotation = rotation.value, // 현재 회전 각도 적용
            starSize = starSize
        )
    }
}