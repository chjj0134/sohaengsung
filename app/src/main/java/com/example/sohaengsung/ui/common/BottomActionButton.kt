package com.example.sohaengsung.ui.common

import android.R.attr.onClick
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun BottomActionButtton(
    onClickAction: () -> Unit,
    icon: ImageVector,
    text: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(MaterialTheme.shapes.extraSmall)
            .clickable(onClick = onClickAction) // 클릭 이벤트 처리
            .padding(4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 원형 배경 아이콘
        Box(
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.onPrimary)
                .padding(10.dp),
            contentAlignment = Alignment.Center
        ) {
            // 아이콘
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.size(38.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        // 버튼 내용 텍스트
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall
        )
    }
}
