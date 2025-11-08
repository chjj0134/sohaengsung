package com.example.sohaengsung.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sohaengsung.ui.theme.SohaengsungTheme

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
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.secondary),

        // 캘린더 아이콘
        leadingIcon = {
            Icon(
                imageVector = Icons.Filled.CalendarMonth,
                contentDescription = "행사 기간 선택",
                tint = MaterialTheme.colorScheme.primary,
                // 캘린더 아이콘 클릭 이벤트 처리
                modifier = Modifier
                    .clickable { onCalendarClick() }
            )
        },

        // 검색 아이콘
        trailingIcon = {
            Icon(
                imageVector = Icons.Filled.Search,
                contentDescription = "검색",
                tint = MaterialTheme.colorScheme.primary,
            )
        },
        singleLine = true
    )
}


//@Preview(showBackground = true)
//@Composable
//fun PreviewSearchBar() {
//    var searchText by remember { mutableStateOf("") }
//
//    SohaengsungTheme {
//        SearchBar(
//            query = searchText,
//            onQueryChange = { newText ->
//                searchText = newText // 텍스트 변경 시 상태 업데이트
//                println("검색어 입력: $newText")
//            },
//            onCalendarClick = {
//                // 캘린더 아이콘 클릭 시의 임시 로직
//                println("캘린더 아이콘이 클릭되었습니다! 행사 기간을 선택하세요.")
//            }
//        )
//    }
//}