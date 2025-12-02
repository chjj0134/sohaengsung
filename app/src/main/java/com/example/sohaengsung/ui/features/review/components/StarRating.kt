package com.example.sohaengsung.ui.features.review.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun StarRating(
    rating: Int,
    onRatingClick: (Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier) {
        for (index in 1..5) {
            val starIndex = index
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = "별점 $starIndex",
                tint = if (starIndex <= rating) {
                    MaterialTheme.colorScheme.primary
                } else {
                    Color.Gray.copy(alpha = 0.3f)
                },
                modifier = Modifier
                    .size(40.dp)
                    .clickable { onRatingClick(starIndex) }
            )
            if (index < 5) {
                Spacer(modifier = Modifier.width(4.dp))
            }
        }
    }
}

