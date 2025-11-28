package com.example.sohaengsung.data.repository

import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.data.network.RetrofitClient
import com.example.sohaengsung.data.remote.GooglePlaceItem
import com.example.sohaengsung.data.remote.toPlace
import com.example.sohaengsung.data.util.Constants
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PlaceRepository {

    private val db = FirebaseFirestore.getInstance()
    private val placeCollection = db.collection("places")

    private val targetTypes = listOf(
        "cafe",
        "restaurant",
        "book_store",
        "library",
        "park",
        "tourist_attraction",
        "point_of_interest",
        "art_gallery",
        "movie_theater",
        "museum",
        "subway_station",
        "bus_station",
        "train_station",
        "transit_station"
    )

    private suspend fun getGooglePlaces(
        location: String,
        type: String
    ): List<GooglePlaceItem> {
        return try {
            RetrofitClient.apiService.getNearbyPlaces(
                location = location,
                type = type,
                apiKey = Constants.GOOGLE_MAP_API_KEY
            ).results
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun getNearbyPlaces(
        lat: Double,
        lng: Double
    ): List<Place> {

        val location = "$lat,$lng"

        val googlePlaces = targetTypes
            .flatMap { type -> getGooglePlaces(location, type) }
            .distinctBy { it.placeId }
            .map { it.toPlace() }

        val userPlaces = loadUserCreatedPlaces()

        return googlePlaces + userPlaces
    }

    suspend fun getPlace(placeId: String): Place? {
        val doc = placeCollection.document(placeId).get().await()
        return doc.toObject(Place::class.java)
    }

    suspend fun getPlaces(placeIds: List<String>): List<Place> {
        if (placeIds.isEmpty()) return emptyList()
        return placeIds.mapNotNull { id -> getPlace(id) }
    }

    private suspend fun loadUserCreatedPlaces(): List<Place> {
        val snapshot = placeCollection.get().await()
        return snapshot.documents.mapNotNull { it.toObject(Place::class.java) }
    }

    suspend fun addUserPlace(place: Place) {
        placeCollection.document(place.placeId).set(place).await()
    }
}
