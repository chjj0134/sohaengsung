package com.example.sohaengsung.ui.features.coupon.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.sohaengsung.ui.common.BulletPointText
import com.example.sohaengsung.ui.common.IndentedText

@Composable
fun CouponGuide() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Text(
            text = "쿠폰 적립 안내",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 12.dp)
        )

        // 2. 불렛 포인트 리스트
        val guideItems = listOf(
            "결제 1회 당 스탬프 1개 획득을 원칙으로 합니다.",
            "결제 인증은 영수증을 통해 이루어집니다.",
            "첫 리뷰 등록 시 스탬프 3개를 획득할 수 있습니다."
        )

        guideItems.forEachIndexed { index, item ->
            BulletPointText(text = item)

            // 두 번째 항목(index == 1) 아래에만 들여쓰기된 추가 설명 추가
            if (index == 1) {
                IndentedText(text = "스탬프 획득 전 영수증을 버리지 않도록 주의해 주세요.")
            }
        }
    }
}