package com.example.sohaengsung.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sohaengsung.data.model.Place

@Composable
fun PlaceInfoContainer(place: Place) {
        Column (
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 32.dp),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            // 장소 이름
            Text(
                text = place.name,
                style = MaterialTheme.typography.bodyLarge
            )

            // 장소 해시태그
            Text(
                // PlaceExample.hashtags의 모든 아이템에 "#"와 공백을 붙여 하나의 문자열로 결합
                text = place.hashtags.joinToString(separator = " ") { hashtag ->
                    "#$hashtag"
                },
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.primary
            )

            // 별점, 리뷰 개수
            Text(
                text = "⭐️ ${place.rating} (리뷰 ${place.reviewCount}개)",
                style = MaterialTheme.typography.labelSmall
            )

            // 임시 사진 영역
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(MaterialTheme.colorScheme.secondary),
            )

            CustomDivider(MaterialTheme.colorScheme.secondary)
        }
    }