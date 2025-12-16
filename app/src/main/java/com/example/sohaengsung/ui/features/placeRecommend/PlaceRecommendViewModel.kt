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
import kotlin.math.PI
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.pow
import kotlin.math.sin
import kotlin.math.sqrt

class PlaceRecommendViewModel(
    private val placeRepository: PlaceRepository = PlaceRepository(),
    private val locationService: LocationService? = null, // GPS 사용 안함으로 삭제 가능
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
        _uiState.value = _uiState.value.copy(
            currentLat = 37.5459,
            currentLng = 126.9649
        ) // 좌표 고정 (숙명여대정문)
        loadPlaceData()
        loadHashtagData()
        observeBookmarks()
    }

    private fun calculateDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val r = 6371e3 // 지구 반지름 (m)
        val phi1 = Math.toRadians(lat1)
        val phi2 = Math.toRadians(lat2)
        val deltaPhi = Math.toRadians(lat2 - lat1)
        val deltaLambda = Math.toRadians(lon2 - lon1)

        val a = Math.sin(deltaPhi / 2).pow(2.0) +
                Math.cos(phi1) * Math.cos(phi2) *
                Math.sin(deltaLambda / 2).pow(2.0)
        val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))

        return r * c // 결과값: 미터(m) 단위
    }

    private fun loadPlaceData(lat: Double? = null, lng: Double? = null) {
        viewModelScope.launch {
            if (isPlaceDataLoaded && lat == null) return@launch

            _uiState.value = _uiState.value.copy(isLoading = true)

            try {
                val userLat = 37.5459 // 고정된 기준 위치
                val userLng = 126.9649

                val places = placeRepository.getAllPlaces()

                // 1. 가져온 데이터를 즉시 거리순으로 정렬
                val sortedPlaces = places.sortedBy { place ->
                    calculateDistance(userLat, userLng, place.latitude, place.longitude)
                }

                // 2. 원본 데이터와 UI State 모두 정렬된 상태로 저장
                _originalPlaces.value = sortedPlaces

                _uiState.value = _uiState.value.copy(
                    place = sortedPlaces,
                    currentLat = userLat,
                    currentLng = userLng,
                    isLoading = false
                )

                isPlaceDataLoaded = true

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(errorMessage = e.message, isLoading = false)
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
            try {
                _uiState.value = _uiState.value.copy(
                    isLoading = true,
                    errorMessage = null
                )
                // TODO: 실제 해시태그 데이터 로드 (Repository에서 가져오기)
                val places = placeRepository.getAllPlaces()

                val hashtags = places
                    .flatMap { place ->
                        place.hashtags ?: emptyList()
                    }
                    .distinct()
                    .map { tag ->
                        Hashtag(name = tag)
                    }

                _uiState.value = _uiState.value.copy(
                    hashtag = hashtags,
                    isLoading = false
                )

            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    hashtag = emptyList(),
                    errorMessage = e.message,
                    isLoading = false
                )
            }
        }
    }

    fun loadReviews(placeId: String) {
        viewModelScope.launch {
            try {
                val googleReviews = placeRepository.getPlaceReviews(placeId)
                _reviews.value = googleReviews
            } catch (e: Exception) {
                _reviews.value = emptyList()
            }
        }
    }

    private fun filterPlaces(
        type: String,
        tags: Set<String>
    ): List<Place> {
        val originalList = _originalPlaces.value

        return originalList.filter { place ->
            val typeMatch = if (type == "전체") true
            else place.category == mapTypeToCategory(type)

            val tagsMatch = if (tags.isEmpty()) true
            else tags.all { tag -> place.hashtags?.contains(tag) == true }

            typeMatch && tagsMatch
        }
    }

    // 카테고리 매핑
    private fun mapTypeToCategory(type: String): String = when(type) {
        "카페" -> "cafe"
        "서점" -> "bookstore"
        "편집샵" -> "select_shop"
        "갤러리" -> "gallery"
        else -> ""
    }

    fun fetchUserLocation() {
        return
    }

    fun onEvent(event: PlaceRecommendScreenEvent) {
        viewModelScope.launch {
            when (event) {
                is PlaceRecommendScreenEvent.onDropDownClick -> {
                    val criteria = event.sortCriteria
                    val currentPlaces = _uiState.value.place.toMutableList()
                    val userLat = _uiState.value.currentLat
                    val userLng = _uiState.value.currentLng

                    val sortedPlaces = when (criteria) {
                        "거리순" -> {
                            currentPlaces.sortedBy { place ->
                                calculateDistance(userLat, userLng, place.latitude, place.longitude)
                            }
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

                // 북마크 아이콘 클릭 시 로직
                is PlaceRecommendScreenEvent.onBookmarkClick -> {
                    val place = event.place

                    bookmarkRepository.toggleBookmark(uid, place.placeId)
                    placeRepository.addUserPlace(place)
                }

                is PlaceRecommendScreenEvent.onTypeFilterClick -> {
                    val newType = event.placeType
                    val currentTags = _uiState.value.selectedHashtags

                    _uiState.value = _uiState.value.copy(
                        selectedType = newType,
                        place = filterPlaces(newType, currentTags)
                    )
                }

                is PlaceRecommendScreenEvent.onResetFilters -> {
                    val originalPlaces = _originalPlaces.value
                    val originalHashtags = _uiState.value.hashtag.sortedBy { it.name }

                    _uiState.value = _uiState.value.copy(
                        selectedType = "전체",
                        selectedHashtags = emptySet(),
                        place = originalPlaces,
                        hashtag = originalHashtags
                    )
                }

                is PlaceRecommendScreenEvent.onHashtagClick -> {
                    val clickedTagName = event.hashtag.name
                    val currentType = _uiState.value.selectedType
                    val currentTags = _uiState.value.selectedHashtags

                    val newTags = if (currentTags.contains(clickedTagName)) currentTags - clickedTagName
                    else currentTags + clickedTagName

                    val sortedHashtags = _uiState.value.hashtag.sortedByDescending { newTags.contains(it.name) }

                    _uiState.value = _uiState.value.copy(
                        selectedHashtags = newTags,
                        hashtag = sortedHashtags,
                        place = filterPlaces(currentType, newTags) // 유형과 새 태그들을 모두 적용
                    )
                }

                is PlaceRecommendScreenEvent.onReviewClick -> {
                    _events.value = PlaceRecommendScreenEvent.Navigation.NavigateToReview(event.placeId)
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

    fun setSelectedPlace(placeId: String) {
        _uiState.value = _uiState.value.copy(selectedPlaceId = placeId)
        loadReviews(placeId)
    }
}
