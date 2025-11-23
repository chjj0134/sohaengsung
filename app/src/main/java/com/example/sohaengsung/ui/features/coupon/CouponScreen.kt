package com.example.sohaengsung.ui.features.coupon

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.sohaengsung.ui.common.ProfilePic
import com.example.sohaengsung.ui.features.coupon.components.CouponContainer
import com.example.sohaengsung.ui.features.coupon.components.CouponGuide

@Composable
fun CouponScreen(
    onNavigate: (route: CouponScreenEvent.Navigation) -> Unit,
    viewModel: CouponViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()
    val event by viewModel.events.collectAsState()

    LaunchedEffect(event) {
        event?.let { navigationEvent ->
            onNavigate(navigationEvent)
            viewModel.clearEvent()
        }
    }

    Column (
        modifier = Modifier
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // 프로필 컨테이너
        Column(
            modifier = Modifier
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ProfilePic(
                user = uiState.user,
                200
            )

            Text(
                "${uiState.user.nickname} 님,\n벌써 ${uiState.coupon.stampCount}번째 스탬프예요!",
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
            )
        }

        // 쿠폰 컨테이너
        CouponContainer(10, uiState.coupon.stampCount)

        // 쿠폰 사용처 텍스트
        Text(
            text = "쿠폰 사용처 알아보기",
            style = MaterialTheme.typography.bodyMedium.copy(
                textDecoration = TextDecoration.Underline
            ),
            modifier = Modifier
                .padding(8.dp)
                .clickable {
                    viewModel.onEvent(
                        CouponScreenEvent.onNavigateToVoucherScreen
                    )
                }
        )

            // 쿠폰 적립 안내
        CouponGuide()
    }
}