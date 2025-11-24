package com.example.sohaengsung.ui.features.pathRecommend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.data.repository.BookmarkRepository
import com.example.sohaengsung.ui.dummy.placeExample
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class PathRecommendViewModel(
    private val repository: BookmarkRepository,
    private val uid: String
) : ViewModel() {

    private val _bookmarkIds = MutableStateFlow<List<String>>(emptyList())
    val bookmarkIds = _bookmarkIds.asStateFlow()

    private val _bookmarkPlaces = MutableStateFlow<List<Place>>(emptyList())
    val bookmarkPlaces = _bookmarkPlaces.asStateFlow()

    init {
        observeBookmarkIds()
    }

    //  Firestore 북마크 자동 감지
    private fun observeBookmarkIds() {
        viewModelScope.launch {
            repository.observeBookmarks(uid).collectLatest { ids ->
                _bookmarkIds.value = ids

                // placeId → Place 변환 (지금은 dummy, 나중에 DB 연동)
                loadPlaces(ids)
            }
        }
    }

    // placeId 리스트 → Place 객체 리스트로 변환
    private fun loadPlaces(ids: List<String>) {
        // 지금은 UI dummy 예시 사용 (프론트 스크린 참고)
        val dummy = placeExample

        _bookmarkPlaces.value = dummy.filter { ids.contains(it.placeId) }
    }
}