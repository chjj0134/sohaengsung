package com.example.sohaengsung.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Bookmark(
    initialChecked: Boolean, // 초기 상태
    onBookmarkToggle: (Boolean) -> Unit
) {
    var checked by remember { mutableStateOf(initialChecked) }
    val icon = if (checked) Icons.Filled.Bookmark else Icons.Filled.BookmarkBorder // 채워진 아이콘 or 빈 아이콘

    Box(
        modifier = Modifier
            .size(24.dp)
            .clickable {
                checked = !checked
                onBookmarkToggle(checked)
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = if (checked) "북마크 해제" else "북마크",
            tint = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.size(24.dp)
        )
    }
}

//@Preview
//@Composable
//fun PreviewBookmarkButton() {
//    Column {
//        Text("초기 상태: 북마크됨 (true)")
//        Bookmark(
//            initialChecked = true,
//            onBookmarkToggle = { /* 작업 내용(예시: viewModel.updateBookmark(storeId, isChecked)) */ }
//        )
//        Spacer(modifier = Modifier.height(16.dp))
//        Text("초기 상태: 북마크 안됨 (false)")
//        Bookmark(
//            initialChecked = false, // false로 시작
//            onBookmarkToggle = { /* 작업 내용 */ }
//        )
//    }
//}