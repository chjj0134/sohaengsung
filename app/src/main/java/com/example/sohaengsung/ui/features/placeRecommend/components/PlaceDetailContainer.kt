package com.example.sohaengsung.ui.features.placeRecommend.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.ui.common.Bookmark

fun Boolean.toOXString(): String = if (this) "O" else "X"

@Composable
fun PlaceDetailContainer(
    place: Place,
    isBookmarked: Boolean,
    onBookmarkToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column (
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = place.name,
                style = MaterialTheme.typography.titleLarge
            )

            Bookmark(
                isBookmarked = isBookmarked,
                onBookmarkToggle = onBookmarkToggle
            )
        }

        Text(
            // PlaceExample.hashtags의 모든 아이템에 "#"와 공백을 붙여 하나의 문자열로 결합
            text = place.hashtags.joinToString(separator = " ") { hashtag ->
                "#$hashtag"
            },
            style = MaterialTheme.typography.titleSmall,
            color = MaterialTheme.colorScheme.primary
        )

        val formattedRating = String.format("%.1f", place.rating)

        // 별점, 리뷰 개수
        Text(
            text = "⭐ $formattedRating (리뷰 ${place.reviewCount}개)",
            style = MaterialTheme.typography.bodyMedium // 원본에서 labelSmall과 bodyMedium이 혼재되어 bodyMedium으로 통일
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = "- 와이파이 제공 여부: ${place.details.wifi.toOXString()}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "- 주차 가능 여부: ${place.details.parking.toOXString()}",
                style = MaterialTheme.typography.bodyMedium
            )

            Text(
                text = "- 키즈존 여부: ${place.details.kidsZone.toOXString()}",
                style = MaterialTheme.typography.bodyMedium
            )

            place.details.signatureMenu?.let { menu ->
                Text(
                    text = "- 시그니처 메뉴: $menu",
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}