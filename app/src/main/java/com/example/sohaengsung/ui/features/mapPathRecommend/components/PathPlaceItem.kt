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
        modifier = modifier.fillMaxWidth()
    ) {
        // 실제 콘텐츠 영역
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 20.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 순서 번호 원형 배지
            Box(
                modifier = Modifier
                    .size(24.dp) // 크기를 조금 키워 시인성 확보
                    .background(
                        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.8f), // 스크린샷 3번 느낌 적용
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = placeWithOrder.order.toString(),
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }

            // 장소 정보
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // 장소 이름
                Text(
                    text = placeWithOrder.place.name,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )

                // 해시태그
                Text(
                    text = placeWithOrder.place.hashtags.joinToString(separator = " ") { "#$it" },
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // 거리 정보
            if (placeWithOrder.distanceFromPrevious != null) {
                Text(
                    text = DistanceCalculator.formatDistance(placeWithOrder.distanceFromPrevious),
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary // 거리는 보조 색상으로
                )
            }
        }

        if (placeWithOrder.order < 3) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(0.5.dp)
                    .background(DividerGray.copy(alpha = 0.2f))
            )
        }
    }
}