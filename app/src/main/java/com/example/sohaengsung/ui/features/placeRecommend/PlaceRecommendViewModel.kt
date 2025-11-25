package com.example.sohaengsung.ui.features.placeRecommend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sohaengsung.data.model.Hashtag
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.data.model.PlaceDetail
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlaceRecommendViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(PlaceRecommendScreenUiState())
    val uiState: StateFlow<PlaceRecommendScreenUiState> = _uiState.asStateFlow()

    private val _events = MutableStateFlow<PlaceRecommendScreenEvent.Navigation?>(null)
    val events: StateFlow<PlaceRecommendScreenEvent.Navigation?> = _events.asStateFlow()

    init {
        loadPlaceData()
        loadHashtagData()
    }

    private fun loadPlaceData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            // TODO: 실제 장소 데이터 로드 (Repository에서 가져오기)
            // 현재는 더미 데이터 사용
            _uiState.value = _uiState.value.copy(
                place = listOf(
                    Place(placeId = "C001", name = "올웨이즈어거스트제작소", address = "서울 마포구 연남로 71 1층", latitude = 37.5051, longitude = 127.0507, hashtags = listOf("카공", "노트북", "콘센트"), rating = 4.5, reviewCount = 53, details = PlaceDetail(wifi = true, parking = true, kidsZone = false, signatureMenu = "흑임자 크림 라떼")),
                    Place(placeId = "R005", name = "너드커피", address = "서울 용산구 청파로27길 제1호 내제1층호", latitude = 37.5145, longitude = 127.1050, hashtags = listOf("시즌음료", "테이크아웃전문"), rating = 4.8, reviewCount = 132, details = PlaceDetail(wifi = false, parking = false, kidsZone = false, signatureMenu = "말차밀크티")),
                    Place(placeId = "M012", name = "책방죄책감", address = "서울 용산구 청파로47길 8 2층", latitude = 37.5760, longitude = 126.9800, hashtags = listOf("다양한책", "아늑한분위기", "북카페"), rating = 4.2, reviewCount = 28, details = PlaceDetail(wifi = true, parking = false, kidsZone = false, signatureMenu = null))
                ),
                isLoading = false
            )
        }
    }

    private fun loadHashtagData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                errorMessage = null
            )

            // TODO: 실제 해시태그 데이터 로드 (Repository에서 가져오기)
            // 현재는 더미 데이터 사용
            _uiState.value = _uiState.value.copy(
                hashtag = listOf(
                    Hashtag(
                        tagId = "h001",
                        name = "카공",
                        useCount = 125
                    ),
                    Hashtag(
                        tagId = "h002",
                        name = "따뜻한",
                        useCount = 98
                    ),
                    Hashtag(
                        tagId = "h003",
                        name = "노트북",
                        useCount = 75
                    ),
                    Hashtag(
                        tagId = "h004",
                        name = "콘센트",
                        useCount = 52
                    ),
                    Hashtag(
                        tagId = "h005",
                        name = "레트로",
                        useCount = 125
                    ),
                    Hashtag(
                        tagId = "h007",
                        name = "주차장",
                        useCount = 75
                    ),
                ),
                isLoading = false
            )
        }
    }

    fun onEvent(event: PlaceRecommendScreenEvent) {
        viewModelScope.launch {
            when (event) {
                PlaceRecommendScreenEvent.onDropDownClick -> {
                   // 드롭다운 클릭 시 로직
                }

                PlaceRecommendScreenEvent.onBookmarkClick -> {
                    // 북마크 아이콘 클릭 시 로직
                }

                PlaceRecommendScreenEvent.onHashtagClick -> {
                   // 해시태그 클릭 시 로직
                }

                PlaceRecommendScreenEvent.onNavigateToReview -> {
                    _events.value = PlaceRecommendScreenEvent.Navigation.NavigateToReview
                }

                is PlaceRecommendScreenEvent.Navigation -> {
                    /* do nothing */
                }
            }
        }
    }

    fun clearEvent() {
        _events.value = null
    }
}