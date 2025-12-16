package com.example.sohaengsung.ui.features.placeRecommend.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.ui.common.Bookmark
import com.example.sohaengsung.ui.features.placeRecommend.PlaceRecommendViewModel
import coil.compose.AsyncImage
import com.example.sohaengsung.ui.features.placeRecommend.PlaceRecommendScreenEvent


@Composable
fun PlaceInfoContainer(
    place: Place,
    onClick: () -> Unit,
    isBookmarked: Boolean,
    onBookmarkToggle: (Place) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp)
            .clickable(onClick = onClick),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Column(
            modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
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
                    style = MaterialTheme.typography.titleMedium
                )

                Bookmark(
                    isBookmarked = isBookmarked,
                    onBookmarkToggle = {
                        onBookmarkToggle(place)
                    }
                )
            }

            // 장소 해시태그
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
                style = MaterialTheme.typography.bodyMedium
            )
        }


        if (place.photoUrls.isNotEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .horizontalScroll(rememberScrollState()) // 가로 스크롤 가능
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp) // 사진 사이 4.dp 간격
            ) {
                place.photoUrls.forEach { url ->
                    AsyncImage(
                        model = url,
                        contentDescription = "${place.name} photo",
                        modifier = Modifier
                            .width(200.dp) // 사진 가로 크기 고정
                            .height(150.dp)
                            .clip(RoundedCornerShape(8.dp)), // 모서리 둥글게
                        contentScale = ContentScale.Crop
                    )
                }
            }
        } else {
            // 사진 없을 때
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(150.dp)
                    .background(MaterialTheme.colorScheme.secondary, RoundedCornerShape(8.dp))
            )
        }
    }
}