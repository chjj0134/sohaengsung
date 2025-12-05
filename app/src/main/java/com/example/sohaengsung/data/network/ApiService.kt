package com.example.sohaengsung.data.network

import com.example.sohaengsung.data.remote.GooglePlacesResponse
import com.example.sohaengsung.data.remote.GooglePlaceDetailResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    // 주변 장소 검색 (Nearby Search)
    @GET("place/nearbysearch/json")
    suspend fun getNearbyPlaces(
        @Query("location") location: String,
        @Query("radius") radius: Int = 1500,
        @Query("type") type: String,
        @Query("key") apiKey: String,
        @Query("language") language: String? = null
    ): GooglePlacesResponse


    // 장소 상세 정보 + 리뷰 조회 (Place Details)
    @GET("place/details/json")
    suspend fun getPlaceDetails(
        @Query("place_id") placeId: String,
        @Query("key") apiKey: String,
        @Query("language") language: String = "ko"
    ): GooglePlaceDetailResponse
}
