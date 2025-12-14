package com.example.sohaengsung.data.repository

import com.example.sohaengsung.data.model.Review
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ReviewRepository {

    private val db = FirebaseFirestore.getInstance()
    private val couponRepo = CouponRepository()
    private val userRepo = UserRepository()

    private val repoScope = CoroutineScope(Dispatchers.IO)

    fun addReview(
        review: Review,
        onComplete: (Boolean) -> Unit
    ) {
        repoScope.launch {
            try {
                val reviewRef = db.collection("users")
                    .document(review.userId)
                    .collection("reviews")
                    .document()

                val newReview = review.copy(
                    reviewId = reviewRef.id,
                    createdAt = Timestamp.now(),
                    source = "USER"
                )

                reviewRef.set(newReview).await()

                val placeReviewRef = db.collection("places")
                    .document(review.placeId)
                    .collection("reviews")
                    .document(newReview.reviewId)

                placeReviewRef.set(newReview).await()

                userRepo.addActivityScore(review.userId, 15)
                couponRepo.addStamp(review.userId, 3)

                onComplete(true)
            } catch (e: Exception) {
                onComplete(false)
            }
        }
    }

    suspend fun getReviewsByPlace(placeId: String): List<Review> {
        val snapshot = db.collection("places")
            .document(placeId)
            .collection("reviews")
            .get()
            .await()

        return snapshot.documents.mapNotNull {
            it.toObject(Review::class.java)
        }
    }
}
