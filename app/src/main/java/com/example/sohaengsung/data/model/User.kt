package com.example.sohaengsung.data.model

// 이 버전은 로그인 후 어플에 사용자 프로필을 만드는 버전
data class User(
    val uid: String = "",
    val profilePic: String? = null,
    val bookmarkedPlaces: List<String> = emptyList(),
    val level: Int = 1,
    val activityScore: Int = 0
)