package com.example.sohaengsung.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.sohaengsung.R
import com.example.sohaengsung.ui.theme.SohaengsungTheme

@Composable
fun LogoTopBar(
    onProfileClick: () -> Unit = {},
    profileContent: @Composable (() -> Unit)? = null
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .padding(horizontal = 20.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            // 왼쪽: 로고 이미지
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "로고",
                modifier = Modifier
                    .width(100.dp)
                    .height(80.dp)
                    .padding(top = 20.dp)  // 로고를 아래로 이동
            )
            
            // 오른쪽: 프로필 이미지 (옵션)
            if (profileContent != null) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .clickable { onProfileClick() },
                    contentAlignment = Alignment.Center
                ) {
                    profileContent()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LogoTopBarPreview() {
    SohaengsungTheme {
        LogoTopBar(
            onProfileClick = {},
            profileContent = {
                // 프로필 이미지 프리뷰
                Box(
                    modifier = Modifier
                        .height(40.dp)
                        .padding(8.dp)
                ) {
                    Text("프로필")
                }
            }
        )
    }
}

