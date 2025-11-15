package com.example.sohaengsung.ui.components.Setting

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.sohaengsung.data.model.User

@Composable
fun ProfileSettingContainer(
    user: User,
    onLevelDetailClick: () -> Unit = {},
    onProfileEditClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .padding(horizontal = 20.dp, vertical = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 프로필 사진 (편집 아이콘 포함)
        EditableProfilePic(
            user = user,
            size = 80,
            onEditClick = onProfileEditClick
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // 사용자 이름
            Text(
                text = user.nickname ?: "사용자",
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                ),
                color = Color.Black
            )

            // 레벨 및 등급 확인 버튼
            Row(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 별 아이콘
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "레벨",
                    modifier = Modifier.size(16.dp),
                    tint = Color(0xFFFFD700) // 노란색
                )

                // 레벨 텍스트
                Text(
                    text = "레벨 ${user.level}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.width(8.dp))

                // 등급 확인 버튼
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = Color.Transparent,
                    modifier = Modifier.clickable { onLevelDetailClick() }
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "등급 확인하러 가기",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color.Black
                        )
                        Icon(
                            imageVector = Icons.Default.ArrowForward,
                            contentDescription = "이동",
                            modifier = Modifier.size(14.dp),
                            tint = Color.Black
                        )
                    }
                }
            }
        }
    }
}