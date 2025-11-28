package com.example.sohaengsung.data.repository

import com.example.sohaengsung.data.model.Coupon
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await

class CouponRepository {

    private val db = FirebaseFirestore.getInstance()

    suspend fun getUserCoupons(userId: String): List<Coupon> {
        return try {
            val result = db.collection("users")
                .document(userId)
                .collection("coupons")
                .get()
                .await()

            result.toObjects(Coupon::class.java)
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun completeCoupon(userId: String, couponId: String) {
        db.collection("users")
            .document(userId)
            .collection("coupons")
            .document(couponId)
            .update("completed", true)
            .await()
    }
}
