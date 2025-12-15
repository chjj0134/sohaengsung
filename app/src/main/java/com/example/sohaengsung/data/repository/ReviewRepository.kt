package com.example.sohaengsung.data.repository

import com.example.sohaengsung.data.model.GoogleReview
import com.example.sohaengsung.data.model.Review
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ReviewRepository {

    private val db = FirebaseFirestore.getInstance()

    suspend fun addReview(review: Review) {
        val userReviewRef = db
            .collection("users")
            .document(review.userId)
            .collection("reviews")
            .document()

        userReviewRef.set(review).await()

        val placeRef = db.collection("places").document(review.placeId)
        val placeSnapshot = placeRef.get().await()

        val currentRating = placeSnapshot.getDouble("rating") ?: 0.0
        val currentCount = placeSnapshot.getLong("reviewCount")?.toInt() ?: 0

        val newCount = currentCount + 1
        val newRating =
            ((currentRating * currentCount) + review.rating) / newCount

        val googleReview = GoogleReview(
            author = review.userId,
            rating = review.rating.toInt(),
            time = review.createdAt?.toDate()?.toString() ?: "",
            content = review.content,
            profilePhotoUrl = null
        )

        placeRef.update(
            mapOf(
                "details.reviews" to FieldValue.arrayUnion(googleReview),
                "rating" to newRating,
                "reviewCount" to newCount
            )
        ).await()
    }

    suspend fun getReviewsByPlace(placeId: String): List<Review> {
        val snapshot = db.collectionGroup("reviews")
            .whereEqualTo("placeId", placeId)
            .get()
            .await()

        return snapshot.documents.mapNotNull {
            it.toObject(Review::class.java)
        }
    }
}
