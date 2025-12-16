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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sohaengsung.ui.common.CustomDivider
import com.example.sohaengsung.ui.common.CustomTopBar
import com.example.sohaengsung.ui.common.Dropdown
import com.example.sohaengsung.ui.features.bookmarked.components.BookmarkedItem
import com.example.sohaengsung.ui.theme.SohaengsungTheme

import android.Manifest
import android.content.pm.PackageManager
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.LocationServices

@Composable
fun BookmarkedScreen(
    onNavigate: (route: BookmarkScreenEvent.Navigation) -> Unit,
    viewModel: BookmarkedViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val event by viewModel.events.collectAsState()

    val context = LocalContext.current
    val fusedLocationClient = remember { LocationServices.getFusedLocationProviderClient(context) }

    LaunchedEffect(event) {
        event?.let { navigationEvent ->
            if (navigationEvent is BookmarkScreenEvent.Navigation) {
                onNavigate(navigationEvent)
                viewModel.clearEvent()
            }
        }
    }

    LaunchedEffect(Unit) {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                location?.let {
                    viewModel.updateCurrentLocation(it.latitude, it.longitude)
                }
            }
        }
    }

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
                        modifier = Modifier.padding(32.dp),
                        style = MaterialTheme.typography.bodyLarge,
                    )
                    CustomDivider(MaterialTheme.colorScheme.secondary)
                }

                // 드롭다운 (정렬 로직 연결됨)
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
                        onItemSelected = { selectedCriteria ->
                            viewModel.onEvent(BookmarkScreenEvent.onDropDownClick(selectedCriteria))
                        }
                    )
                }

                // 북마크 리스트 렌더링
                uiState.bookmarkedPlaces.forEach { placeWithDistance ->
                    BookmarkedItem(
                        place = placeWithDistance.place,
                        // distance = placeWithDistance.distance // 필요시 거리 표시 전달 가능
                        onDeleteClick = {
                            viewModel.onEvent(BookmarkScreenEvent.onDeleteClick(placeWithDistance))
                        }
                    )
                }
            }
        }
    }
}