package com.example.sohaengsung.ui.features.pathRecommend

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sohaengsung.ui.common.BottomActionButtton
import com.example.sohaengsung.ui.common.CustomDivider
import com.example.sohaengsung.ui.common.Dropdown
import com.example.sohaengsung.ui.common.CustomTopBar
import com.example.sohaengsung.ui.features.pathRecommend.components.PlaceForPathContainer
import com.example.sohaengsung.ui.features.placeRecommend.PlaceRecommendScreenEvent
import com.example.sohaengsung.ui.theme.SohaengsungTheme

@Composable
fun PathRecommendScreen(
    uid: String,
    onNavigate: (PathRecommendScreenEvent.Navigation) -> Unit,
) {
    val viewModel: PathRecommendViewModel = viewModel(
        factory = PathRecommendViewModelFactory(uid))
    val bookmarkPlaces by viewModel.bookmarkPlaces.collectAsState()

    // val uiState by viewModel.uiState.collectAsState()
    val event by viewModel.events.collectAsState()

    LaunchedEffect(event) {
        event?.let { navigationEvent ->
            onNavigate(navigationEvent)
            viewModel.clearEvent()
        }
    }

    SohaengsungTheme {
        Scaffold(
            topBar = {
                CustomTopBar(contentText = "경로 추천")
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {

                Column(
                    modifier = Modifier
                        .padding(horizontal = 20.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(
                        "나의 취향 모음집",
                        modifier = Modifier
                            .padding(32.dp),
                        style = MaterialTheme.typography.bodyLarge,
                    )

                    CustomDivider(MaterialTheme.colorScheme.secondary)
                }

                // 드롭다운
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.Absolute.Right
                ) {
                    Dropdown(
                        label = "거리순",
                        items = listOf("거리순", "별점높은순", "리뷰많은순"),
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        onItemSelected = {
                                // 장소 추천에서 로직 가져다 복붙
                        }
                    )
                }

                bookmarkPlaces.forEach { place ->
                    PlaceForPathContainer(
                        place = place,
                        onCheckBoxClick = { place ->
                            viewModel.onEvent(PathRecommendScreenEvent.onCheckboxClick) }
                        )
                }

            }

            // 쿠폰 확인 버튼
            Box(
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxSize(),
                contentAlignment = Alignment.BottomEnd
            ) {

                // 경로 작성 버튼
                BottomActionButtton(
                    onClickAction = {
                        viewModel.onEvent(
                            PathRecommendScreenEvent.onPathComposeClick
                        )
                    },
                    icon = Icons.Filled.Create,
                    text = "경로 작성",
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }
        }
    }
}