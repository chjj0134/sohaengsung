package com.example.sohaengsung.ui.components.Common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


// 드롭다운 버튼 컴포저블
@Composable
fun DropdownButton(
    content: String,
    containerColor: Color,
    contentColor: Color,
    onClick: () -> Unit // 클릭 이벤트 처리 람다 추가
) {
    Surface(
        shape = RoundedCornerShape(percent = 50),
        color = containerColor,
        tonalElevation = 1.dp,
        border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.primary),
        modifier = Modifier.clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 드롭다운 텍스트
            Text(
                text = content,
                style = MaterialTheme.typography.labelLarge,
                color = contentColor,
            )

            // 텍스트와 아이콘 사이 간격
            Spacer(modifier = Modifier.width(3.dp))

            // 드롭다운 아이콘
            Icon(
                imageVector = Icons.Filled.KeyboardArrowDown,
                contentDescription = "드롭다운 메뉴 열기",
                tint = contentColor,
                modifier = Modifier.width(16.dp)
            )
        }
    }
}

@Composable
fun Dropdown(
    label: String, // 초기 내용 (유형별, 리뷰순)
    items: List<String>, // 선택 가능 리스트 (카페, 서점, 소품샵...)
    initialSelection: String? = null, // 초기 선택 값
    containerColor: Color,
    contentColor: Color
) {
    // 열림/닫힘 상태
    var expanded by remember { mutableStateOf(false) }
    // 초기 선택 값이 없으면 label을, 있으면 초기 선택 값을 사용
    var selectedText by remember { mutableStateOf(initialSelection ?: label) }

    // DropdownMenu를 DropdownButton 위에 배치
    Box(
        modifier = Modifier,
        contentAlignment = Alignment.TopStart
    ) {
        // 위에서 정의해 둔 DropdownButton (클릭 가능한 부분)
        DropdownButton(
            content = selectedText, // 현재 선택된 텍스트 표시
            containerColor = containerColor,
            contentColor = contentColor,
            onClick = { expanded = true } // 클릭하면 메뉴 열림
        )

        // 4. DropdownMenu (선택 리스트)
        DropdownMenu(
            expanded = expanded, // 확장 상태
            onDismissRequest = { expanded = false } // 메뉴 바깥을 클릭하면 닫힘
        ) {
            items.forEach { selectionOption ->
                DropdownMenuItem(
                    text = { Text(selectionOption) },
                    onClick = {
                        // 항목 선택 시 상태 변경
                        selectedText = selectionOption // 텍스트 업데이트
                        expanded = false // 메뉴 닫기
                    },
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}