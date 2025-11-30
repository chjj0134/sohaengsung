package com.example.sohaengsung.ui.features.placeRecommend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.ui.common.Bookmark
import com.example.sohaengsung.ui.features.placeRecommend.PlaceRecommendViewModel

@Composable
fun PlaceInfoContainer(
    place: Place,
    onClick: () -> Unit,
    viewModel: PlaceRecommendViewModel
) {
    val bookmarkIds by viewModel.bookmarkIds.collectAsState()
    val isBookmarked = bookmarkIds.contains(place.placeId)

    Column (
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            // 장소 이름 및 북마크
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = place.name,
                    style = MaterialTheme.typography.titleSmall
                )

                Bookmark(
                    initialChecked = isBookmarked,
                    onBookmarkToggle = {  viewModel.toggleBookmark(place)}
                )
            }

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
                    .padding(4.dp)
                    .height(150.dp)
                    .background(MaterialTheme.colorScheme.secondary),
            )
        }
    }