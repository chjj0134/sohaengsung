package com.example.sohaengsung.ui.features.level.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ScoreContainer(
    user: com.example.sohaengsung.data.model.User
) {

    val SCORE_PER_BOOKMARK = 3
    val SCORE_PER_REVIEW = 15

    val bookmarkCount = user.bookmarkedPlaces.size
    val totalBookmarkScore = bookmarkCount * SCORE_PER_BOOKMARK

    val totalReviewScore = (user.activityScore - totalBookmarkScore).coerceAtLeast(0)
    val estimatedReviewCount = totalReviewScore / SCORE_PER_REVIEW

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "참여 활동 점수 >",
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.weight(1f))

            Text(
                text = "${user.activityScore}점",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
        }

        ScoreItem(
            icon = Icons.Default.Edit,
            title = "리뷰",
            detail = "별점 입력 및 리뷰 작성 (${estimatedReviewCount}회)",
            score = totalReviewScore,
        )

        ScoreItem(
            icon = Icons.Default.Folder,
            title = "북마크",
            detail = "장소 저장 (${bookmarkCount}회)",
            score = totalBookmarkScore,
        )
    }
}