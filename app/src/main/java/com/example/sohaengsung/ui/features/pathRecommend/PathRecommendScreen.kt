package com.example.sohaengsung.ui.features.pathRecommend

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Redeem
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
import com.example.sohaengsung.ui.features.pathRecommend.components.PlaceForPathContainer
import com.example.sohaengsung.ui.common.CustomTopBar
import com.example.sohaengsung.ui.theme.SohaengsungTheme

@Composable
fun PathRecommendScreen(
    uid: String = "dummy-user-id",
    onNavigate: (route: PathRecommendScreenEvent.Navigation) -> Unit,
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
                        items = listOf("별점높은순", "리뷰많은순"),
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                    )
                }

//                bookmarkPlaces.forEach { place ->
//                    PlaceForPathContainer(place = place)
//                }
            }
        }
    }
}