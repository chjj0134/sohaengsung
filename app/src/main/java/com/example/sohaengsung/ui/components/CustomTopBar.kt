import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.sohaengsung.ui.components.Dropdown
import com.example.sohaengsung.ui.theme.SohaengsungTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(contentText: String) {

    // 그라데이션 색상 정의
    val textGradientBrush = Brush.horizontalGradient(
        listOf(
            MaterialTheme.colorScheme.primary,   // 시작 색상
            MaterialTheme.colorScheme.tertiary // 끝 색상
        )
    )

    MediumTopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.background,
            titleContentColor = Color.Unspecified
        ),
        title = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = contentText,
                    style = androidx.compose.ui.text.TextStyle(
                        brush = textGradientBrush,
                        fontSize = MaterialTheme.typography.titleLarge.fontSize,
                        fontWeight = MaterialTheme.typography.titleLarge.fontWeight
                    )
                )
            }
        }
    )
}

// 테스트 코드
//@Preview(showBackground = false)
//@Composable
//fun CustomTopBarPreview() {
//    SohaengsungTheme {
//        Scaffold(
//            topBar = {
//                CustomTopBar(contentText = "내 주변 장소 추천") // 임포트해서 재사용
//            }
//        ) { innerPadding ->
//            Column(Modifier.padding(innerPadding)) {
//                Dropdown(
//                    label = "유형별",
//                    items = listOf("카페", "스터디", "도서관", "야외"),
//                    containerColor = MaterialTheme.colorScheme.primary,
//                    contentColor = MaterialTheme.colorScheme.onPrimary
//                )
//            }
//        }
//    }
//}