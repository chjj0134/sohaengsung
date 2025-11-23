package com.example.sohaengsung.ui.features.event

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sohaengsung.data.model.Event
import com.example.sohaengsung.ui.common.CustomTopBar
import com.example.sohaengsung.ui.common.SearchBar
import com.example.sohaengsung.ui.features.event.components.EventCard
import com.example.sohaengsung.ui.features.event.components.EventContainer
import com.google.firebase.Timestamp
import java.util.Date

@Composable
fun EventScreen() {

    val eventExample = Event(
        title = "2025 카페&베이커리페어 시즌2",
        tags = listOf("커피", "디저트", "박람회"),
        seasonInfo = "11/5(수) ~ 11/8(토)",
    )

    Scaffold (
        topBar = {
            CustomTopBar(contentText = "행사 정보")
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {

            Column(
                modifier = Modifier
                    .padding(32.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                SearchBar(
                    "이벤트를 검색해 보세요!",
                    onQueryChange = {

                    },
                    onCalendarClick = {

                    }
                )

                // 최근 업데이트 행사 컨테이너
                EventContainer(
                    "최근 업데이트된 행사",
                    eventExample
                )

                // 추천 행사 컨테이너
                EventContainer(
                    "가을 감성에 딱 맞는 행사",
                    eventExample
                )
            }
        }
    }
}
