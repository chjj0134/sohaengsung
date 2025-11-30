package com.example.sohaengsung.data.util

import kotlin.math.*

/**
 * 두 지점 간의 거리를 계산하는 유틸리티 객체
 * Haversine 공식을 사용하여 지구 표면의 두 좌표 간 거리를 계산.
 */
object DistanceCalculator {
    
    /**
     * 두 좌표 간의 거리를 미터 단위로 계산
     * @param lat1 첫 번째 지점의 위도
     * @param lon1 첫 번째 지점의 경도
     * @param lat2 두 번째 지점의 위도
     * @param lon2 두 번째 지점의 경도
     * @return 거리 (미터)
     */
    fun calculateDistance(
        lat1: Double,
        lon1: Double,
        lat2: Double,
        lon2: Double
    ): Double {
        val earthRadius = 6371000.0 // 지구 반지름 (미터)
        
        val dLat = Math.toRadians(lat2 - lat1)
        val dLon = Math.toRadians(lon2 - lon1)
        
        val a = sin(dLat / 2).pow(2) +
                cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2)) *
                sin(dLon / 2).pow(2)
        
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        
        return earthRadius * c
    }
    
    /**
     * 거리를 읽기 쉬운 형식으로 변환 (예: "0.4km", "250m")
     */
    fun formatDistance(distanceInMeters: Double): String {
        return when {
            distanceInMeters >= 1000 -> {
                val km = distanceInMeters / 1000.0
                String.format("%.1fkm", km)
            }
            else -> {
                String.format("%.0fm", distanceInMeters)
            }
        }
    }
}

