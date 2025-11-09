package com.example.sohaengsung.data.model

import com.google.firebase.Timestamp

data class Event(
    val eventId: String = "",
    val title: String = "",
    val tags: List<String> = emptyList(),
    val startDate: Timestamp? = null,
    val endDate: Timestamp? = null,
    val imageUrl: String = "",
    val externalLink: String = ""
)