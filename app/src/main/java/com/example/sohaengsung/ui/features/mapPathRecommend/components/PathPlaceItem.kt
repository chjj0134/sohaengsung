package com.example.sohaengsung.ui.features.mapPathRecommend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sohaengsung.ui.features.mapPathRecommend.components.DistanceCalculator
import com.example.sohaengsung.ui.features.mapPathRecommend.PlaceWithOrder
import com.example.sohaengsung.ui.theme.DividerGray

@Composable
fun PathPlaceItem(
    placeWithOrder: PlaceWithOrder,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 순서 번호 원형 배지
            Box(
                modifier = Modifier
                    .size(32.dp)
                    .background(
                        color = MaterialTheme.colorScheme.primary,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = placeWithOrder.order.toString(),
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            // 장소 정보
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = placeWithOrder.place.name,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = placeWithOrder.place.hashtags.joinToString(separator = " ") { hashtag ->
                        "#$hashtag"
                    },
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // 거리 정보 (1번 장소는 표시하지 않음)
            if (placeWithOrder.distanceFromPrevious != null) {
                Text(
                    text = DistanceCalculator.formatDistance(placeWithOrder.distanceFromPrevious),
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        // 점선 구분선 (마지막 아이템이 아닌 경우)
        if (placeWithOrder.order < 3) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
                    .height(1.dp)
                    .background(DividerGray.copy(alpha = 0.3f))  // Theme의 색상 사용
            )
        }
    }
}

