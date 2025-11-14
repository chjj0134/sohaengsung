package com.example.sohaengsung.ui.screens

import CustomTopBar
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sohaengsung.ui.components.Common.Bookmark
import com.example.sohaengsung.ui.components.Common.CheckBox
import com.example.sohaengsung.ui.components.Common.CustomDivider
import com.example.sohaengsung.ui.components.Common.Dropdown
import com.example.sohaengsung.ui.components.PathRecommend.PlaceForPathContainer
import com.example.sohaengsung.ui.components.PlaceRecommend.PlaceInfoContainer
import com.example.sohaengsung.ui.dummy.placeExample
import com.example.sohaengsung.ui.theme.SohaengsungTheme
import com.google.android.libraries.places.api.model.kotlin.place

@Preview(showBackground = false)
@Composable
fun PathRecommendScreen() {
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

                placeExample.forEach { place ->
                    PlaceForPathContainer(
                        place = place
                    )
                }
            }
        }
    }
}