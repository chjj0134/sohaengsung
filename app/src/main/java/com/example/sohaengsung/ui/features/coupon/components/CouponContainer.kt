package com.example.sohaengsung.ui.features.coupon.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.sohaengsung.ui.common.CustomDivider

@Composable
fun CouponContainer(
    stampTotal: Int,
    stampCount: Int
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(15.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "나의 쿠폰함",
                style = MaterialTheme.typography.bodyLarge,
            )

            CustomDivider(
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            LazyVerticalGrid(
                columns = GridCells.Fixed(stampTotal / 2),
                modifier = Modifier.heightIn(max = 180.dp), // 높이 제한으로 스크롤 방지
                contentPadding = PaddingValues(horizontal = 4.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(stampTotal) { index ->
                    StampItem(isEarned = index < stampCount)
                }
            }
        }
    }
}

@Composable
fun StampItem(isEarned: Boolean) {
    // 스탬프 아이콘을 감싸는 원형 컨테이너
    Box(
        modifier = Modifier
            .aspectRatio(1f) // 정사각형
            .clip(CircleShape)
            .background(
                if (isEarned)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.secondary
            ) // 획득 여부에 따라 색상 변경
            .size(50.dp), // 아이콘 크기
        contentAlignment = Alignment.Center
    ) {
        if (isEarned) {
            // 획득한 스탬프는 임시로 하트로 표시
            Icon(
                Icons.Filled.Favorite,
                contentDescription = "스탬프 획득",
                tint = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier.size(30.dp)
            )
        } else {
            // 미획득 스탬프 -> 빈 원
        }
    }
}