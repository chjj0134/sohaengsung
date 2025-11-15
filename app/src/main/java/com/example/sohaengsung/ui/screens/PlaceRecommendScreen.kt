package com.example.sohaengsung.ui.screens

import CustomTopBar
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.ui.components.PlaceRecommend.CustomContainer
import com.example.sohaengsung.ui.components.Common.CustomDivider
import com.example.sohaengsung.ui.components.Common.Dropdown
import com.example.sohaengsung.ui.components.PlaceRecommend.HashtagListContainer
import com.example.sohaengsung.ui.components.PlaceRecommend.PlaceDetailSheet
import com.example.sohaengsung.ui.components.PlaceRecommend.PlaceInfoContainer
import com.example.sohaengsung.ui.dummy.HashtagListExample01
import com.example.sohaengsung.ui.dummy.HashtagListExample02
import com.example.sohaengsung.ui.dummy.placeExample
import com.example.sohaengsung.ui.theme.SohaengsungTheme

@Composable
fun PlaceRecommendScreen() {

    var isSheetOpen by remember { mutableStateOf(false) }
    var selectedPlace by remember { mutableStateOf<Place?>(null) }

    SohaengsungTheme {
        Scaffold(
            topBar = {
                CustomTopBar(contentText = "내 주변 장소 추천") // 임포트해서 재사용
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                // 지도 컴포넌트 임시 영역
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp) // 높이 임의 설정 중
                        .background(MaterialTheme.colorScheme.secondary),
                )
                {
                    Column (
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(horizontal = 8.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {

                        // 해시태그 영역
                        HashtagListContainer(
                            HashtagListExample01,
                            HashtagListExample02
                        )

                        // 드롭다운 영역
                        Row(
                            modifier = Modifier
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Dropdown(
                                label = "유형별",
                                items = listOf("카페", "스터디", "도서관", "야외"),
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )

                            Dropdown(
                                label = "거리순",
                                items = listOf("별점높은순", "리뷰많은순"),
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }

                CustomContainer() {
                    placeExample.forEach { place ->
                        PlaceInfoContainer(
                            place = place,
                            onClick = {
                                selectedPlace = place // 클릭된 장소 정보를 상태에 저장
                                isSheetOpen = true // 바텀 시트 열기
                            }
                        )
                        CustomDivider(MaterialTheme.colorScheme.secondary)
                    }
                }

                /// 바텀 시트 호출 및 데이터 전달
                // selectedPlace가 null이 아닐 때만 시트를 표시
                if (selectedPlace != null) {
                    PlaceDetailSheet(
                        isSheetOpen = isSheetOpen,
                        onSheetDismiss = {
                            isSheetOpen = false
                            selectedPlace = null // 닫을 때 선택된 장소 상태 초기화
                        },
                        place = selectedPlace!! // 널 검사 후 저장된 place 객체를 전달
                    )
                }
            }
        }
    }
}