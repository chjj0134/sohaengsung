package com.example.sohaengsung.ui.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun IndentedText(text: String) {
    Row(
        Modifier.padding(horizontal = 12.dp)
    ) {
        // 텍스트만 표시 (별도의 불렛 포인트 문자 없음)
        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall
        )
    }
}