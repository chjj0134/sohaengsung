package com.example.sohaengsung.ui.components.Common

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun CustomVerticalDivider(
    color: Color,
    height: Int
) {
    VerticalDivider(
        Modifier
            .height(height.dp)
            .fillMaxHeight(),
        1.dp,
        color = color
    )
}