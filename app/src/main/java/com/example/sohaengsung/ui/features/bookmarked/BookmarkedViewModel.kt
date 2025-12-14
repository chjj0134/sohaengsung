package com.example.sohaengsung.ui.features.bookmarked

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.data.repository.BookmarkRepository
import com.example.sohaengsung.data.repository.PlaceRepository
import com.example.sohaengsung.data.repository.UserRepository
import com.example.sohaengsung.ui.features.pathRecommend.PlaceWithDistance
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.math.*


class BookmarkedViewModel : ViewModel() {

    private val userRepository = UserRepository()
    private val bookmarkRepository = BookmarkRepository()
    private val placeRepository = PlaceRepository()

    private val _uiState = MutableStateFlow(BookmarkedScreenUiState())
    val uiState: StateFlow<BookmarkedScreenUiState> = _uiState.asStateFlow()

    private val _events = MutableStateFlow<BookmarkScreenEvent?>(null)
    val events: StateFlow<BookmarkScreenEvent?> = _events.asStateFlow()

    init {
        loadUserData()
        loadBookmarkedPlaces()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, errorMessage = null) }

                val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
                val user = userRepository.getUser(uid)

                if (user != null) {
                    _uiState.update { it.copy(user = user, isLoading = false) }
                } else {
                    _uiState.update {
                        it.copy(errorMessage = "사용자 정보가 없습니다.", isLoading = false)
                    }
                }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(errorMessage = e.message ?: "알 수 없는 오류", isLoading = false)
                }
            }
        }
    }

    private fun loadBookmarkedPlaces() {
        viewModelScope.launch {
            val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch

            try {
                // 1. 북마크된 ID 리스트 가져오기
                val ids = bookmarkRepository.getBookmarksOnce(uid)

                // 2. ID를 이용해 장소 상세 정보 가져오기
                val rawPlaces = placeRepository.getPlaces(ids)

                // 3. PlaceWithDistance 모델로 변환
                val placesWithDistance = rawPlaces.map { place ->
                    PlaceWithDistance(place = place, distance = 0.0)
                }

                _uiState.update { it.copy(bookmarkedPlaces = placesWithDistance) }

                // 4. 초기 거리 계산 (서울시청 기준 초기화 예시)
                calculateDistances(37.5665, 126.9780)

            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message ?: "북마크 로드 실패") }
            }
        }
    }

    /**
     * 현재 위치를 기준으로 모든 장소와의 거리를 계산합니다.
     */
    fun calculateDistances(lat: Double, lng: Double) {
        _uiState.update { state ->
            val updatedList = state.bookmarkedPlaces.map { item ->
                val dist = computeHaversineDistance(
                    lat, lng,
                    item.place.latitude, item.place.longitude
                )
                item.copy(distance = dist)
            }
            state.copy(bookmarkedPlaces = updatedList)
        }
    }

    /**
     * 하버사인 공식을 이용한 직선 거리 계산 (단위: m)
     */
    private fun computeHaversineDistance(lat1: Double, lon1: Double, lat2: Double, lon2: Double): Double {
        val r = 6371e3 // 지구 반지름 (meters)
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        val a = sin(dLat / 2).pow(2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2).pow(2)
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        return r * c
    }

    private suspend fun deleteBookmark(placeId: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        try {
            bookmarkRepository.removeBookmark(uid, placeId)

            _uiState.update { state ->
                val updatedList = state.bookmarkedPlaces.filter { it.place.placeId != placeId }
                state.copy(bookmarkedPlaces = updatedList)
            }
            Log.d("BookmarkVM", "삭제 성공: $placeId")
        } catch (e: Exception) {
            _uiState.update { it.copy(errorMessage = "삭제 실패: ${e.message}") }
        }
    }

    fun onEvent(event: BookmarkScreenEvent) {
        viewModelScope.launch {
            when (event) {
                is BookmarkScreenEvent.onDropDownClick -> {
                    val criteria = event.sortCriteria
                    val currentList = _uiState.value.bookmarkedPlaces

                    val sortedList = when (criteria) {
                        "거리순" -> currentList.sortedBy { it.distance }
                        "별점높은순" -> currentList.sortedByDescending { it.place.rating }
                        "리뷰많은순" -> currentList.sortedByDescending { it.place.reviewCount }
                        else -> currentList
                    }

                    _uiState.update { it.copy(bookmarkedPlaces = sortedList) }
                }

                is BookmarkScreenEvent.onDeleteClick -> {
                    val targetId = event.placeWithDistance.place.placeId
                    deleteBookmark(targetId)
                }

                is BookmarkScreenEvent.Navigation -> { /* logic */ }
            }
        }
    }

    fun clearEvent() {
        _events.value = null
    }
}