package com.example.sohaengsung.ui.features.pathRecommend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.data.repository.BookmarkRepository
import com.example.sohaengsung.data.repository.PlaceRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

import kotlin.math.*

// UI 전용 모델 정의
data class PlaceWithDistance(
    val place: Place,
    val distance: Double = 0.0
)

class PathRecommendViewModel(
    private val bookmarkRepository: BookmarkRepository,
    private val placeRepository: PlaceRepository,
    private val uid: String
) : ViewModel() {

    private val _bookmarkIds = MutableStateFlow<List<String>>(emptyList())
    val bookmarkIds = _bookmarkIds.asStateFlow()

    // 내부적으로만 관리하는 원본 장소 리스트
    private val _originalPlaces = MutableStateFlow<List<Place>>(emptyList())

    private val _uiState = MutableStateFlow(PathRecommendScreenUiState())
    val uiState: StateFlow<PathRecommendScreenUiState> = _uiState.asStateFlow()

    private val _events = MutableStateFlow<PathRecommendScreenEvent.Navigation?>(null)
    val events: StateFlow<PathRecommendScreenEvent.Navigation?> = _events.asStateFlow()

    private val _selectedPlaceIds = MutableStateFlow<Set<String>>(emptySet())
    val selectedPlaceIds: StateFlow<Set<String>> = _selectedPlaceIds.asStateFlow()

    init {
        observeBookmarkIds()
    }

    private fun observeBookmarkIds() {
        viewModelScope.launch {
            bookmarkRepository.observeBookmarks(uid).collectLatest { ids ->
                _bookmarkIds.value = ids
                loadPlaces(ids)
            }
        }
    }

    private suspend fun loadPlaces(ids: List<String>) {
        val places = placeRepository.getPlaces(ids)
        _originalPlaces.value = places

        // Place -> PlaceWithDistance 변환
        val placesWithDistance = places.map { PlaceWithDistance(it) }

        _uiState.update { state ->
            state.copy(
                place = placesWithDistance, // 이제 UiState의 place는 List<PlaceWithDistance>여야 합니다.
                isLoading = false
            )
        }

        // 위치 정보가 이미 있다면 계산 수행
        _uiState.value.currentLocation?.let { calculateDistance(it) }
    }

    /**
     * 현재 위치 정보를 받아 거리 계산 후 UI State 업데이트
     */
    fun calculateDistance(currentLocation: Pair<Double, Double>) {
        val (currentLat, currentLng) = currentLocation

        val updatedList = _uiState.value.place.map { item ->
            val dist = computeHaversineDistance(
                currentLat, currentLng,
                item.place.latitude, item.place.longitude
            )
            item.copy(distance = dist)
        }

        _uiState.update { it.copy(place = updatedList, currentLocation = currentLocation) }
    }

    private fun computeHaversineDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val r = 6371e3 // 미터 단위
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2).pow(2) + cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) * sin(dLon / 2).pow(2)
        return r * 2 * atan2(sqrt(a), sqrt(1 - a))
    }

    fun onEvent(event: PathRecommendScreenEvent) {
        viewModelScope.launch {
            when (event) {
                is PathRecommendScreenEvent.onDropDownClick -> {
                    val criteria = event.sortCriteria
                    val currentList = _uiState.value.place

                    val sortedList = when (criteria) {
                        "거리순" -> currentList.sortedBy { it.distance }
                        "별점높은순" -> currentList.sortedByDescending { it.place.rating }
                        "리뷰많은순" -> currentList.sortedByDescending { it.place.reviewCount }
                        else -> currentList
                    }

                    _uiState.update { it.copy(place = sortedList) }
                }

                is PathRecommendScreenEvent.onCheckboxClick -> {
                    val placeId = event.place.placeId
                    _selectedPlaceIds.update { current ->
                        if (current.contains(placeId)) current - placeId else current + placeId
                    }
                }

                is PathRecommendScreenEvent.onPathComposeClick -> navigateToPathCompose()
                else -> { /* 처리 */ }
            }
        }
    }

    private fun navigateToPathCompose() {
        val selectedIds = _selectedPlaceIds.value
        if (selectedIds.isEmpty()) return

        _events.value = PathRecommendScreenEvent.Navigation.NavigateToPathCompose(
            placeIds = selectedIds.toList()
        )
    }

    fun clearEvent() { _events.value = null }
}