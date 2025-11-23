package com.example.sohaengsung.data.repository

import com.example.sohaengsung.data.model.Place
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class PlaceRepository {

    private val db = FirebaseFirestore.getInstance()
    private val placeCollection = db.collection("places")

    // placeId 로 Place 객체 가져오기
    suspend fun getPlace(placeId: String): Place? {
        val doc = placeCollection.document(placeId).get().await()
        return doc.toObject(Place::class.java)
    }

    // placeId 리스트 → Place 리스트
    suspend fun getPlaces(placeIds: List<String>): List<Place> {
        if (placeIds.isEmpty()) return emptyList()

        return placeIds.mapNotNull { id -> getPlace(id) }
    }
}
