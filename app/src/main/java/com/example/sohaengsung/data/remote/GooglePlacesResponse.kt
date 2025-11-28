package com.example.sohaengsung.data.remote

import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.data.model.PlaceDetail
import com.google.gson.annotations.SerializedName


data class GooglePlacesResponse(
    @SerializedName("results")
    val results: List<GooglePlaceItem>
)

data class GooglePlaceItem(
    @SerializedName("place_id")
    val placeId: String,

    @SerializedName("name")
    val name: String?,

    @SerializedName("vicinity")
    val address: String?,

    @SerializedName("geometry")
    val geometry: Geometry?,

    @SerializedName("types")
    val types: List<String>?,

    @SerializedName("rating")
    val rating: Double?,

    @SerializedName("user_ratings_total")
    val reviewCount: Int?
)

data class Geometry(
    @SerializedName("location")
    val location: Location
)

data class Location(
    @SerializedName("lat")
    val lat: Double,

    @SerializedName("lng")
    val lng: Double
)

fun GooglePlaceItem.toPlace(): Place {

    // 기본 type 기반 해시태그
    val baseTags = types?.map { it.toKoreanHashtag() } ?: emptyList()

    // 카공/스터디 태그 자동 추가
    val studyTags = mutableListOf<String>()

    if (types?.contains("cafe") == true) {
        studyTags += "와이파이"
        studyTags += "콘센트"
    }
    if (types?.contains("library") == true) {
        studyTags += "와이파이"
    }

    return Place(
        placeId = placeId,
        name = name ?: "",
        address = address ?: "",
        latitude = geometry?.location?.lat ?: 0.0,
        longitude = geometry?.location?.lng ?: 0.0,
        hashtags = baseTags + studyTags,
        rating = rating ?: 0.0,
        reviewCount = reviewCount ?: 0,
        details = PlaceDetail()
    )
}

private fun String.toKoreanHashtag(): String {
    return when (this) {
        // 카공 관련
        "cafe" -> "카페"
        "book_store" -> "북카페"
        "library" -> "도서관"
        "restaurant" -> "브런치"

        // 분위기 장소
        "park" -> "공원"
        "tourist_attraction" -> "명소"
        "point_of_interest" -> "핫플"
        "art_gallery" -> "갤러리"
        "movie_theater" -> "영화관"
        "museum" -> "박물관"

        // 접근성 장소
        "subway_station" -> "역근처"
        "bus_station" -> "버스정류장"
        "train_station" -> "기차역"
        "transit_station" -> "환승센터"

        else -> this
    }
}
