package com.example.sohaengsung.data.model

data class Place(
    val placeId: String = "",
    val name: String = "",
    val address: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val hashtags: List<String> = emptyList(),
    val rating: Double = 0.0,
    val reviewCount: Int = 0,
    val details: PlaceDetail = PlaceDetail()
)

data class PlaceDetail(
    val wifi: Boolean = false,
    val parking: Boolean = false,
    val kidsZone: Boolean = false,
    val signatureMenu: String? = null
)