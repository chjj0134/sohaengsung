package com.example.sohaengsung.data.util

import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.data.model.RouteResult
import com.example.sohaengsung.data.model.RouteSegment
import kotlin.math.*

object RouteCalculator {

    fun computeBestRoute(
        currentLat: Double,
        currentLng: Double,
        selectedPlaces: List<Place>
    ): RouteResult {

        if (selectedPlaces.isEmpty()) {
            return RouteResult()
        }

        val remaining = selectedPlaces.toMutableList()
        val ordered = mutableListOf<Place>()
        val segments = mutableListOf<RouteSegment>()

        var lastLat = currentLat
        var lastLng = currentLng
        var lastName = "현재 위치"

        var totalDistance = 0.0

        while (remaining.isNotEmpty()) {

            val nearest = remaining.minByOrNull { place ->
                distanceMeters(
                    lastLat, lastLng,
                    place.latitude, place.longitude
                )
            }!!

            val dist = distanceMeters(
                lastLat, lastLng,
                nearest.latitude, nearest.longitude
            )

            totalDistance += dist
            ordered += nearest

            segments += RouteSegment(
                fromName = lastName,
                toName = nearest.name,
                distanceMeters = dist
            )

            lastLat = nearest.latitude
            lastLng = nearest.longitude
            lastName = nearest.name

            remaining.remove(nearest)
        }

        return RouteResult(
            orderedPlaces = ordered,
            totalDistanceMeters = totalDistance,
            segments = segments
        )
    }

    private fun distanceMeters(
        lat1: Double, lng1: Double,
        lat2: Double, lng2: Double
    ): Double {
        val earth = 6371000.0
        val dLat = Math.toRadians(lat2 - lat1)
        val dLng = Math.toRadians(lng2 - lng1)

        val a = sin(dLat / 2).pow(2) +
                cos(Math.toRadians(lat1)) *
                cos(Math.toRadians(lat2)) *
                sin(dLng / 2).pow(2)

        return earth * 2 * atan2(sqrt(a), sqrt(1 - a))
    }
}
