package com.example.sohaengsung.ui.features.logIn

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sohaengsung.R

import com.example.sohaengsung.ui.features.logIn.components.LoginButton
import com.example.sohaengsung.ui.theme.SohaengsungTheme

@Composable
fun LogInScreen(
    onNavigate: (route: LogInScreenEvent.Navigation) -> Unit,
    viewModel: LogInViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val event by viewModel.events.collectAsState()

    LaunchedEffect(event) {
        event?.let { navigationEvent ->
            onNavigate(navigationEvent)
            viewModel.clearEvent()
        }
    }

    SohaengsungTheme {
        Scaffold (
            modifier = Modifier
                .fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                // 로고 컨테이너
                Column (
                    modifier = Modifier
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {

                    Text(
                        "태그로 검색하는 내 주변 가게",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "로고",
                        modifier = Modifier
                            .width(220.dp)
                            .height(90.dp)
                    )
                }

                // 로그인 버튼
                Column (
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    LoginButton(
                        "이메일로 로그인하기",
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.onPrimary,
                        onClick = {
                            viewModel.onEvent(
                                LogInScreenEvent.onEmailLoginClick
                            )
                        }
                    )
                    LoginButton(
                        "카카오로 로그인하기",
                        MaterialTheme.colorScheme.tertiary,
                        MaterialTheme.colorScheme.onTertiary,
                        onClick = {
                            viewModel.onEvent(
                                LogInScreenEvent.onKakaoLoginClick
                            )
                        }
                    )
                }

                Text(
                    "회원가입",
                    modifier = Modifier
                        .clickable{
                            viewModel.onEvent(
                                LogInScreenEvent.onSignUpClick
                            )
                        },
                    style = MaterialTheme.typography.labelLarge.copy(
                        textDecoration = TextDecoration.Underline
                    ),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}