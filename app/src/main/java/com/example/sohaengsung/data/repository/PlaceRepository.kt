package com.example.sohaengsung.data.repository

import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.data.model.Review
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PlaceRepository {

    private val db = FirebaseFirestore.getInstance()
    private val placeCollection = db.collection("places")

    suspend fun getPlace(placeId: String): Place? {
        val doc = placeCollection.document(placeId).get().await()
        return doc.toObject(Place::class.java)
    }

    suspend fun getPlaces(placeIds: List<String>): List<Place> {
        if (placeIds.isEmpty()) return emptyList()
        return placeIds.mapNotNull { getPlace(it) }
    }

    suspend fun getAllPlaces(): List<Place> {
        val snapshot = placeCollection.get().await()
        return snapshot.documents.mapNotNull { it.toObject(Place::class.java) }
    }

    suspend fun addUserPlace(place: Place) {
        placeCollection.document(place.placeId).set(place).await()
    }

    suspend fun updateRating(placeId: String, reviews: List<Review>) {
        if (reviews.isEmpty()) return

        val avgRating = reviews.map { it.rating }.average()
        val count = reviews.size

        placeCollection.document(placeId).update(
            mapOf(
                "rating" to avgRating,
                "reviewCount" to count
            )
        ).await()
    }
}
