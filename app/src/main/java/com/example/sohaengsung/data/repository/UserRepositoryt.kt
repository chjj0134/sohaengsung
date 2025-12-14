package com.example.sohaengsung.data.repository

import com.example.sohaengsung.data.model.User
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import kotlinx.coroutines.tasks.await

class UserRepository {

    private val db = FirebaseFirestore.getInstance()
    private val users = db.collection("users")

    suspend fun createUserIfNotExists(user: User) {
        val docSnapshot = users.document(user.uid).get().await()

        if (!docSnapshot.exists()) {
            val newUser = user.copy(
                level = 1,
                activityScore = 0
            )
            users.document(user.uid).set(newUser).await()
        } else {
            val existingUser = docSnapshot.toObject(User::class.java)
            val updates = mutableMapOf<String, Any?>()

            if (user.nickname != null && user.nickname != existingUser?.nickname) {
                updates["nickname"] = user.nickname
            }

            if (user.profilePic != null && user.profilePic != existingUser?.profilePic) {
                updates["profilePic"] = user.profilePic
            }

            if (updates.isNotEmpty()) {
                users.document(user.uid).update(updates).await()
            }
        }
    }

    suspend fun getUser(uid: String): User? {
        return users.document(uid).get().await().toObject(User::class.java)
    }

    suspend fun addActivityScore(uid: String, scoreToAdd: Int) {
        val userRef = users.document(uid)

        userRef.update("activityScore", FieldValue.increment(scoreToAdd.toLong())).await()

        updateLevelIfNeeded(uid)
    }

    fun calculateLevel(activityScore: Int): Int {
        return when {
            activityScore >= 60 -> 5
            activityScore >= 30 -> 4
            activityScore >= 15 -> 3
            activityScore >= 3  -> 2
            else                -> 1
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

    fun listenUser(uid: String, onChange: (User) -> Unit): ListenerRegistration {
        return users.document(uid)
            .addSnapshotListener { snapshot, _ ->
                val user = snapshot?.toObject(User::class.java)
                if (user != null) onChange(user)
            }
    }
}
