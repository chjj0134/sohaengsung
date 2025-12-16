package com.example.sohaengsung.ui.features.level

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sohaengsung.data.repository.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LevelViewModel : ViewModel() {

    private val userRepository = UserRepository()

    private val _uiState = MutableStateFlow(LevelScreenUiState())
    val uiState: StateFlow<LevelScreenUiState> = _uiState.asStateFlow()

    private val _events = MutableStateFlow<LevelScreenEvent?>(null)
    val events: StateFlow<LevelScreenEvent?> = _events.asStateFlow()

    private var userListener: ListenerRegistration? = null

    init {
        loadUserData()
        startUserRealtimeListener()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
            val user = userRepository.getUser(uid) ?: return@launch

            _uiState.update {
                it.copy(
                    user = user,
                    nextScoreNeeded = calculateNextScore(user.activityScore),
                    remainingReviews = calculateRemainingReviews(
                        user.reviewCount,
                        user.level
                    ),
                    isLoading = false
                )
            }
        }
    }

    private fun startUserRealtimeListener() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        userListener = userRepository.listenUser(uid) { user ->
            _uiState.update {
                it.copy(
                    user = user,
                    nextScoreNeeded = calculateNextScore(user.activityScore),
                    remainingReviews = calculateRemainingReviews(
                        user.reviewCount,
                        user.level
                    )
                )
            }
        }
    }

    private fun calculateNextScore(activityScore: Int): Int {
        return when {
            activityScore < 3 -> 3 - activityScore
            activityScore < 15 -> 15 - activityScore
            activityScore < 30 -> 30 - activityScore
            activityScore < 60 -> 60 - activityScore
            else -> 0
        }
    }

    private fun calculateRemainingReviews(
        reviewCount: Int,
        level: Int
    ): Int {
        return when (level) {
            1 -> (1 - reviewCount).coerceAtLeast(0)
            2 -> (5 - reviewCount).coerceAtLeast(0)
            3 -> (10 - reviewCount).coerceAtLeast(0)
            4 -> (20 - reviewCount).coerceAtLeast(0)
            else -> 0
        }
    }

    fun onEvent(event: LevelScreenEvent) {
        when (event) {
            LevelScreenEvent.onLevelInfoClick ->
                _events.value = LevelScreenEvent.Navigation.NavigateToLevelInfo

            is LevelScreenEvent.Navigation -> {}
        }
    }

    fun clearEvent() {
        _events.value = null
    }

    override fun onCleared() {
        super.onCleared()
        userListener?.remove()
    }
}
