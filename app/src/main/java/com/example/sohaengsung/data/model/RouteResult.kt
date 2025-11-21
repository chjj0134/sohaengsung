package com.example.sohaengsung.data.model

data class RouteResult(
    val orderedPlaces: List<Place> = emptyList(),       // 방문 순서
    val totalDistanceMeters: Double = 0.0,              // 총 이동 거리
    val segments: List<RouteSegment> = emptyList()      // A→B , B→C 상세 거리
)

data class RouteSegment(
    val fromName: String,
    val toName: String,
    val distanceMeters: Double
)
