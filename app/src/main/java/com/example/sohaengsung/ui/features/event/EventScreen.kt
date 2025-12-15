package com.example.sohaengsung.ui.features.event

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.sohaengsung.data.model.Event
import com.example.sohaengsung.ui.common.CustomTopBar
import com.example.sohaengsung.ui.common.SearchBar
import com.example.sohaengsung.ui.features.event.components.EventCard
import com.example.sohaengsung.ui.features.event.components.EventContainer
import com.example.sohaengsung.ui.features.event.components.recentUpdatedEvents
import com.example.sohaengsung.ui.features.event.components.winterEvents

@Composable
fun EventScreen() {
    var searchQuery by remember { mutableStateOf("") }
    
    // 검색어에 따라 필터링된 이벤트
    val filteredRecentEvents = remember(searchQuery) {
        if (searchQuery.isEmpty()) {
            recentUpdatedEvents
        } else {
            recentUpdatedEvents.filter { event ->
                event.tags.any { tag ->
                    tag.contains(searchQuery, ignoreCase = true)
                } || event.title.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    val filteredWinterEvents = remember(searchQuery) {
        if (searchQuery.isEmpty()) {
            winterEvents
        } else {
            winterEvents.filter { event ->
                event.tags.any { tag ->
                    tag.contains(searchQuery, ignoreCase = true)
                } || event.title.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Scaffold (
        topBar = {
            CustomTopBar(contentText = "행사 정보")
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // 검색 바
                SearchBar(
                    query = searchQuery,
                    onQueryChange = { query ->
                        searchQuery = query
                    },
                    onCalendarClick = {
                        // 캘린더 기능 (필요시 구현)
                    }
                )

//                // 최근 업데이트된 행사 컨테이너
//                EventContainer(
//                    contentText = "⏰ 최근 업데이트된 행사",
//                    events = filteredRecentEvents
//                )
//
//                // 겨울 감성에 딱 맞는 행사 컨테이너
//                EventContainer(
//                    contentText = "🧣 겨울 감성에 딱 맞는 행사",
//                    events = filteredWinterEvents
//                )

                if (searchQuery.isEmpty()) {
                    // [검색 전]
                    EventContainer(
                        contentText = "⏰ 최근 업데이트된 행사",
                        events = filteredRecentEvents
                    )

                    // 겨울 감성에 딱 맞는 행사 컨테이너
                    EventContainer(
                        contentText = "🧣 겨울 감성에 딱 맞는 행사",
                        events = filteredWinterEvents
                    )
                } else {
                    EventContainer(
                        contentText = "🔍 '${searchQuery}' 검색 결과",
                        events = filteredRecentEvents + filteredWinterEvents // 검색 로직으로 걸러진 리스트
                    )

                    // 결과가 없을 때의 추가 안내 (선택 사항)
                    if (filteredRecentEvents.isEmpty() && filteredWinterEvents.isEmpty()) {
                        Text(
                            text = "해당하는 행사가 없습니다.",
                            modifier = Modifier.padding(16.dp).fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}
