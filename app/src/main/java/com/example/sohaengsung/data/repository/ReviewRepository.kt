package com.example.sohaengsung.data.repository

import com.example.sohaengsung.data.model.Review
import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReviewRepository {

    private val db = FirebaseFirestore.getInstance()
    private val couponRepo = CouponRepository()
    private val userRepo = UserRepository()

    private val repoScope = CoroutineScope(Dispatchers.IO)

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

        reviewRef.set(newReview)
            .addOnSuccessListener {

                repoScope.launch {

                    try {
                        // 활동 점수 +15
                        userRepo.addActivityScore(review.userId, 15)

                        // 스탬프 3개 증가
                        couponRepo.addStamp(review.userId, 3)

                        onComplete(true)

                    } catch (e: Exception) {
                        e.printStackTrace()
                        onComplete(false)
                    }
                }
            }
            .addOnFailureListener {
                onComplete(false)
            }
    }
}
