package com.example.sohaengsung.ui.features.placeRecommend.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.example.sohaengsung.data.model.Hashtag
import com.example.sohaengsung.ui.features.placeRecommend.PlaceRecommendScreenEvent
import com.example.sohaengsung.ui.features.placeRecommend.PlaceRecommendViewModel

@Composable
fun HashtagListContainer(
    hashtagList: List<Hashtag>,
    selectedHashtags: Set<String>, // Set으로 받음
    viewModel: PlaceRecommendViewModel
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .horizontalScroll(rememberScrollState())
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        hashtagList.forEach { hashtagData ->
            val isSelected = selectedHashtags.contains(hashtagData.name)

            val borderWidth by animateDpAsState(
                targetValue = if (isSelected) 2.5.dp else 0.dp,
                label = "borderWidthAnimation"
            )

            val scale by animateFloatAsState(
                targetValue = if (isSelected) 1.1f else 1.0f,
                label = "scaleAnimation"
            )

            com.example.sohaengsung.ui.common.Hashtag(
                content = "#${hashtagData.name}",
                containerColor = MaterialTheme.colorScheme.tertiary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .graphicsLayer(
                        scaleX = scale,
                        scaleY = scale
                    )
                    .border(
                        width = borderWidth,
                        color = MaterialTheme.colorScheme.onPrimary,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable {
                        viewModel.onEvent(PlaceRecommendScreenEvent.onHashtagClick(hashtagData))
                    }
            )
        }
    }
}