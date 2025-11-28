package com.example.sohaengsung.data.network

import com.example.sohaengsung.data.remote.GooglePlacesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("place/nearbysearch/json")
    suspend fun getNearbyPlaces(
        @Query("location") location: String,     // "37.5665,126.9780"
        @Query("radius") radius: Int = 1500,     // 반경 (미터)
        @Query("type") type: String = "cafe",    // 1개만 허용
        @Query("key") apiKey: String
    ): GooglePlacesResponse
}
