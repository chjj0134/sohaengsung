package com.example.sohaengsung.data.repository

import com.example.sohaengsung.data.model.Coupon
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.toObjects
import kotlinx.coroutines.tasks.await

class CouponRepository {

    private val db = FirebaseFirestore.getInstance()

    fun listenUserCoupon(userId: String, onChange: (Int) -> Unit): ListenerRegistration {
        return db.collection("users")
            .document(userId)
            .collection("coupons")
            .addSnapshotListener { snapshot, _ ->
                if (snapshot != null) {
                    val coupon = snapshot.toObjects(Coupon::class.java)
                        .firstOrNull { !it.completed }

                    onChange(coupon?.stampCount ?: 0)
                }
            }
    }

    suspend fun getActiveCoupon(userId: String): Coupon? {
        val snapshot = db.collection("users")
            .document(userId)
            .collection("coupons")
            .whereEqualTo("completed", false)
            .limit(1)
            .get()
            .await()

        return snapshot.toObjects<Coupon>().firstOrNull()
    }

    suspend fun createCouponIfNotExists(userId: String): Coupon {
        val existing = getActiveCoupon(userId)
        if (existing != null) return existing

        val newCoupon = Coupon(
            couponId = db.collection("dummy").document().id,
            stampCount = 0,
            completed = false
        )

        db.collection("users")
            .document(userId)
            .collection("coupons")
            .document(newCoupon.couponId)
            .set(newCoupon)
            .await()

        return newCoupon
    }

    suspend fun addStamp(userId: String, count: Int) {
        val coupon = getActiveCoupon(userId) ?: createCouponIfNotExists(userId)

        val couponRef = db.collection("users")
            .document(userId)
            .collection("coupons")
            .document(coupon.couponId)

        couponRef.update("stampCount", FieldValue.increment(count.toLong())).await()

        val updated = couponRef.get().await().toObject(Coupon::class.java) ?: return

        if (updated.stampCount >= 10) {
            completeCoupon(userId, updated.couponId)

            createCouponIfNotExists(userId)
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
