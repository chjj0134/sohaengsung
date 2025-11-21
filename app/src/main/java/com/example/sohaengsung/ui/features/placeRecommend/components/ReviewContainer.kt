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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sohaengsung.ui.components.Common.ProfilePic
import com.example.sohaengsung.ui.components.Common.StarRating
import com.example.sohaengsung.ui.dummy.reviewExample
import com.example.sohaengsung.ui.dummy.userExample

@Preview(showBackground = true)
@Composable
fun ReviewContainer(
    // review: Review
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
                // 예시 프로필 사진
                ProfilePic(userExample, 24)

                Text(
                    text = "${userExample.nickname}",
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

                // 예시 별점
                StarRating(
                    rating = reviewExample[1].rating,
                    size = 20
                )

                Text(
                    text = "${reviewExample[1].createdAt}",
                    style = MaterialTheme.typography.labelLarge
                )
            }

            // 해시태그
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                reviewExample[1].tags.map { tag ->
                    Text(
                        text = "#${tag}", // 해시태그 이름 앞에 # 붙이기
                        color = MaterialTheme.colorScheme.primary,
                    )
                }
            }

            // 리뷰 본문
            Text(
                text = "${reviewExample[1].content}"
            )
        }
    }
}