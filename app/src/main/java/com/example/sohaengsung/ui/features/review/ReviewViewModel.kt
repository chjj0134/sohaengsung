package com.example.sohaengsung.ui.features.review

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sohaengsung.data.model.Review
import com.example.sohaengsung.data.repository.ReviewRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReviewViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(ReviewScreenUiState())
    val uiState: StateFlow<ReviewScreenUiState> = _uiState.asStateFlow()

    private val _events = MutableStateFlow<ReviewScreenEvent.Navigation?>(null)
    val events: StateFlow<ReviewScreenEvent.Navigation?> = _events.asStateFlow()

    private val reviewRepository = ReviewRepository()
    private val placeId: String = savedStateHandle.get<String>("placeId") ?: ""

    fun updateRating(rating: Int) {
        val newRating = if (_uiState.value.rating == rating) 0 else rating
        _uiState.value = _uiState.value.copy(rating = newRating)
    }

    fun updateReviewText(text: String) {
        _uiState.value = _uiState.value.copy(reviewText = text)
    }

    fun toggleThemeTag(tag: String) {
        val currentTag = _uiState.value.selectedThemeTag
        val newTag = if (currentTag == tag) null else tag
        _uiState.value = _uiState.value.copy(selectedThemeTag = newTag)
    }

    fun toggleAtmosphereTag(tag: String) {
        val currentTag = _uiState.value.selectedAtmosphereTag
        val newTag = if (currentTag == tag) null else tag
        _uiState.value = _uiState.value.copy(selectedAtmosphereTag = newTag)
    }

    fun toggleConvenienceTag(tag: String) {
        val currentTag = _uiState.value.selectedConvenienceTag
        val newTag = if (currentTag == tag) null else tag
        _uiState.value = _uiState.value.copy(selectedConvenienceTag = newTag)
    }

    fun submitReview() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)

            // TODO: 실제 리뷰 제출 로직 구현
            // 예: repository.submitReview(...)

            val uid = FirebaseAuth.getInstance().currentUser?.uid
            if (uid == null) {
                _uiState.value = _uiState.value.copy(isLoading = false)
                return@launch
            }

            val tags = listOfNotNull(
                _uiState.value.selectedThemeTag,
                _uiState.value.selectedAtmosphereTag,
                _uiState.value.selectedConvenienceTag
            )

            val review = Review(
                reviewId = "",
                userId = uid,
                placeId = placeId,
                rating = _uiState.value.rating.toDouble(),
                content = _uiState.value.reviewText,
                tags = tags
            )

            try {
                reviewRepository.addReview(review)
                _uiState.value = _uiState.value.copy(isLoading = false)
                _events.value = ReviewScreenEvent.Navigation.NavigateBack
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(isLoading = false)
            }
        }
    }

    fun onBackClick() {
        _events.value = ReviewScreenEvent.Navigation.NavigateBack
    }

    fun clearEvent() {
        _events.value = null
    }
}
