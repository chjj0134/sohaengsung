package com.example.sohaengsung.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class BookmarkRepository {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val userRepository = UserRepository()

    private fun bookmarkCollection(uid: String) =
        db.collection("users")
            .document(uid)
            .collection("bookmarks")

    /**
     * 북마크 추가
     * - bookmarks 컬렉션에 placeId 저장
     * - 활동 점수 +1
     * - 북마크 횟수 +1
     * - 레벨 자동 갱신
     */
    suspend fun addBookmark(uid: String, placeId: String) {
        val data = mapOf("placeId" to placeId)
        bookmarkCollection(uid).document(placeId).set(data).await()

        // 🔥 점수 + 횟수 + 레벨 처리
        userRepository.addBookmarkActivity(uid)
    }

    /**
     * 북마크 제거
     * (제거 시 점수 / 횟수 감소는 하지 않음 – 정책상 일반적)
     */
    suspend fun removeBookmark(uid: String, placeId: String) {
        bookmarkCollection(uid).document(placeId).delete().await()
    }

    /**
     * 북마크 목록 1회 조회
     */
    suspend fun getBookmarksOnce(uid: String): List<String> {
        val snapshot = bookmarkCollection(uid).get().await()
        return snapshot.documents.mapNotNull { it.getString("placeId") }
    }

    /**
     * 북마크 실시간 관찰
     */
    fun observeBookmarks(uid: String): Flow<List<String>> = callbackFlow {

        val listener = bookmarkCollection(uid)
            .addSnapshotListener { snapshot, error ->

                if (error != null) return@addSnapshotListener

                if (snapshot != null) {
                    val list = snapshot.documents.mapNotNull { it.getString("placeId") }
                    trySend(list)
                }
            }

        awaitClose {
            listener.remove()
        }
    }

    /**
     * 북마크 토글
     * - 이미 있으면 제거
     * - 없으면 추가 + 활동 반영
     */
    suspend fun toggleBookmark(uid: String, placeId: String) {
        val current = getBookmarksOnce(uid)

        if (current.contains(placeId)) {
            removeBookmark(uid, placeId)
        } else {
            addBookmark(uid, placeId)
        }
    }
}
