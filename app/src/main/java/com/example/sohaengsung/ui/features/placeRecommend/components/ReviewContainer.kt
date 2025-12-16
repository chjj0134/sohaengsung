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
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


fun getRelativeTime(timeString: String?): String {
    if (timeString.isNullOrBlank()) return ""

    val targetFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA)

    return try {
        if (timeString.all { it.isDigit() }) {
            // 1. 숫자로만 된 경우 (Long Timestamp)
            targetFormat.format(Date(timeString.toLong()))
        } else {
            // 2. "Tue Dec 16..." 같은 시스템 문자열 형식인 경우
            // 이 형식은 기본 EEE MMM dd HH:mm:ss zzz yyyy 포맷을 사용합니다.
            val sourceFormat = SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)
            val parsedDate = sourceFormat.parse(timeString)
            parsedDate?.let { targetFormat.format(it) } ?: timeString
        }
    } catch (e: Exception) {
        // 변환 실패 시 원본 혹은 날짜 부분만 대략적으로 반환 시도
        timeString.take(10) // 최소한의 정보만 출력
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