package com.example.sohaengsung.ui.features.placeRecommend.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.ui.common.Bookmark
import com.example.sohaengsung.ui.features.placeRecommend.PlaceRecommendScreenEvent
import com.example.sohaengsung.ui.features.placeRecommend.PlaceRecommendViewModel

fun Boolean.toOXString(): String = if (this) "O" else "X"

@Composable
fun PlaceDetailContainer(
    place: Place,
    isBookmarked: Boolean,
    onBookmarkToggle: () -> Unit
) {

    Column (
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {fun Boolean.toOXString(): String = if (this) "O" else "X"

            @Composable
            fun PlaceDetailContainer(
                place: Place,
                viewModel: PlaceRecommendViewModel
            ) {

                val bookmarkIds = viewModel.bookmarkIds.collectAsState()
                val isBookmarked = bookmarkIds.value.contains(place.placeId)

                Column (
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
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
                            isBookmarked = isBookmarked,
                            onBookmarkToggle = onBookmarkToggle
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

                    // 디테일 영역
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        // 1. 와이파이 정보
                        Text(
                            text = "- 와이파이 제공 여부: ${place.details.wifi.toOXString()}",
                            style = MaterialTheme.typography.labelSmall
                        )

                        // 2. 주차 정보
                        Text(
                            text = "- 주차 가능 여부: ${place.details.parking.toOXString()}",
                            style = MaterialTheme.typography.labelSmall
                        )

                        // 3. 키즈존 정보
                        Text(
                            text = "- 키즈존 여부: ${place.details.kidsZone.toOXString()}",
                            style = MaterialTheme.typography.labelSmall
                        )

                        // 4. 시그니처 메뉴 정보 (null이 아닐 경우에만 표시)
                        place.details.signatureMenu?.let { menu ->
                            Text(
                                text = "- 시그니처 메뉴: $menu",
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }
            Text(
                text = place.name,
                style = MaterialTheme.typography.titleSmall
            )

            Bookmark(
                isBookmarked = isBookmarked,
                onBookmarkToggle = {
                    onBookmarkToggle
                }
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

        // 디테일 영역
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            // 1. 와이파이 정보
            Text(
                text = "- 와이파이 제공 여부: ${place.details.wifi.toOXString()}",
                style = MaterialTheme.typography.labelSmall
            )

            // 2. 주차 정보
            Text(
                text = "- 주차 가능 여부: ${place.details.parking.toOXString()}",
                style = MaterialTheme.typography.labelSmall
            )

            // 3. 키즈존 정보
            Text(
                text = "- 키즈존 여부: ${place.details.kidsZone.toOXString()}",
                style = MaterialTheme.typography.labelSmall
            )

            // 4. 시그니처 메뉴 정보 (null이 아닐 경우에만 표시)
            place.details.signatureMenu?.let { menu ->
                Text(
                    text = "- 시그니처 메뉴: $menu",
                    style = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}