package com.example.sohaengsung.ui.features.level.components

import android.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sohaengsung.data.model.User
import com.example.sohaengsung.ui.common.ProfilePic
import com.example.sohaengsung.ui.dummy.userExample

data class StarData(
    val level: Int,
    val color: Color
)

// 전체 컴포넌트
@Composable
fun LevelOrbitAnimation(
    user: User
) {

    val totalOrbits = 5 // 총 궤도 개수 (최대 레벨)
    val maxRadius = 150.dp // 가장 바깥 궤도의 반지름
    val starSize = 24.dp

    // 사용자가 제공한 ProfilePic 컴포넌트 (가운데)
    Box(
        modifier = Modifier.size(maxRadius * 2), // 궤도를 포함할 전체 크기
        contentAlignment = Alignment.Center
    ) {
        // 1. 프로필 이미지 (가운데)
        ProfilePic(user, 52) // 사용자의 프로필 컴포넌트

        // 2. 5개의 궤도 (회색/투명)
        OrbitRings(totalOrbits, maxRadius)

        // 3. 레벨에 따른 별들
        AnimatedStars(user.level, totalOrbits, maxRadius, starSize)
    }
}