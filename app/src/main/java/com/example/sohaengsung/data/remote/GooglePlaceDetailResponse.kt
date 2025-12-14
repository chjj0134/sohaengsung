package com.example.sohaengsung.data.remote

import com.google.gson.annotations.SerializedName

data class GooglePlaceDetailResponse(
    @SerializedName("result")
    val result: GooglePlaceDetailResult?
)

data class GooglePlaceDetailResult(

    @SerializedName("formatted_address")
    val address: String?,

    @SerializedName("formatted_phone_number")
    val phoneNumber: String?,

    @SerializedName("website")
    val website: String?,

    @SerializedName("rating")
    val rating: Double?,

    @SerializedName("user_ratings_total")
    val reviewCount: Int?,

    @SerializedName("opening_hours")
    val openingHours: OpeningHours?,

    @SerializedName("reviews")
    val reviews: List<GoogleReview>?,

    @SerializedName("photos")
    val photos: List<GooglePhotoDetail>?
)

data class GooglePhotoDetail(
    @SerializedName("photo_reference")
    val photoReference: String?,

    @SerializedName("height")
    val height: Int?,

    @SerializedName("width")
    val width: Int?
)

data class OpeningHours(
    @SerializedName("weekday_text")
    val weekdayText: List<String>?
)

data class GoogleReview(
    @SerializedName("author_name")
    val authorName: String? = null,

    @SerializedName("rating")
    val rating: Int? = null,

    @SerializedName("relative_time_description")
    val relativeTimeDescription: String? = null,

    @SerializedName("text")
    val text: String? = null,

    @SerializedName("profile_photo_url")
    val profilePhotoUrl: String? = null
)
