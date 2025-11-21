package com.example.sohaengsung.ui.features.logIn.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun LoginButton(
    contentText: String,
    containerColor: Color,
    contentColor: Color,
    onClick: () -> Unit
) {
    Button (
        modifier = Modifier
            .width(200.dp)
            .height(60.dp)
            .padding(horizontal = 4.dp, vertical = 4.dp),
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        shape = RoundedCornerShape(50),
        elevation = ButtonDefaults.buttonElevation(
            defaultElevation = 4.dp
        )
    ) {
        Text(
            text = contentText,
            style = MaterialTheme.typography.bodyMedium
        )
    }
}