package com.example.sohaengsung.ui.features.review

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Alignment
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sohaengsung.ui.common.CustomTopBar
import com.example.sohaengsung.ui.features.review.components.StarRating
import com.example.sohaengsung.ui.features.review.components.TagDropdown
import com.example.sohaengsung.ui.theme.SohaengsungTheme

@Composable
fun ReviewScreen(
    onNavigate: (route: ReviewScreenEvent.Navigation) -> Unit,
    viewModel: ReviewViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val event by viewModel.events.collectAsState()

    LaunchedEffect(event) {
        event?.let { navigationEvent ->
            onNavigate(navigationEvent)
            viewModel.clearEvent()
        }
    }

    // 태그 더미 데이터
    val themeTags = listOf("카페", "책", "갤러리", "음식점", "소품")
    val atmosphereTags = listOf(
        "카공하기좋은",
        "아늑한",
        "대화하기 좋은",
        "조용한",
        "레트로",
        "특색있는",
        "집중하기 좋은",
        "단체모임하기 좋은",
        "뷰가 좋은"
    )
    val convenienceTags = listOf("콘센트좌석", "노트북", "와이파이 제공", "주차장")

    SohaengsungTheme {
        Scaffold(
            topBar = {
                CustomTopBar(contentText = "리뷰 등록하기")
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(modifier = Modifier.height(8.dp))

                // 서브타이틀 (가운데 정렬)
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "방문한 장소의 후기를 작성해 주세요",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 별점 (가운데 정렬)
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    StarRating(
                        rating = uiState.rating,
                        onRatingClick = { rating ->
                            viewModel.updateRating(rating)
                        }
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // 리뷰 입력란
                Text(
                    text = "리뷰 작성",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                TextField(
                    value = uiState.reviewText,
                    onValueChange = { text ->
                        viewModel.updateReviewText(text)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp),
                    placeholder = {
                        Text(
                            text = "자유롭게 리뷰를 작성해주세요",
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    },
                    shape = RoundedCornerShape(8.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = MaterialTheme.colorScheme.outline
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    maxLines = Int.MAX_VALUE
                )

                Spacer(modifier = Modifier.height(32.dp))

                // 태그 선택
                Text(
                    text = "태그 선택(최대 6개)",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(16.dp))

                // 태그 드롭다운들
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TagDropdown(
                        label = "장소 테마",
                        items = themeTags,
                        selectedItem = uiState.selectedThemeTag,
                        onItemClick = { tag ->
                            viewModel.toggleThemeTag(tag)
                        },
                        modifier = Modifier.weight(1f)
                    )

                    TagDropdown(
                        label = "분위기",
                        items = atmosphereTags,
                        selectedItem = uiState.selectedAtmosphereTag,
                        onItemClick = { tag ->
                            viewModel.toggleAtmosphereTag(tag)
                        },
                        modifier = Modifier.weight(1f)
                    )

                    TagDropdown(
                        label = "편의사항",
                        items = convenienceTags,
                        selectedItem = uiState.selectedConvenienceTag,
                        onItemClick = { tag ->
                            viewModel.toggleConvenienceTag(tag)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))

                // 리뷰 등록 버튼
                Button(
                    onClick = {
                        viewModel.submitReview()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = RoundedCornerShape(8.dp),
                    enabled = !uiState.isLoading
                ) {
                    Text(
                        text = if (uiState.isLoading) "등록 중..." else "리뷰 등록",
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

