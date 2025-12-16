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
            android.util.Log.d("ReviewDebug", "제출 시도 - placeId: $placeId, uid: ${FirebaseAuth.getInstance().currentUser?.uid}")

            _uiState.value = _uiState.value.copy(isLoading = true)

            val uid = FirebaseAuth.getInstance().currentUser?.uid
            if (uid == null) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "로그인이 필요합니다."
                )
                return@launch
            }

            // 선택된 태그들을 하나의 리스트로 합침
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
                tags = tags,
                createdAt = com.google.firebase.Timestamp.now()
            )

            try {
                reviewRepository.addReview(review)

                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    showSuccessModal = true // 성공 모달 표시
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    errorMessage = "리뷰 등록에 실패했습니다: ${e.message}"
                )
            }
        }
    }

    fun onConfirmSuccess() {
        _uiState.value = _uiState.value.copy(showSuccessModal = false)
        _events.value = ReviewScreenEvent.Navigation.NavigateBack
    }

    fun onBackClick() {
        _events.value = ReviewScreenEvent.Navigation.NavigateBack
    }

    fun onEvent(event: ReviewScreenEvent) {
        when (event) {
            is ReviewScreenEvent.OnRatingClick -> {
                val newRating = if (_uiState.value.rating == event.rating) 0 else event.rating
                _uiState.value = _uiState.value.copy(rating = newRating)
            }

            is ReviewScreenEvent.OnReviewTextChange -> {
                _uiState.value = _uiState.value.copy(reviewText = event.text)
            }

            is ReviewScreenEvent.OnTagSelect -> {
                when (event.tagType) {
                    ReviewScreenEvent.TagType.THEME -> toggleThemeTag(event.tag)
                    ReviewScreenEvent.TagType.ATMOSPHERE -> toggleAtmosphereTag(event.tag)
                    ReviewScreenEvent.TagType.CONVENIENCE -> toggleConvenienceTag(event.tag)
                }
            }

            ReviewScreenEvent.OnSubmitReview -> {
                submitReview()
            }

            ReviewScreenEvent.OnBackClick -> {
                _events.value = ReviewScreenEvent.Navigation.NavigateBack
            }

            else -> { /* Navigation 이벤트 등 처리 */ }
        }
    }

    fun clearEvent() {
        _events.value = null
    }
}
