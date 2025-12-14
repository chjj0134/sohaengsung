package com.example.sohaengsung.data.model

import com.google.firebase.Timestamp

data class Event(
    val eventId: String = "",
    val title: String = "",
    val tags: List<String> = emptyList(),
    val startDate: Timestamp? = null,
    val endDate: Timestamp? = null,
    val seasonInfo: String = "날짜 미정",
    val imageUrl: String = "",
    val externalLink: String = "",
    val countdown: String = "" // D-20, D-3, 진행중 등
)