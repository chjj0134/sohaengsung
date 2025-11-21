package com.example.sohaengsung.ui.features.setting.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.sohaengsung.data.model.User
import com.example.sohaengsung.ui.components.Common.CustomVerticalDivider

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
                    fontWeight = FontWeight.Bold
                ),
                color = Color.Black
            )

            // 레벨 및 등급 확인 통합 버튼
            Button(
                onClick = { onLevelDetailClick() },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFE5EEF2),
                    contentColor = Color.Black
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 1. 왼쪽 그룹: 별 아이콘과 레벨 텍스트
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = "레벨",
                        modifier = Modifier.size(16.dp),
                        tint = Color(0xFF9DADC9) // 별 아이콘은 지정된 색상 유지
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "레벨 ${user.level}",
                        style = MaterialTheme.typography.bodyMedium
                    )

                    CustomVerticalDivider(
                        MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier
                            .height(16.dp)
                            .padding(horizontal = 6.dp),
                    )

                    // 2. 오른쪽 그룹: 등급 확인 텍스트와 화살표
                    Text(
                        text = "등급 확인하러 가기",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            textDecoration = TextDecoration.Underline
                        )
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "이동",
                        modifier = Modifier.size(14.dp)
                    )
                }
            }
        }
    }
}