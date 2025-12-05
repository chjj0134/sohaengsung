package com.example.sohaengsung.ui.features.level

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sohaengsung.data.repository.UserRepository
import com.example.sohaengsung.ui.dummy.userExample
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
            try {
                _uiState.update { it.copy(isLoading = true, errorMessage = null) }

                val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return@launch
                val user = userRepository.getUser(uid)

                if (user != null) {
                    val nextScore = calculateNextScore(user.activityScore)
                    val remainingReviews = calculateRemainingReviews(nextScore)
                    _uiState.update {
                        it.copy(
                            user = user,
                            nextScoreNeeded = nextScore,
                            remainingReviews = remainingReviews,
                            isLoading = false
                        ) }
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

    private fun startUserRealtimeListener() {
        val uid = FirebaseAuth.getInstance().currentUser?.uid ?: return

        userListener = userRepository.listenUser(uid) { user ->
            _uiState.update {
                it.copy(
                    user = user,
                    nextScoreNeeded = calculateNextScore(user.activityScore)
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
            else -> 1
        }
    }

    private fun calculateRemainingReviews(nextScoreNeeded: Int): Int {
        return if (nextScoreNeeded <= 0) 0 else ((nextScoreNeeded + 14) / 15)
    }


    fun onEvent(event: LevelScreenEvent) {
        viewModelScope.launch {
            when (event) {
                LevelScreenEvent.onLevelInfoClick -> {
                    _events.value = LevelScreenEvent.Navigation.NavigateToLevelInfo
                }

                is LevelScreenEvent.Navigation -> {
                    /* do nothing */
                }
            }
        }
    }

    fun clearEvent() {
        _events.value = null
    }
}