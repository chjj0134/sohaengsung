package com.example.sohaengsung.data.model

data class Level(
    val uid: String = "",
    val currentLevel: Int = 1,
    val activityScore: Int = 0,
    val nextLevelThreshold: Int = 100
)