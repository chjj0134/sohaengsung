package com.example.sohaengsung.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.sohaengsung.R
import com.example.sohaengsung.ui.components.Common.CustomDivider
import com.example.sohaengsung.ui.components.Common.CustomVerticalDivider
import com.example.sohaengsung.ui.components.Common.ProfilePic
import com.example.sohaengsung.ui.components.Setting.ProfileSettingContainer
import com.example.sohaengsung.ui.dummy.userExample
import com.example.sohaengsung.ui.theme.SohaengsungTheme
import com.google.common.math.LinearTransformation.horizontal

@Composable
fun SettingScreen() {
    SohaengsungTheme {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
            ) {
                Column (
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 32.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                )
                {
                    // 로고
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "로고",
                        modifier = Modifier
                            .width(120.dp)
                            .height(80.dp)
                    )

                    // 프로필 컨테이너
                    ProfileSettingContainer(userExample)

                    // 디바이더
                    CustomDivider(
                        MaterialTheme.colorScheme.primary
                    )

                    // 임시 설정 목록
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 6.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = "계정 설정",
                            modifier = Modifier
                                .padding(horizontal = 20.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )

                        CustomDivider(
                            MaterialTheme.colorScheme.secondary
                        )

                        Text(
                            text = "테마 변경",
                            modifier = Modifier
                                .padding(horizontal = 20.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )

                        CustomDivider(
                            MaterialTheme.colorScheme.secondary
                        )
                        
                        Text(
                            text = "약관 확인",
                            modifier = Modifier
                                .padding(horizontal = 20.dp, vertical = 8.dp),
                            style = MaterialTheme.typography.bodyMedium
                        )

                        CustomDivider(
                            MaterialTheme.colorScheme.secondary
                        )
                    }
                }
            }
        }
    }
}