package com.example.sohaengsung.ui.features.pathRecommend.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.ui.components.Common.CheckBox
import com.example.sohaengsung.ui.components.Common.CustomDivider

@Composable
fun PlaceForPathContainer(
    place: Place,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp),
    ) {

        CustomDivider(MaterialTheme.colorScheme.secondary)

        // 개별 장소 컴포넌트
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 32.dp, horizontal = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // 체크박스
            CheckBox(
                initialChecked = false,
                onBookmarkToggle = {
                    /* 작업 내용(예시: viewModel.updateBookmark(storeId, isChecked)) */
                }
            )

            // 장소 이름, 해시태그
            Column(
                modifier = Modifier
                    .weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = place.name,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    // PlaceExample.hashtags의 모든 아이템에 "#"와 공백을 붙여 하나의 문자열로 결합
                    text = place.hashtags.joinToString(separator = " ") { hashtag ->
                        "#$hashtag"
                    },
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // 임시 거리
            Text(
                "160m",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}