package com.example.sohaengsung.ui.components.Setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.sohaengsung.data.model.User
import com.example.sohaengsung.ui.components.Common.CustomVerticalDivider
import com.example.sohaengsung.ui.components.Common.ProfilePic
import com.example.sohaengsung.ui.dummy.userExample

@Composable
fun ProfileSettingContainer(
    user: User
) {
    Row(
        modifier = Modifier
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 프로필 사진
        ProfilePic(user, 80)

        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "${user.nickname}",
                modifier = Modifier
                    .padding(horizontal = 4.dp),
                style = MaterialTheme.typography.titleMedium
            )

            Surface (
                shape = RoundedCornerShape(percent = 50),
                color = MaterialTheme.colorScheme.secondary,
                tonalElevation = 1.dp,
            ) {
                Row(
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 4.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text(
                        text = "레벨 ${user.level}",
                        style = MaterialTheme.typography.labelLarge,
                        color = MaterialTheme.colorScheme.onSecondary,
                    )

                    // 세로 커스텀 디바이더
                    CustomVerticalDivider(
                        MaterialTheme.colorScheme.onSecondary,
                        15
                    )

                    Text(
                        text = "등급 확인하러 가기",
                        modifier = Modifier
                            .clickable{
                                // 등급 확인 페이지로 이동
                            },
                        style = MaterialTheme.typography.labelLarge.copy(
                            textDecoration = TextDecoration.Underline
                        ),
                        color = MaterialTheme.colorScheme.onSecondary,
                    )
                }
            }
        }
    }
}