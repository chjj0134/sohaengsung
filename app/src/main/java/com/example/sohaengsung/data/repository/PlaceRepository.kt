package com.example.sohaengsung.data.repository

import com.example.sohaengsung.data.model.GoogleReview
import com.example.sohaengsung.data.model.Hashtag
import com.example.sohaengsung.data.model.Place
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

    suspend fun getPlaceReviews(placeId: String): List<GoogleReview> {
        val place = getPlace(placeId)
        return place?.details?.reviews ?: emptyList()
    }

    suspend fun getPlaceHashtags(placeId: String): List<Hashtag> {
        val doc = placeCollection.document(placeId).get().await()

        val rawTags = doc.get("hashtags") as? List<String> ?: emptyList()

        return rawTags.map { tag ->
            Hashtag(name = tag)
        }
    }
}
