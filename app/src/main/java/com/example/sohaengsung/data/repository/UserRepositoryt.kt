package com.example.sohaengsung.data.repository

import com.example.sohaengsung.data.model.User
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository {

    private val db = FirebaseFirestore.getInstance()
    private val users = db.collection("users")

    suspend fun createUserIfNotExists(user: User) {
        val doc = users.document(user.uid).get().await()

        if (doc.exists()) return

        users.document(user.uid).set(
            user.copy(
                level = 1,
                activityScore = 0
            )
        ).await()
    }

    suspend fun getUser(uid: String): User? {
        val snap = users.document(uid).get().await()
        return snap.toObject(User::class.java)
    }

    suspend fun addActivityScore(uid: String, scoreToAdd: Int) {
        val userRef = users.document(uid)

        userRef.update("activityScore", FieldValue.increment(scoreToAdd.toLong())).await()

        updateLevelIfNeeded(uid)
    }

    fun calculateLevel(activityScore: Int): Int {
        return when {
            activityScore >= 150 -> 5
            activityScore >= 100 -> 4
            activityScore >= 50  -> 3
            activityScore >= 20  -> 2
            else                 -> 1
        }
    }

    suspend fun updateLevelIfNeeded(uid: String) {
        val user = getUser(uid) ?: return

        val newLevel = calculateLevel(user.activityScore)

        if (newLevel != user.level) {
            users.document(uid).update("level", newLevel).await()
        }
    }

    suspend fun updateUserLevelAndScore(uid: String, addedScore: Int) {
        val userRef = users.document(uid)

        userRef.update("activityScore", FieldValue.increment(addedScore.toLong())).await()

        updateLevelIfNeeded(uid)
    }
}
