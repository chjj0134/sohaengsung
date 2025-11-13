package com.example.sohaengsung.ui.components.PlaceRecommend

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sohaengsung.data.model.Hashtag
import kotlin.collections.map

@Composable
fun HashtagListContainer(HashtagList01: List<Hashtag>, HashtagList02: List<Hashtag>) {
    Column(
        modifier = Modifier
            .padding(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        // 해시태그 첫 번째 줄
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()), // 가로 스크롤 가능
            horizontalArrangement = Arrangement.spacedBy(8.dp), // 8.dp 간격으로 해시태그 배치
            verticalAlignment = Alignment.CenterVertically
        ) {
            // HashtagListExample을 map으로 분해하여 Hashtag 컴포저블에 배치
            HashtagList01.map { hashtagData ->
                com.example.sohaengsung.ui.components.Common.Hashtag(
                    content = "#${hashtagData.name}", // 해시태그 이름 앞에 # 붙이기
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        // 해시태그 두 번째 줄
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()), // 가로 스크롤 가능
            horizontalArrangement = Arrangement.spacedBy(8.dp), // 8.dp 간격으로 해시태그 배치
            verticalAlignment = Alignment.CenterVertically
        ) {
            // HashtagListExample을 map으로 분해하여 Hashtag 컴포저블에 배치
            HashtagList02.map { hashtagData ->
                com.example.sohaengsung.ui.components.Common.Hashtag(
                    content = "#${hashtagData.name}", // 해시태그 이름 앞에 # 붙이기
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                )
            }
        }
    }
}