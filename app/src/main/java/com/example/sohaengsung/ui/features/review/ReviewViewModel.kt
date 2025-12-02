package com.example.sohaengsung.ui.features.review

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ReviewViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(ReviewScreenUiState())
    val uiState: StateFlow<ReviewScreenUiState> = _uiState.asStateFlow()

    private val _events = MutableStateFlow<ReviewScreenEvent.Navigation?>(null)
    val events: StateFlow<ReviewScreenEvent.Navigation?> = _events.asStateFlow()

    fun updateRating(rating: Int) {
        // 같은 별을 다시 클릭하면 0으로, 아니면 해당 별점으로 설정
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
            
            // 임시로 성공 처리
            _uiState.value = _uiState.value.copy(isLoading = false)
            _events.value = ReviewScreenEvent.Navigation.NavigateBack
        }
    }

    fun onBackClick() {
        _events.value = ReviewScreenEvent.Navigation.NavigateBack
    }

    fun clearEvent() {
        _events.value = null
    }
}

