import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sohaengsung.data.repository.UserRepository
import com.example.sohaengsung.ui.features.home.HomeScreenEvent
import com.example.sohaengsung.ui.features.home.HomeScreenUiState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel : ViewModel() {
    private val userRepository = UserRepository()

    private val _uiState = MutableStateFlow(HomeScreenUiState())
    val uiState: StateFlow<HomeScreenUiState> = _uiState.asStateFlow()

    private val _events = MutableStateFlow<HomeScreenEvent?>(null)
    val events: StateFlow<HomeScreenEvent?> = _events.asStateFlow()

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
                            errorMessage = "사용자 정보를 찾을 수 없습니다.",
                            isLoading = false
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.update { it.copy(errorMessage = e.message, isLoading = false) }
            }
        }
    } // loadUserData 함수가 여기서 정확히 끝나야 함

    // 이제 HomeScreen에서 호출 가능한 공용 함수가 됨
    fun onEvent(event: HomeScreenEvent) {
        viewModelScope.launch {
            when (event) {
                HomeScreenEvent.onPlaceRecommendClick -> {
                    _events.value = HomeScreenEvent.Navigation.NavigateToPlaceRecommend
                }
                HomeScreenEvent.onPathRecommendClick -> {
                    _events.value = HomeScreenEvent.Navigation.NavigateToPathRecommend
                }
                HomeScreenEvent.onBookmarkClick -> {
                    _events.value = HomeScreenEvent.Navigation.NavigateToBookmark
                }
                HomeScreenEvent.onCouponClick -> {
                    _events.value = HomeScreenEvent.Navigation.NavigateToCoupon
                }
                HomeScreenEvent.onEventClick -> {
                    _events.value = HomeScreenEvent.Navigation.NavigateToEvent
                }
                HomeScreenEvent.onSettingClick -> {
                    _events.value = HomeScreenEvent.Navigation.NavigateToSetting
                }
                is HomeScreenEvent.Navigation -> {
                    /* do nothing */
                }
            }
        }
    }

    fun clearEvent() {
        _events.value = null
    }
}