package com.example.sohaengsung.ui.features.placeRecommend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sohaengsung.data.model.Hashtag
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.data.model.PlaceDetail
import com.example.sohaengsung.data.repository.BookmarkRepository
import com.example.sohaengsung.data.repository.PlaceRepository
import com.example.sohaengsung.data.util.LocationService
import com.example.sohaengsung.ui.features.pathRecommend.PathRecommendScreenEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlaceRecommendViewModel(
    private val placeRepository: PlaceRepository = PlaceRepository(),
    private val locationService: LocationService? = null,
    private val uid: String
) : ViewModel() {

    private val bookmarkRepository = BookmarkRepository()
    private val _bookmarkIds = MutableStateFlow<List<String>>(emptyList())
    val bookmarkIds = _bookmarkIds.asStateFlow()

    private val _uiState = MutableStateFlow(PlaceRecommendScreenUiState())
    val uiState: StateFlow<PlaceRecommendScreenUiState> = _uiState.asStateFlow()

    private val _events = MutableStateFlow<PlaceRecommendScreenEvent.Navigation?>(null)
    val events: StateFlow<PlaceRecommendScreenEvent.Navigation?> = _events.asStateFlow()

    init {
        loadPlaceData()
        loadHashtagData()
        observeBookmarks()
    }

    private fun loadPlaceData() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val location = locationService?.getCurrentLocation()
                val (lat, lng) = location ?: (37.5665 to 126.9780)

                val places = placeRepository.getNearbyPlaces(lat, lng)

                _uiState.value = _uiState.value.copy(
                    place = places,
                    isLoading = false
                )

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message,
                    isLoading = false
                )
            }
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
                    fun setSelectedPlace(placeId: String) {
                        _uiState.value = _uiState.value.copy(
                            selectedPlaceId = placeId
                        )
                    }
                }

                PlaceRecommendScreenEvent.onHashtagClick -> {
                    // 해시태그 클릭 시 로직
                }

                PlaceRecommendScreenEvent.onCouponClick -> {
                    _events.value = PlaceRecommendScreenEvent.Navigation.NavigateToCoupon
                }

                PlaceRecommendScreenEvent.onReviewClick -> {
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

    //GPS Update
    fun updateLocation(lat: Double, lng: Double) {
        _uiState.value = _uiState.value.copy(
            currentLat = lat,
            currentLng = lng
        )
    }

    private fun observeBookmarks() {
        viewModelScope.launch {
            bookmarkRepository.observeBookmarks(uid).collect { ids ->
                _bookmarkIds.value = ids
            }
        }
    }

    fun setSelectedPlace(placeId: String) {
        _uiState.value = _uiState.value.copy(
            selectedPlaceId = placeId
        )
    }

    fun toggleBookmark(place: Place) {
        viewModelScope.launch {
            bookmarkRepository.toggleBookmark(uid, place.placeId)
            placeRepository.addUserPlace(place)
        }
    }
}