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
    val details: PlaceDetail = PlaceDetail(),

    //카테고리 : cafe, bookstore, select_shop, gallery
    val category: String = "",
    val photoUrls: List<String> = emptyList()

)

data class PlaceDetail(
    val wifi: Boolean = false,
    val parking: Boolean = false,
    val kidsZone: Boolean = false,
    val signatureMenu: String? = null,

    //구글 상세 정보
    val phone: String? = null,
    val website: String? = null,
    val openingHours: List<String> = emptyList(),
    val reviews: List<GoogleReview> = emptyList()
)

//구글 리뷰 구조
data class GoogleReview(
    val author: String = "",
    val rating: Int = 0,
    val time: String = "",
    val content: String = "",
    val profilePhotoUrl: String? = null
)
