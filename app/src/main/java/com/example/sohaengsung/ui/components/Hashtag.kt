package com.example.sohaengsung.ui.components

import android.R.id.primary
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sohaengsung.ui.theme.SohaengsungTheme

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
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
        )
    }
}


// 테스트 코드
//@Preview(showBackground = true)
//@Composable
//fun HashtagPreview() {
//    SohaengsungTheme {
//        Hashtag(
//            content = "인스타맛집",
//            containerColor = MaterialTheme.colorScheme.primary,
//            contentColor = MaterialTheme.colorScheme.onPrimary
//        )
//    }
//}