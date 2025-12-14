package com.example.sohaengsung.ui.features.placeRecommend

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sohaengsung.data.model.Hashtag
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.data.model.PlaceDetail
import com.example.sohaengsung.data.model.GoogleReview
import com.example.sohaengsung.data.repository.BookmarkRepository
import com.example.sohaengsung.data.repository.PlaceRepository
import com.example.sohaengsung.data.repository.ReviewRepository
import com.example.sohaengsung.data.util.LocationService
import com.example.sohaengsung.ui.features.pathRecommend.PathRecommendScreenEvent
import com.google.android.libraries.places.api.model.kotlin.place
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
    private val reviewRepository = ReviewRepository()
    private val _bookmarkIds = MutableStateFlow<List<String>>(emptyList())
    val bookmarkIds = _bookmarkIds.asStateFlow()

    private val _uiState = MutableStateFlow(PlaceRecommendScreenUiState())
    val uiState: StateFlow<PlaceRecommendScreenUiState> = _uiState.asStateFlow()

    private val _events = MutableStateFlow<PlaceRecommendScreenEvent.Navigation?>(null)
    val events: StateFlow<PlaceRecommendScreenEvent.Navigation?> = _events.asStateFlow()

    private val _reviews = MutableStateFlow<List<GoogleReview>>(emptyList())
    val reviews: StateFlow<List<GoogleReview>> = _reviews.asStateFlow()

    private val _originalPlaces = MutableStateFlow<List<Place>>(emptyList())

    private var isPlaceDataLoaded = false

    init {
        loadPlaceData()
        loadHashtagData()
        observeBookmarks()
    }

    private fun loadPlaceData(lat: Double? = null, lng: Double? = null) {
        viewModelScope.launch {
            if (isPlaceDataLoaded && lat == null) {
                return@launch
            }

            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                // loadPlaceData()가 불필요하게 호출되어 초기값으로 덮어쓰지 않도록 로직을 수정
                val currentLat = lat ?: _uiState.value.currentLat
                val currentLng = lng ?: _uiState.value.currentLng

                val finalLat = currentLat.takeIf { it != 0.0 } ?: 37.5665
                val finalLng = currentLng.takeIf { it != 0.0 } ?: 126.9780

                val places = placeRepository.getAllPlaces()

                _originalPlaces.value = places

                _uiState.value = _uiState.value.copy(
                    place = places,
                    currentLat = finalLat,
                    currentLng = finalLng,
                    isLoading = false
                )

                isPlaceDataLoaded = true

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    errorMessage = e.message,
                    isLoading = false
                )
            }
        }
    }

    private fun observeBookmarks() {
        viewModelScope.launch {
            bookmarkRepository.observeBookmarks(uid).collect { ids ->
                _bookmarkIds.value = ids
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
        }
    }

    fun loadReviews(placeId: String) {
        viewModelScope.launch {
            try {
                val googleReviews = emptyList<GoogleReview>()

                val userReviews = reviewRepository.getReviewsByPlace(placeId)

                placeRepository.updateRating(placeId, userReviews)

                val convertedUserReviews = userReviews.map {
                    GoogleReview(
                        author = it.userId,
                        rating = it.rating.toInt(),
                        time = it.createdAt?.toDate()?.toString() ?: "",
                        content = it.content,
                        profilePhotoUrl = null
                    )
                }

                _reviews.value = convertedUserReviews
            } catch (e: Exception) {
                _reviews.value = emptyList()   // 실패 시 빈 리스트
            }
        }
    }

    fun fetchUserLocation() {
        // 이미 위치 정보가 업데이트된 경우 다시 시도하지 않음
        val isInitialState = _uiState.value.currentLat == 37.5665 && _uiState.value.currentLng == 126.9780
        if (!isInitialState) return

        viewModelScope.launch {
            // 주입된 LocationService를 사용
            val location = locationService?.getCurrentLocation()

            if (location != null) {
                val (lat, lng) = location
                updateLocation(lat, lng)
            }
        }
    }

    fun onEvent(event: PlaceRecommendScreenEvent) {
        viewModelScope.launch {
            when (event) {
                is PlaceRecommendScreenEvent.onDropDownClick -> {
                    val criteria = event.sortCriteria
                    val currentPlaces = _uiState.value.place.toMutableList()

                    val sortedPlaces = when (criteria) {
                        "거리순" -> {
                            // 별도 거리 계산 로직 (예: Haversine)이 필요함
                            // 우선 placeId로 정렬
                            currentPlaces.sortedBy { it.placeId }
                        }
                        "별점높은순" -> {
                            currentPlaces.sortedByDescending { it.rating }
                        }
                        "리뷰많은순" -> {
                            currentPlaces.sortedByDescending { it.reviewCount }
                        }
                        else -> currentPlaces // 그 외의 경우 현재 순서 유지
                    }

                    // 정렬된 리스트로 UI 상태 업데이트
                    _uiState.value = _uiState.value.copy(
                        place = sortedPlaces
                    )
                }

                is PlaceRecommendScreenEvent.onTypeFilterClick -> {
                    val selectedType = event.placeType
                    val originalList = _originalPlaces.value

                    val filteredList = when (selectedType) {
                        "전체" -> {
                            // '전체'를 선택하면 원본 목록 그대로 사용
                            originalList
                        }
                        "카페" -> {
                            originalList.filter { place ->
                                place.category == "cafe"
                            }
                        }
                        "서점" -> {
                            originalList.filter { place ->
                                place.category == "bookstore"
                            }
                        }
                        "편집샵" -> {
                            originalList.filter { place ->
                                place.category == "select_shop"
                            }
                        }
                        "갤러리" -> {
                            originalList.filter { place ->
                                place.category == "gallery"
                            }
                        }
                        else -> originalList
                    }

                    // 필터링된 리스트로 UI 상태 업데이트
                    _uiState.value = _uiState.value.copy(
                        place = filteredList
                    )
                }

                // 북마크 아이콘 클릭 시 로직
                is PlaceRecommendScreenEvent.onBookmarkClick -> {
                    val place = event.place

                    bookmarkRepository.toggleBookmark(uid, place.placeId)
                    placeRepository.addUserPlace(place)
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
        loadPlaceData(lat, lng)
    }

    fun setSelectedPlace(placeId: String) {
        _uiState.value = _uiState.value.copy(selectedPlaceId = placeId)
        loadReviews(placeId)
    }
}
