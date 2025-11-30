package com.example.sohaengsung.ui.features.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sohaengsung.ui.common.AppIcons
import com.example.sohaengsung.ui.common.LogoTopBar
import com.example.sohaengsung.ui.common.ProfilePic
import com.example.sohaengsung.ui.features.home.components.HomeMenuCard
import com.example.sohaengsung.ui.theme.SohaengsungTheme

@Composable
fun HomeScreen(
    onNavigate: (route: HomeScreenEvent.Navigation) -> Unit,
    viewModel: HomeScreenViewModel = viewModel(),
) {

    val uiState by viewModel.uiState.collectAsState()
    val event by viewModel.events.collectAsState()

    LaunchedEffect(event) {
        event?.let { navigationEvent ->
            onNavigate(navigationEvent as HomeScreenEvent.Navigation)
            viewModel.clearEvent()
        }
    }

    SohaengsungTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                LogoTopBar(
                    onProfileClick = {
                        viewModel.onEvent(
                            HomeScreenEvent.onSettingClick
                        )
                    },
                    profileContent = {
                        ProfilePic(
                            user = uiState.user,
                            size = 40
                        )
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(top = 30.dp)  // topBar와의 간격 증가
                    .padding(horizontal = 20.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)  // Row 간 간격 증가
            ) {
                // 첫 번째 행: 내 주변 장소 추천, 경로 추천
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)  // 카드 간 간격 증가
                ) {
                    HomeMenuCard(
                        title = "내 주변\n장소 추천",
                        icon = AppIcons.LocationOn,
                        onClick = {
                            viewModel.onEvent(HomeScreenEvent.onPlaceRecommendClick)
                        },
                        modifier = Modifier.weight(1f)
                    )
                    
                    HomeMenuCard(
                        title = "경로 추천",
                        icon = AppIcons.Map,
                        onClick = {
                            viewModel.onEvent(HomeScreenEvent.onPathRecommendClick)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                // 두 번째 행: 내 북마크 확인하기, 쿠폰 확인하기
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)  // 카드 간 간격 증가
                ) {
                    HomeMenuCard(
                        title = "내 북마크\n확인하기",
                        icon = AppIcons.Folder,
                        onClick = {
                            viewModel.onEvent(HomeScreenEvent.onBookmarkClick)
                        },
                        modifier = Modifier.weight(1f)
                    )
                    
                    HomeMenuCard(
                        title = "쿠폰\n확인하기",
                        icon = AppIcons.CardGiftcard,
                        onClick = {
                            viewModel.onEvent(HomeScreenEvent.onCouponClick)
                        },
                        modifier = Modifier.weight(1f)
                    )
                }

                // 세 번째 행: 행사 정보 확인하기
                Row(
                    horizontalArrangement = Arrangement.spacedBy(24.dp)  // 카드 간 간격 증가
                ) {
                    HomeMenuCard(
                        title = "행사 정보\n확인하기",
                        icon = AppIcons.LocalCafe,
                        onClick = {
                            viewModel.onEvent(HomeScreenEvent.onEventClick)
                        }
                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun HomeScreenPreview() {
//    SohaengsungTheme {
//        HomeScreen()
//    }
//}

