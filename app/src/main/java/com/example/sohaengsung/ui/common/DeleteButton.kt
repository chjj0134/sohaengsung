package com.example.sohaengsung.ui.common

import android.R.attr.checked
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DeleteButton(
    onDeleteClick: () -> Unit
) {
    // 채워진 아이콘 or 빈 아이콘
    val icon = Icons.Filled.Delete

    Box(
        modifier = Modifier
            .size(24.dp)
            .clickable {
                onDeleteClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "삭제",
            tint = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.size(24.dp)
        )
    }
}