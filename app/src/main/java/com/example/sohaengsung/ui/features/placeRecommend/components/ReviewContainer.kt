package com.example.sohaengsung.ui.features.placeRecommend.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sohaengsung.ui.common.ProfilePic
import com.example.sohaengsung.ui.common.StarRating
import com.example.sohaengsung.data.model.GoogleReview


fun getRelativeTime(timeString: String?): String {
    if (timeString == null) return ""

    // 만약 구글 리뷰처럼 이미 날짜 형식 문자열이라면 그대로 반환
    if (timeString.contains("-") || timeString.contains(":")) return timeString

    return try {
        val timeMillis = timeString.toLong()
        val now = System.currentTimeMillis()
        val diff = now - timeMillis

        when {
            diff < 60_000 -> "방금 전"
            diff < 3600_000 -> "${diff / 60_000}분 전"
            diff < 86400_000 -> "${diff / 3600_000}시간 전"
            diff < 2592000_000 -> "${diff / 86400_000}일 전"
            else -> java.text.SimpleDateFormat("yyyy.MM.dd", java.util.Locale.KOREA).format(java.util.Date(timeMillis))
        }
    } catch (e: Exception) {
        timeString // 숫자가 아니면 원본 반환
    }
}

@Composable
fun ReviewContainer(
    review: GoogleReview
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                ProfilePic(
                    imageUrl = review.profilePhotoUrl,
                    size = 32
                )

                Column {
                    Text(
                        text = review.author ?: "익명 사용자",
                        style = MaterialTheme.typography.titleSmall
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        StarRating(
                            rating = review.rating.toDouble(),
                            size = 14 // 별점 크기 조절
                        )
                        Text(
                            text = getRelativeTime(review.time),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // 해시태그 영역
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 태그 없으므로 빈 상태 유지
            }

            // 리뷰 본문
            Text(
                text = review.content ?: "",
                modifier = Modifier.padding(top = 8.dp),
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 20.sp
            )
        }
    }
}