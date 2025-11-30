package com.example.sohaengsung.ui.features.event.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.sohaengsung.data.model.Event

@Composable
fun EventCard(
    event: Event,
    onCardClick: (Event) -> Unit
) {
    Card(
        modifier = Modifier
            .width(320.dp) // 화면 전체 너비의 80%
            .height(300.dp) // 화면 전체 너비의 80%
            .padding(16.dp)
            .background(MaterialTheme.colorScheme.onPrimary)
            .clickable {
                onCardClick(event)
            },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)
                    .background(MaterialTheme.colorScheme.primary)
            ) {
                // 사진 대체 임시 placeholder
                Text(
                    text = "이미지 Placeholder",
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }


            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {

                // 해시태그 목록
                Row() {
                event.tags.map { tag ->
                        Text(
                            text = "#${tag} ", // 해시태그 이름 앞에 # 붙이기
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }

                // 행사명
                Text(
                    text = event.title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                // 기간
                Text(
                    text = "기간: ${event.seasonInfo}",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(bottom = 4.dp)
                )

                Text(
                    text = "D-20", // D-Day 계산 로직 대신 하드코딩된 값으로 대체
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}
