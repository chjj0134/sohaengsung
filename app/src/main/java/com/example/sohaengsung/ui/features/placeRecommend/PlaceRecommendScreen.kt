package com.example.sohaengsung.ui.features.placeRecommend

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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.ui.features.placeRecommend.components.CustomContainer
import com.example.sohaengsung.ui.common.CustomDivider
import com.example.sohaengsung.ui.common.CustomTopBar
import com.example.sohaengsung.ui.common.Dropdown
import com.example.sohaengsung.ui.features.placeRecommend.components.HashtagListContainer
import com.example.sohaengsung.ui.features.placeRecommend.components.PlaceDetailSheet
import com.example.sohaengsung.ui.features.placeRecommend.components.PlaceInfoContainer
import com.example.sohaengsung.ui.dummy.placeExample
import com.example.sohaengsung.ui.features.map.MapScreen
import com.example.sohaengsung.ui.theme.SohaengsungTheme
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.location.LocationServices
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.firebase.auth.FirebaseAuth


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PlaceRecommendScreen(
    onNavigate: (route: PlaceRecommendScreenEvent.Navigation) -> Unit,
    viewModel: PlaceRecommendViewModel = viewModel(factory = PlaceRecommendViewModelFactory(
        FirebaseAuth.getInstance().currentUser?.uid ?: "")),
) {

    var isSheetOpen by remember { mutableStateOf(false) }
    var selectedPlace by remember { mutableStateOf<Place?>(null) }

    val uiState by viewModel.uiState.collectAsState()
    val event by viewModel.events.collectAsState()

    val locationPermission = rememberPermissionState(
        permission = Manifest.permission.ACCESS_FINE_LOCATION
    )

    val context = LocalContext.current
    val fusedClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(true) {
        locationPermission.launchPermissionRequest()
    }

    LaunchedEffect(locationPermission.status) {
        if (!locationPermission.status.isGranted) return@LaunchedEffect

        val hasPermission = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (hasPermission) {
            try {
                fusedClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        viewModel.updateLocation(
                            lat = location.latitude,
                            lng = location.longitude
                        )
                    }
                }
            } catch (e: SecurityException) {
                e.printStackTrace()
            }
        }
    }


    LaunchedEffect(event) {
        event?.let { navigationEvent ->
            onNavigate(navigationEvent)
            viewModel.clearEvent()
        }
    }

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
                    MapScreen(
                        latitude = uiState.currentLat,
                        longitude = uiState.currentLng
                    )

                    Column (
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(horizontal = 8.dp),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {

                        // 해시태그 영역
                        HashtagListContainer(
                            uiState.hashtag,
                            uiState.hashtag,
                            viewModel
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
                    uiState.place.forEach { place ->
                        PlaceInfoContainer(
                            place = place,
                            onClick = {
                                selectedPlace = place // 클릭된 장소 정보를 상태에 저장
                                isSheetOpen = true // 바텀 시트 열기
                            },
                            viewModel = viewModel
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
                        place = selectedPlace!!, // 널 검사 후 저장된 place 객체를 전달
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}