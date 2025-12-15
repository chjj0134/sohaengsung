package com.example.sohaengsung.ui.features.event.components

import android.R.attr.fontWeight
import android.R.attr.maxLines
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import com.example.sohaengsung.data.model.Event

@Composable
fun EventCard(
    event: Event,
    onCardClick: (Event) -> Unit
) {
    val context = LocalContext.current

    val textGradientBrush = Brush.horizontalGradient(
        listOf(
            MaterialTheme.colorScheme.primary.copy(alpha = 0.2f),   // 시작 색상
            MaterialTheme.colorScheme.tertiary.copy(alpha = 0.2f) // 끝 색상
        )
    )
    
    Card(
        modifier = Modifier
            .width(280.dp)
            .height(330.dp)
            .padding(horizontal = 8.dp, vertical = 8.dp)
            .clickable {
                // 외부 링크로 이동
                if (event.externalLink.isNotEmpty()) {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.externalLink))
                    context.startActivity(intent)
                }
                onCardClick(event)
            },
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Transparent)
        ) {
            // 이미지 영역
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)
            ) {
                if (event.imageUrl.isNotEmpty()) {
                    SubcomposeAsyncImage(
                        model = event.imageUrl,
                        contentDescription = event.title,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        loading = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.surfaceVariant),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "로딩 중...",
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        },
                        error = {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "이미지 로드 실패",
                                    textAlign = TextAlign.Center,
                                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                        }
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.primary),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "이미지 없음",
                            textAlign = TextAlign.Center,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }

            // 정보 영역
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(brush = textGradientBrush)
                    .padding(horizontal = 16.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column (
                    Modifier
                    .background(Color.Transparent)
                ) {

                    // 해시태그 목록
                    Row (
                    ) {
                        event.tags.forEach { tag ->
                            Text(
                                text = "#${tag} ",
                                color = MaterialTheme.colorScheme.primary,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Bold,
                                ),
                            )
                        }
                    }

                    // 행사명
                    Text(
                        text = event.title,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.typography.titleSmall,
                        // modifier = Modifier.padding(bottom = 4.dp)
                    )

                }

                Column (
                    Modifier
                        .background(Color.Transparent),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {

                    // 기간
                    Text(
                        text = "기간: ${event.seasonInfo}",
                        style = MaterialTheme.typography.bodySmall,
                        // modifier = Modifier.padding(bottom = 4.dp)
                    )

                    // D-Day 또는 진행 중
                    Text(
                        text = event.countdown.ifEmpty { "D-20" },
                        style = MaterialTheme.typography.bodySmall.copy(
                            fontWeight = FontWeight.Bold,
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
