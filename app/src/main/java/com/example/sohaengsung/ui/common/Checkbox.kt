package com.example.sohaengsung.ui.common

import android.R.attr.checked
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckBox
import androidx.compose.material.icons.filled.CheckBoxOutlineBlank
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CheckBox(
    isChecked: Boolean, // 초기 상태
    onClick: () -> Unit
) {
    // 채워진 아이콘 or 빈 아이콘
    val icon = if (isChecked) Icons.Filled.CheckBox else Icons.Filled.CheckBoxOutlineBlank

    Box(
        modifier = Modifier
            .size(24.dp)
            .clickable {
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = if (isChecked) "체크 해제" else "체크",
            tint = MaterialTheme.colorScheme.tertiary,
            modifier = Modifier.size(24.dp)
        )
    }
}