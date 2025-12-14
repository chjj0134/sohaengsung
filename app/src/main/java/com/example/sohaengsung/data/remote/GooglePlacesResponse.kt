package com.example.sohaengsung.data.remote

import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.data.model.PlaceDetail
import com.example.sohaengsung.data.util.Constants
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
    val reviewCount: Int?,

    @SerializedName("photos")
    val photos: List<GooglePhoto>?
)

data class GooglePhoto(
    @SerializedName("photo_reference")
    val photoReference: String?
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

fun GooglePlaceItem.toPlace(category: String): Place {

    val photoUrls = photos
        ?.mapNotNull { it.photoReference }
        ?.map { ref ->
            "https://maps.googleapis.com/maps/api/place/photo" +
                    "?maxwidth=400" +
                    "&photo_reference=$ref" +
                    "&key=${Constants.GOOGLE_MAP_API_KEY}"
        }
        ?: emptyList()

    return Place(
        placeId = placeId,
        name = name ?: "",
        address = address ?: "",
        latitude = geometry?.location?.lat ?: 0.0,
        longitude = geometry?.location?.lng ?: 0.0,
        hashtags = emptyList(),
        rating = rating ?: 0.0,
        reviewCount = reviewCount ?: 0,
        details = PlaceDetail(),
        category = category,
        photoUrls = photoUrls
    )
}
