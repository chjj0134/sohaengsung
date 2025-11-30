package com.example.sohaengsung.ui.features.level

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sohaengsung.data.repository.UserRepository
import com.example.sohaengsung.ui.dummy.userExample
import com.google.firebase.auth.FirebaseAuth
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

    init {
        loadUserData()
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