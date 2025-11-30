package com.example.sohaengsung.ui.features.level.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Folder
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ScoreContainer(
    totalScore: Int
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp) // 전체 패딩
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
                text = "${totalScore}점",
                style = MaterialTheme.typography.titleSmall,
            )
        }

        // 개별 스코어 우선 하드 코딩
        ScoreItem(
            icon = Icons.Default.Edit,
            title = "리뷰",
            detail = "작성, 수정제안, 피드백",
            score = 15,
        )

        ScoreItem(
            icon = Icons.Default.Folder,
            title = "북마크",
            detail = "공개그룹 생성, 장소 저장",
            score = 3,
        )

        ScoreItem(
            icon = Icons.Default.Add,
            title = "장소 제안",
            detail = "신규 장소 등록, 삭제, 수정",
            score = 2,
        )
    }
}