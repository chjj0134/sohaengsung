package com.example.sohaengsung.ui.features.mapPathRecommend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.data.repository.BookmarkRepository
import com.example.sohaengsung.data.repository.PlaceRepository
import com.example.sohaengsung.data.util.DistanceCalculator
import com.example.sohaengsung.data.util.LocationService
import com.example.sohaengsung.ui.dummy.placeExample
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MapPathRecommendViewModel(
    private val bookmarkRepository: BookmarkRepository,
    private val placeRepository: PlaceRepository,
    private val locationService: LocationService,
    private val uid: String
) : ViewModel() {

    private val _uiState = MutableStateFlow(MapPathRecommendScreenUiState())
    val uiState: StateFlow<MapPathRecommendScreenUiState> = _uiState.asStateFlow()

    init {
        loadPathRecommendation()
    }

    private fun loadPathRecommendation() {
        viewModelScope.launch {
            try {
                _uiState.update { it.copy(isLoading = true, errorMessage = null) }

                // TODO: 더미 데이터 사용 (테스트용)
                // 실제 데이터 사용 시 아래 주석을 해제하고 더미 데이터 부분을 제거
                
                // ========== 더미 데이터 사용 시작 ==========
                // 1. 더미 현재 위치 (서울 중심: 명동)
                val currentLocation = Pair(37.5665, 126.9780)
                
                // 2. 더미 북마크된 장소 (placeExample에서 가져오기)
                val places = placeExample.take(3) // 최대 3개만 사용
                
                if (places.isEmpty()) {
                    _uiState.update {
                        it.copy(
                            currentLocation = currentLocation,
                            isLoading = false,
                            errorMessage = "북마크된 장소가 없습니다."
                        )
                    }
                    return@launch
                }
                // ========== 더미 데이터 사용 끝 ==========
                
                /* 실제 데이터 사용 코드 (주석 처리됨)
                // 1. 현재 위치 가져오기
                val currentLocation = locationService.getCurrentLocation()
                if (currentLocation == null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "현재 위치를 가져올 수 없습니다."
                        )
                    }
                    return@launch
                }

                // 2. 북마크된 장소 ID 리스트 가져오기
                val bookmarkIds = bookmarkRepository.getBookmarksOnce(uid)
                if (bookmarkIds.isEmpty()) {
                    _uiState.update {
                        it.copy(
                            currentLocation = currentLocation,
                            isLoading = false,
                            errorMessage = "북마크된 장소가 없습니다."
                        )
                    }
                    return@launch
                }

                // 3. Place 객체 리스트로 변환
                val places = placeRepository.getPlaces(bookmarkIds)
                if (places.isEmpty()) {
                    _uiState.update {
                        it.copy(
                            currentLocation = currentLocation,
                            isLoading = false,
                            errorMessage = "장소 정보를 가져올 수 없습니다."
                        )
                    }
                    return@launch
                }
                */

                // 4. 거리 기반으로 정렬 (최대 3개)
                val sortedPlaces = sortPlacesByDistance(currentLocation, places)

                _uiState.update {
                    it.copy(
                        currentLocation = currentLocation,
                        sortedPlaces = sortedPlaces,
                        isLoading = false
                    )
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "알 수 없는 오류가 발생했습니다."
                    )
                }
            }
        }
    }

    /**
     * 거리 기반으로 장소들을 정렬
     * 1번: 현재 위치에서 가장 가까운 곳
     * 2번: 1번 장소에서 가장 가까운 곳
     * 3번: 2번 장소에서 가장 가까운 곳
     */
    private fun sortPlacesByDistance(
        currentLocation: Pair<Double, Double>,
        places: List<Place>
    ): List<PlaceWithOrder> {
        if (places.isEmpty()) return emptyList()

        val result = mutableListOf<PlaceWithOrder>()
        val remainingPlaces = places.toMutableList()
        var currentLat = currentLocation.first
        var currentLon = currentLocation.second

        // 최대 3개까지만 선택
        for (order in 1..minOf(3, places.size)) {
            // 남은 장소들 중에서 현재 위치에서 가장 가까운 장소 찾기
            val nearestPlace = remainingPlaces.minByOrNull { place ->
                DistanceCalculator.calculateDistance(
                    currentLat,
                    currentLon,
                    place.latitude,
                    place.longitude
                )
            } ?: break

            // 거리 계산
            val distance = DistanceCalculator.calculateDistance(
                currentLat,
                currentLon,
                nearestPlace.latitude,
                nearestPlace.longitude
            )

            // 결과에 추가
            result.add(
                PlaceWithOrder(
                    place = nearestPlace,
                    order = order,
                    distanceFromPrevious = if (order == 1) null else distance
                )
            )

            // 다음 반복을 위해 현재 위치를 찾은 장소로 업데이트
            currentLat = nearestPlace.latitude
            currentLon = nearestPlace.longitude

            // 선택된 장소를 리스트에서 제거
            remainingPlaces.remove(nearestPlace)
        }

        return result
    }

    fun refresh() {
        loadPathRecommendation()
    }
}

