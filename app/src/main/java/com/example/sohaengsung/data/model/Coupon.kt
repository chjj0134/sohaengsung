package com.example.sohaengsung.data.model


data class Coupon(
    val couponId: String = "",
    val userId: String = "",
    val placeId: String = "",
    val stampCount: Int = 0,
    val completed: Boolean = false
)
