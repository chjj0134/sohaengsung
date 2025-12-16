package com.example.sohaengsung.ui.features.mapPathRecommend

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sohaengsung.data.repository.BookmarkRepository
import com.example.sohaengsung.data.repository.PlaceRepository
import com.example.sohaengsung.data.util.LocationService

class MapPathRecommendViewModelFactory(
    private val uid: String,
    private val context: Context,
    private val placeIds: List<String>? = null
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MapPathRecommendViewModel::class.java)) {
            return MapPathRecommendViewModel(
                bookmarkRepository = BookmarkRepository(),
                placeRepository = PlaceRepository(),
                locationService = LocationService(context),
                uid = uid,
                placeIds = placeIds
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

