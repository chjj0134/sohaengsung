package com.example.sohaengsung.ui.features.setting.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.MaterialTheme
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
fun EditableProfilePic(
    user: User,
    size: Int = 80,
    onEditClick: () -> Unit = {}
) {
    val imageSize = size.dp
    val editIconSize = 24.dp

    Box(
        modifier = Modifier.size(imageSize)
    ) {
        // 프로필 사진
        Box(
            modifier = Modifier
                .size(imageSize)
                .clip(CircleShape)
                .background(Color(0xFFFFE5E5)), // 연한 핑크 배경
            contentAlignment = Alignment.Center
        ) {
            if (!user.profilePic.isNullOrEmpty()) {
                AsyncImage(
                    model = user.profilePic,
                    contentDescription = "${user.nickname}님의 프로필 사진",
                    modifier = Modifier
                        .fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            } else {
                Icon(
                    imageVector = Icons.Default.Person,
                    contentDescription = "기본 프로필 아이콘",
                    modifier = Modifier.size(imageSize * 0.7f),
                    tint = Color.DarkGray
                )
            }
        }

        // 편집 아이콘 (오른쪽 하단에 붙이기)
        // 편집 기능 없앰
//        Box(
//            modifier = Modifier
//                .size(editIconSize)
//                .align(Alignment.BottomEnd)
//                .clip(CircleShape)
//                .background(MaterialTheme.colorScheme.surface)
//                .border(1.dp, Color.Gray, CircleShape)
//                .clickable { onEditClick() },
//            contentAlignment = Alignment.Center
//        ) {
//            Icon(
//                imageVector = Icons.Default.Edit,
//                contentDescription = "프로필 사진 편집",
//                modifier = Modifier.size(editIconSize * 0.6f),
//                tint = Color.Gray
//            )
//        }
    }
}

