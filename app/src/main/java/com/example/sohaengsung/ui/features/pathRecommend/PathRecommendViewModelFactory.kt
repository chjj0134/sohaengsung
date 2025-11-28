package com.example.sohaengsung.ui.features.pathRecommend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sohaengsung.data.repository.BookmarkRepository
import com.example.sohaengsung.data.repository.PlaceRepository

class PathRecommendViewModelFactory(
    private val uid: String
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PathRecommendViewModel::class.java)) {
            return PathRecommendViewModel(
                bookmarkRepository = BookmarkRepository(),
                placeRepository = PlaceRepository(),      // //추가
                uid = uid
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

