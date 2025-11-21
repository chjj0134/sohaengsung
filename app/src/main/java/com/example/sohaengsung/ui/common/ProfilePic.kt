package com.example.sohaengsung.ui.components.Common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.sohaengsung.data.model.User

@Composable
fun ProfilePic(
    user: User,
    size: Int
) {
    // size를 dp 단위로 변환
    val imageSize = size.dp

    // user.profilePic에 값이 없거나 비어 있는 경우
    Box(
        modifier = Modifier
            .size(imageSize) // 전체 크기 설정
            .clip(CircleShape) // 원형으로
            .background(Color.LightGray), // 사진이 없을 때 배경색
        contentAlignment = Alignment.Center
    ) {
        // user.profilePic이 null이거나 빈 문자열이 아닐 때만 이미지를 로드
        if (user.profilePic.isNullOrEmpty().not()) {
            AsyncImage(
                model = user.profilePic, // 프로필 사진 URL
                contentDescription = "${user.uid}님의 프로필 사진",
                modifier = Modifier
                    .matchParentSize(), // Box 크기에 맞춤
                contentScale = ContentScale.Crop,
            )
        } else {
            // profilePic이 null이거나 비어 있을 때 기본 아이콘
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "기본 프로필 아이콘",
                modifier = Modifier.size(imageSize * 0.7f),
                tint = Color.DarkGray
            )
        }
    }
}