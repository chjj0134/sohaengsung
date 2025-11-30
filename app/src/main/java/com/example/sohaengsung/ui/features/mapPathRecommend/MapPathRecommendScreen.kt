package com.example.sohaengsung.ui.features.mapPathRecommend

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sohaengsung.ui.common.CustomTopBar
import com.example.sohaengsung.ui.features.mapPathRecommend.components.PathMapView
import com.example.sohaengsung.ui.features.mapPathRecommend.components.PathPlaceItem
import com.example.sohaengsung.ui.theme.SohaengsungTheme
import com.google.firebase.auth.FirebaseAuth

@Composable
fun MapPathRecommendScreen(
    uid: String? = null,
    viewModel: MapPathRecommendViewModel = viewModel(
        factory = MapPathRecommendViewModelFactory(
            uid = uid ?: FirebaseAuth.getInstance().currentUser?.uid ?: "dummy-user-id",
            context = LocalContext.current
        )
    )
) {
    val uiState by viewModel.uiState.collectAsState()

    SohaengsungTheme {
        Scaffold(
            topBar = {
                CustomTopBar(contentText = "경로 추천")
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                when {
                    uiState.isLoading -> {
                        // 로딩 중
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                        ) {
                            CircularProgressIndicator()
                            Text(
                                text = "경로를 계산하는 중...",
                                modifier = Modifier.padding(top = 16.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }

                    uiState.errorMessage != null -> {
                        // 에러 메시지
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                        ) {
                            Text(
                                text = uiState.errorMessage ?: "오류가 발생했습니다.",
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.error
                            )
                        }
                    }

                    uiState.sortedPlaces.isEmpty() -> {
                        // 데이터 없음
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                        ) {
                            Text(
                                text = "북마크된 장소가 없습니다.",
                                style = MaterialTheme.typography.bodyLarge
                            )
                        }
                    }

                    else -> {
                        // 정상 표시
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState())
                        ) {
                            // 지도 영역
                            PathMapView(
                                currentLocation = uiState.currentLocation,
                                sortedPlaces = uiState.sortedPlaces.map { it.place },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(400.dp)
                            )

                            // 경로 상세 정보
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 16.dp)
                            ) {
                                uiState.sortedPlaces.forEach { placeWithOrder ->
                                    PathPlaceItem(placeWithOrder = placeWithOrder)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

