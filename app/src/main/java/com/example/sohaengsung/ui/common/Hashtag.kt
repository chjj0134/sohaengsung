package com.example.sohaengsung.ui.components.Common

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Hashtag(content: String,
            containerColor: Color,
            contentColor: Color
) {
    Surface(
        shape = RoundedCornerShape(percent = 50),
        color = containerColor, // 배경 색
        tonalElevation = 1.dp,
        border = BorderStroke(0.5.dp, MaterialTheme.colorScheme.primary)
    ) {
        Text(
            text = content,
            style = MaterialTheme.typography.labelLarge,
            color = contentColor, // 글자 색
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 4.dp)
        )
    }
}