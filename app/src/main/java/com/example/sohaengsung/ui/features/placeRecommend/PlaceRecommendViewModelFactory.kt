package com.example.sohaengsung.ui.features.placeRecommend

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sohaengsung.data.repository.PlaceRepository
import com.example.sohaengsung.data.util.LocationService

class PlaceRecommendViewModelFactory(
    private val uid: String,
    private val placeRepository: PlaceRepository = PlaceRepository(),
    private val locationService: LocationService? = null
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PlaceRecommendViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return PlaceRecommendViewModel(
                placeRepository = placeRepository,
                locationService = locationService,
                uid = uid
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
    }
}