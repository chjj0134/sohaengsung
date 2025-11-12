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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sohaengsung.data.model.Hashtag
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.data.model.PlaceDetail
import com.example.sohaengsung.ui.components.CustomContainer
import com.example.sohaengsung.ui.components.Dropdown
import com.example.sohaengsung.ui.components.HashtagListContainer
import com.example.sohaengsung.ui.components.PlaceInfoContainer
import com.example.sohaengsung.ui.theme.SohaengsungTheme


@Preview(showBackground = false)
@Composable
fun PlaceRecommendScreen() {

    // 예시용 임시 데이터
    val HashtagListExample = listOf(
        Hashtag(
            tagId = "h001",
            name = "공부하기좋은",
            useCount = 125
        ),
        Hashtag(
            tagId = "h002",
            name = "따뜻한분위기",
            useCount = 98
        ),
        Hashtag(
            tagId = "h003",
            name = "노트북콘센트",
            useCount = 75
        ),
        Hashtag(
            tagId = "h004",
            name = "주차가능",
            useCount = 52
        )
    )

    val PlaceExample = Place(
        "00",
        "올웨이즈어거스트제작소",
        "망원동 00길 00번지",
        hashtags = listOf("공부하기좋은", "따뜻한"),
        details = PlaceDetail(
            true,
            true,
            true,
            "크림 라떼"
        )
    )

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
                            HashtagListExample,
                            HashtagListExample
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
                                label = "유형별",
                                items = listOf("카페", "스터디", "도서관", "야외"),
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }

                // 커스텀 컨테이너 안에 map 사용해서 장소 정보 PlaceInfoContainer 형태로 하나씩 배치 예정
                CustomContainer() {
                    PlaceInfoContainer(PlaceExample)
                }
            }
        }
    }
}