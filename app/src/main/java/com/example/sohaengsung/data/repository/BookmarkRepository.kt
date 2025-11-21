package com.example.sohaengsung.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class BookmarkRepository {

    private val db = FirebaseFirestore.getInstance()

    private fun bookmarkCollection(uid: String) =
        db.collection("users").document(uid).collection("bookmarks")

    // 북마크 추가
    suspend fun addBookmark(uid: String, placeId: String) {
        val data = mapOf("placeId" to placeId)
        bookmarkCollection(uid).document(placeId).set(data).await()
    }

    // 북마크 삭제
    suspend fun removeBookmark(uid: String, placeId: String) {
        bookmarkCollection(uid).document(placeId).delete().await()
    }

    // 북마크 단발성 조회 (1회 요청)
    suspend fun getBookmarksOnce(uid: String): List<String> {
        val snapshot = bookmarkCollection(uid).get().await()
        return snapshot.documents.mapNotNull { it.getString("placeId") }
    }

    // 실시간 북마크 감지 (Flow)
    fun observeBookmarks(uid: String) = callbackFlow<List<String>> {

        val listener = bookmarkCollection(uid)
            .addSnapshotListener { snapshot, _ ->

                if (snapshot != null) {
                    val list = snapshot.documents.mapNotNull { it.getString("placeId") }
                    trySend(list)  // Flow로 내보내기
                }
            }

        // 리스너 제거
        awaitClose { listener.remove() }
    }
}
