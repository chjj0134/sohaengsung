package com.example.sohaengsung.data.model

import com.google.firebase.Timestamp

data class Review(
    val reviewId: String = "",
    val userId: String = "",
    val userNickname: String = "",
    val placeId: String = "",
    val rating: Double = 0.0,
    val content: String = "",
    val tags: List<String> = emptyList(),
    val createdAt: Timestamp? = null,
    val source: String = "USER"
)
