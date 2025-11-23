package com.example.sohaengsung.data.repository

import com.example.sohaengsung.data.model.User
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserRepository {

    private val db = FirebaseFirestore.getInstance()
    private val users = db.collection("users")

    // 로그인 후 Firestore에 사용자 문서 생성/업데이트
    suspend fun createUserIfNotExists(user: User) {
        val doc = users.document(user.uid).get().await()

        if (doc.exists()) return

        users.document(user.uid).set(user).await()
    }

    // Firestore에서 사용자 정보 가져오기
    suspend fun getUser(uid: String): User? {
        val snap = users.document(uid).get().await()
        return snap.toObject(User::class.java)
    }
}
