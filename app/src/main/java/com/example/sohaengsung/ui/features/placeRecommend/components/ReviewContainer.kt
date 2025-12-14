package com.example.sohaengsung.ui.features.placeRecommend.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sohaengsung.ui.common.ProfilePic
import com.example.sohaengsung.ui.common.StarRating
import com.example.sohaengsung.data.model.GoogleReview

//@Preview(showBackground = true)
@Composable
fun ReviewContainer(
    // review: Review
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
            // 프로필 사진, 닉네임 라인
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 예시 프로필 사진 → 실제 리뷰 데이터 적용
                ProfilePic(
                    imageUrl = review.profilePhotoUrl,
                    size = 24
                )

                Text(
                    text = review.author ?: "",
                    style = MaterialTheme.typography.labelLarge
                )
            }

            // 별점, 등록일 순
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    modifier = Modifier
                        .size(24.dp)
                )

                // 예시 별점 → 실제 리뷰 데이터 적용
                StarRating(
                    rating = review.rating.toDouble(),
                    size = 20
                )

                Text(
                    text = review.time ?: "",
                    style = MaterialTheme.typography.labelLarge
                )
            }

            // 해시태그
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 현재는 전달되는 태그가 없으므로 빈 상태 유지
            }

            // 리뷰 본문
            Text(
                text = review.content ?: "",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}
