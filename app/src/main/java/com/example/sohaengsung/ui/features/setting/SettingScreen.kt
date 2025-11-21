package com.example.sohaengsung.ui.features.setting

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sohaengsung.R
import com.example.sohaengsung.ui.components.Common.CustomDivider
import com.example.sohaengsung.ui.features.setting.components.ProfileSettingContainer
import com.example.sohaengsung.ui.features.setting.components.SettingMenuItem
import com.example.sohaengsung.ui.theme.SohaengsungTheme

@Composable
fun SettingScreen(
    viewModel: SettingScreenViewModel = viewModel(),
    onNavigateToAccountManagement: () -> Unit = {},
    onNavigateToThemeChange: () -> Unit = {},
    onNavigateToTerms: () -> Unit = {},
    onNavigateToNotice: () -> Unit = {},
    onNavigateToLevelDetail: () -> Unit = {},
    onEditProfilePicture: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val events by viewModel.events.collectAsState()

    // 이벤트 처리
    LaunchedEffect(events) {
        when (val event = events) {
            is SettingScreenEvent.NavigateToAccountManagement -> {
                onNavigateToAccountManagement()
                viewModel.clearEvent()
            }
            is SettingScreenEvent.NavigateToThemeChange -> {
                onNavigateToThemeChange()
                viewModel.clearEvent()
            }
            is SettingScreenEvent.NavigateToTerms -> {
                onNavigateToTerms()
                viewModel.clearEvent()
            }
            is SettingScreenEvent.NavigateToNotice -> {
                onNavigateToNotice()
                viewModel.clearEvent()
            }
            is SettingScreenEvent.NavigateToLevelDetail -> {
                onNavigateToLevelDetail()
                viewModel.clearEvent()
            }
            is SettingScreenEvent.EditProfilePicture -> {
                onEditProfilePicture()
                viewModel.clearEvent()
            }
            null -> {}
        }
    }

    SohaengsungTheme {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "로고",
                    modifier = Modifier
                        .width(100.dp)
                        .height(80.dp)
                )

                // 프로필 섹션
                ProfileSettingContainer(
                    user = uiState.user,
                    onLevelDetailClick = {
                        viewModel.onEvent(SettingScreenEvent.NavigateToLevelDetail)
                    },
                    onProfileEditClick = {
                        viewModel.onEvent(SettingScreenEvent.EditProfilePicture)
                    }
                )

                // 구분선
                CustomDivider(
                    color = Color(0xFFE0E0E0), // 연한 회색
                    modifier = Modifier.padding(vertical = 8.dp)
                )

                // 메뉴 리스트
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    SettingMenuItem(
                        text = "계정 관리",
                        onClick = {
                            viewModel.onEvent(SettingScreenEvent.NavigateToAccountManagement)
                        }
                    )

                    CustomDivider(
                        color = Color(0xFFE0E0E0),
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )

                    SettingMenuItem(
                        text = "테마 변경",
                        onClick = {
                            viewModel.onEvent(SettingScreenEvent.NavigateToThemeChange)
                        }
                    )

                    CustomDivider(
                        color = Color(0xFFE0E0E0),
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )

                    SettingMenuItem(
                        text = "약관 확인",
                        onClick = {
                            viewModel.onEvent(SettingScreenEvent.NavigateToTerms)
                        }
                    )

                    CustomDivider(
                        color = Color(0xFFE0E0E0),
                        modifier = Modifier.padding(horizontal = 20.dp)
                    )

                    SettingMenuItem(
                        text = "공지사항",
                        onClick = {
                            viewModel.onEvent(SettingScreenEvent.NavigateToNotice)
                        }
                    )
                }
            }
        }
    }


@Preview(showBackground = true)
@Composable
fun SettingScreenPreview() {
    SohaengsungTheme {
        SettingScreen()
    }
}