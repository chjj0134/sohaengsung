package com.example.sohaengsung.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.StarHalf
import androidx.compose.material.icons.filled.StarBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun StarRating(
    rating: Double,
    size: Int,
    modifier: Modifier = Modifier,
    maxRating: Int = 5,
    starColor: Color = MaterialTheme.colorScheme.tertiary
) {
    // 채워진 별의 개수를 정수로 계산 (예: 4.5 -> 4)
    val fullStars = rating.toInt()

    // 반만 색칠된 별(0.5점짜리)이 필요한지 확인 (예: 4.5 -> true, 3.0 -> false)
    val hasHalfStar = (rating - fullStars) >= 0.5

    // 빈 별의 개수를 계산
    // maxRating - fullStars - (반 별이 있으면 1, 없으면 0)
    val emptyStars = maxRating - fullStars - (if (hasHalfStar) 1 else 0)

    Row(
        modifier = modifier,
    ) {

        // 채워진 별 (Icons.Filled.Star) 표시
        repeat(fullStars) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "Full Star",
                tint = starColor,
                modifier = Modifier
                    .size(size.dp)
            )
        }

        // 반만 색칠된 별 (Icons.Filled.StarHalf) 표시
        if (hasHalfStar) {
            Icon(
                imageVector = Icons.Filled.StarHalf,
                contentDescription = "Half Star",
                tint = starColor,
                modifier = Modifier
                    .size(size.dp)
            )
        }

        // 빈 별 (Icons.Filled.StarBorder) 표시
        repeat(emptyStars) {
            Icon(
                imageVector = Icons.Filled.StarBorder,
                contentDescription = "Empty Star",
                tint = starColor,
                modifier = Modifier
                    .size(size.dp)
            )
        }
    }
}