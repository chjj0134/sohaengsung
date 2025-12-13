package com.example.sohaengsung.ui.features.bookmarked

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sohaengsung.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import com.example.sohaengsung.data.repository.BookmarkRepository
import com.example.sohaengsung.data.repository.PlaceRepository
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.ui.features.pathRecommend.PlaceWithDistance

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
                        it.copy(
                            errorMessage = "사용자 정보가 없습니다.",
                            isLoading = false
                        )
                    }
                }

            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        errorMessage = e.message ?: "알 수 없는 오류",
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun loadBookmarkedPlaces() {
        viewModelScope.launch {

            val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch

            try {
                val ids = bookmarkRepository.getBookmarksOnce(uid)

                val rawPlaces = placeRepository.getPlaces(ids)

                val placesWithDistance = rawPlaces.map { place ->
                    PlaceWithDistance(place = place, distance = 0.0)
                }

                _uiState.update { it.copy(bookmarkedPlaces = placesWithDistance) }

            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message ?: "북마크 로드 실패") }
            }
        }
    }

    private suspend fun deleteBookmark(placeId: String) {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        try {
            // 서버(Repository)에서 삭제
            bookmarkRepository.removeBookmark(uid, placeId)

            _uiState.update { state ->
                val updatedList = state.bookmarkedPlaces.filter {
                    it.place.placeId != placeId
                }
                state.copy(bookmarkedPlaces = updatedList)
            }
        } catch (e: Exception) {
            _uiState.update { it.copy(errorMessage = "삭제 실패: ${e.message}") }
        }
    }

    fun onEvent(event: BookmarkScreenEvent) {
        viewModelScope.launch {
            when (event) {
                is BookmarkScreenEvent.onDeleteClick -> {
                    val targetId = event.placeWithDistance.place.placeId
                    deleteBookmark(targetId)
                }

                is BookmarkScreenEvent.Navigation -> {
                    /* do nothing */
                }
            }
        }
    }

    fun clearEvent() {
        _events.value = null
    }
}