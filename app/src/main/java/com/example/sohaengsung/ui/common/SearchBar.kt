package com.example.sohaengsung.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp

@Composable
fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onCalendarClick: () -> Unit
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange, // 텍스트 변경 이벤트 처리
        placeholder = { Text("search event") },
        shape = RoundedCornerShape(30.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),

        // 캘린더 아이콘
//        leadingIcon = {
//            Icon(
//                imageVector = Icons.Filled.CalendarMonth,
//                contentDescription = "행사 기간 선택",
//                tint = MaterialTheme.colorScheme.primary,
//                // 캘린더 아이콘 클릭 이벤트 처리
//                modifier = Modifier
//                    .clickable { onCalendarClick() }
//                    .size(30.dp)
//            )
//        },

        // 검색 아이콘
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "검색",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .size(30.dp)
            )
        },
        singleLine = true
    )
}