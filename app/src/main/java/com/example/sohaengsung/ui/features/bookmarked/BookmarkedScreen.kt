package com.example.sohaengsung.ui.features.bookmarked

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sohaengsung.ui.common.CustomDivider
import com.example.sohaengsung.ui.common.CustomTopBar
import com.example.sohaengsung.ui.common.Dropdown
import com.example.sohaengsung.ui.theme.SohaengsungTheme

@Composable
fun BookmarkedScreen(
    uid: String = "dummy-user-id"
) {
    // TODO: 리포지토리 연결 후 주석만 풀어 주세요!
    // val viewModel: BookmarkedViewModel = viewModel(
    //    factory = BookmarkedRecommendViewModelFactory(uid))

    // val bookmarkPlaces by viewModel.bookmarkPlaces.collectAsState()

    SohaengsungTheme {
        Scaffold(
            topBar = {
                CustomTopBar(contentText = "북마크 확인하기")
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
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp, vertical = 20.dp),
                    horizontalArrangement = Arrangement.Absolute.Right
                ) {
                    Dropdown(
                        label = "거리순",
                        items = listOf("별점높은순", "리뷰많은순"),
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                }

                // TODO: 리포지토리 연결 후 주석만 풀어 주세요!
                // bookmarkPlaces.forEach { place ->
                //    BookmarkedItem(place = place)
                }
            }
        }
    }
