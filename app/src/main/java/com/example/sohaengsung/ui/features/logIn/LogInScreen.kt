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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.sohaengsung.R
import com.example.sohaengsung.ui.features.logIn.components.LoginButton
import com.example.sohaengsung.ui.theme.SohaengsungTheme

@Composable
fun LogInScreen(
    onClickGoogleLogin: () -> Unit,
    onClickKakaoLogin: () -> Unit
) {
    SohaengsungTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                // 로고 영역
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
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

                // 로그인 버튼 영역
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    LoginButton(
                        "구글로 로그인하기",
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        onClick = { onClickGoogleLogin() }
                    )

                    LoginButton(
                        "카카오로 로그인하기",
                        containerColor = MaterialTheme.colorScheme.tertiary,
                        contentColor = MaterialTheme.colorScheme.onTertiary,
                        onClick = { onClickKakaoLogin() }
                    )
                }

//                Text(
//                    "회원가입",
//                    modifier = Modifier.clickable { /* 나중에 구현 */ },
//                    style = MaterialTheme.typography.labelLarge.copy(
//                        textDecoration = TextDecoration.Underline
//                    ),
//                    color = MaterialTheme.colorScheme.primary
//                )
            }
        }
    }
}
