package com.example.sohaengsung.ui.features.pathRecommend

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.data.repository.BookmarkRepository
import com.example.sohaengsung.data.repository.PlaceRepository
import com.example.sohaengsung.ui.features.placeRecommend.PlaceRecommendScreenEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PathRecommendViewModel(
    private val bookmarkRepository: BookmarkRepository,
    private val placeRepository: PlaceRepository,
    private val uid: String
) : ViewModel() {

    private val _bookmarkIds = MutableStateFlow<List<String>>(emptyList())
    val bookmarkIds = _bookmarkIds.asStateFlow()

    private val _bookmarkPlaces = MutableStateFlow<List<Place>>(emptyList())
    val bookmarkPlaces = _bookmarkPlaces.asStateFlow()

    private val _uiState = MutableStateFlow(PathRecommendScreenUiState())
    val uiState: StateFlow<PathRecommendScreenUiState> = _uiState.asStateFlow()

    private val _events = MutableStateFlow<PathRecommendScreenEvent.Navigation?>(null)
    val events: StateFlow<PathRecommendScreenEvent.Navigation?> = _events.asStateFlow()

    private val _selectedPlaceIds = MutableStateFlow<Set<String>>(emptySet())
    val selectedPlaceIds: StateFlow<Set<String>> = _selectedPlaceIds.asStateFlow()

    init {
        observeBookmarkIds()
    }

    //  Firestore 북마크 자동 감지
    private fun observeBookmarkIds() {
        viewModelScope.launch {
            bookmarkRepository.observeBookmarks(uid).collectLatest { ids ->
                _bookmarkIds.value = ids

                // placeId → Place 변환 (지금은 dummy, 나중에 DB 연동)
                loadPlaces(ids)
            }
        }
    }

    private suspend fun loadPlaces(ids: List<String>) {
        // 지금은 UI dummy 예시 사용 (프론트 스크린 참고)
        val places = placeRepository.getPlaces(ids)
        _bookmarkPlaces.value = places

        _uiState.value = _uiState.value.copy(
            place = places,
            isLoading = false
        )
    }

    private fun navigateToPathCompose() {
        val selectedIds = _selectedPlaceIds.value

        if (selectedIds.isEmpty()) {
            Log.d("PathVM", "선택된 장소가 없습니다.")
            return
        }

        val selectedPlaces = _uiState.value.place.filter { place ->
            selectedIds.contains(place.placeId)
        }

        _events.value = PathRecommendScreenEvent.Navigation.NavigateToPathCompose(
            placeIds = selectedPlaces.map { it.placeId }
        )
    }

    fun onEvent(event: PathRecommendScreenEvent) {
        viewModelScope.launch {
            when (event) {
                is PathRecommendScreenEvent.onDropDownClick -> {
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

                is PathRecommendScreenEvent.onCheckboxClick -> {
                    val place = event.place // Place 객체를 가정

                    _selectedPlaceIds.value = if (_selectedPlaceIds.value.contains(place.placeId)) {
                        // 이미 선택되어 있으면 제거
                        _selectedPlaceIds.value.minus(place.placeId)
                    } else {
                        // 선택되어 있지 않으면 추가
                        _selectedPlaceIds.value.plus(place.placeId)
                    }.toSet()
                }

                is PathRecommendScreenEvent.onPathComposeClick -> {
                    navigateToPathCompose()
                    // _events.value = PathRecommendScreenEvent.Navigation.NavigateToPathCompose
                }

                is PathRecommendScreenEvent.Navigation -> {
                    /* do nothing */
                }
            }
        }
    }

    fun clearEvent() {
        _events.value = null
    }
}