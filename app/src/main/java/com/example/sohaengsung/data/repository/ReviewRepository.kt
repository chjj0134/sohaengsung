package com.example.sohaengsung.data.repository

import com.example.sohaengsung.data.model.Review
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

class ReviewRepository {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    fun addReview(
        review: Review,
        onComplete: (Boolean) -> Unit
    ) {
        val reviewRef = db.collection("users")
            .document(review.userId)
            .collection("reviews")
            .document()

        val newReview = review.copy(
            reviewId = reviewRef.id,
            createdAt = Timestamp.now()
        )

        db.runBatch { batch ->
            batch.set(reviewRef, newReview)

            val userRef = db.collection("users").document(review.userId)
            batch.update(
                userRef,
                "activityScore",
                FieldValue.increment(15)
            )

            val couponRef = userRef
                .collection("coupons")
                .document(review.placeId)

            batch.update(
                couponRef,
                "stampCount",
                FieldValue.increment(1)
            )
        }
            .addOnSuccessListener { onComplete(true) }
            .addOnFailureListener { onComplete(false) }
    }
}
