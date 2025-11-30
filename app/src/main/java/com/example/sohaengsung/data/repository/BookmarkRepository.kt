package com.example.sohaengsung.data.repository

import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await

class BookmarkRepository {

    private val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    private fun bookmarkCollection(uid: String) =
        db.collection("users")
            .document(uid)
            .collection("bookmarks")

    suspend fun addBookmark(uid: String, placeId: String) {
        val data = mapOf("placeId" to placeId)
        bookmarkCollection(uid).document(placeId).set(data).await()
    }

    suspend fun removeBookmark(uid: String, placeId: String) {
        bookmarkCollection(uid).document(placeId).delete().await()
    }

    suspend fun getBookmarksOnce(uid: String): List<String> {
        val snapshot = bookmarkCollection(uid).get().await()
        return snapshot.documents.mapNotNull { it.getString("placeId") }
    }

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

    suspend fun toggleBookmark(uid: String, placeId: String) {
        val current = getBookmarksOnce(uid)

        if (current.contains(placeId)) {
            removeBookmark(uid, placeId)
        } else {
            addBookmark(uid, placeId)
        }
    }


}
