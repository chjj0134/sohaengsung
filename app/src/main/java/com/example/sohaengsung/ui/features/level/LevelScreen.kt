package com.example.sohaengsung.ui.features.level

import android.R.id.message
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sohaengsung.ui.common.CustomTopBar
import com.example.sohaengsung.ui.common.ProfilePic
import com.example.sohaengsung.ui.dummy.userExample
import com.example.sohaengsung.ui.features.level.components.LevelOrbitAnimation
import com.example.sohaengsung.ui.features.level.components.ScoreContainer
import com.example.sohaengsung.ui.features.setting.SettingScreenEvent
import com.example.sohaengsung.ui.theme.SohaengsungTheme
import com.google.common.math.LinearTransformation.horizontal

@Composable
fun LevelScreen(
    onNavigate: (route: LevelScreenEvent.Navigation) -> Unit,
    viewModel: LevelViewModel = viewModel(),
) {

    val uiState by viewModel.uiState.collectAsState()
    val event by viewModel.events.collectAsState()

    val message = if (uiState.nextScoreNeeded <= 0) {
        "최고 레벨에 도달하셨습니다!"
    } else {
        "앞으로 리뷰 ${uiState.remainingReviews}번 더 작성하면 레벨 업!"
    }

    LaunchedEffect(event) {
        event?.let { navigationEvent ->
            onNavigate(navigationEvent as LevelScreenEvent.Navigation)
            viewModel.clearEvent()
        }
    }

    SohaengsungTheme {
        Scaffold(
            topBar = {
                CustomTopBar(contentText = "레벨 확인")
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .verticalScroll(rememberScrollState())
            ) {
                Column (
                    modifier = Modifier
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // 프로필
                    Column(
                        modifier = Modifier
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LevelOrbitAnimation(
                            uiState.user
                        )

                        Column (
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "${uiState.user.nickname} 님은 현재",
                                style = MaterialTheme.typography.titleMedium,
                                textAlign = TextAlign.Center,
                            )
                            Text(
                                "Lv.${uiState.user.level}입니다!",
                                style = MaterialTheme.typography.titleLarge,
                                textAlign = TextAlign.Center,
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                MaterialTheme.colorScheme.secondary,
                                shape = RoundedCornerShape(16.dp)
                            ),

                    ) {
                        Text(
                            text = message,
                            modifier = Modifier
                                .padding(horizontal = 32.dp, vertical = 8.dp)
                                .fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSecondary
                        )
                    }

                    ScoreContainer(uiState.user)
                }
            }
        }
    }
}