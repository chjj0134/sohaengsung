package com.example.sohaengsung.data.repository

import com.example.sohaengsung.data.network.RetrofitClient
import com.example.sohaengsung.data.remote.GooglePlaceItem
import com.example.sohaengsung.data.remote.GooglePlaceDetailResponse
import com.example.sohaengsung.data.model.GoogleReview
import com.example.sohaengsung.data.remote.GoogleReview as GoogleApiReview
import com.example.sohaengsung.data.remote.toPlace
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.example.sohaengsung.data.model.Place
import com.example.sohaengsung.data.model.Review
import com.example.sohaengsung.data.util.Constants

class PlaceRepository {

    private val db = FirebaseFirestore.getInstance()
    private val placeCollection = db.collection("places")

    private val categoryTypeMap = mapOf(
        "cafe" to listOf("cafe"),
        "bookstore" to listOf("book_store"),
        "select_shop" to listOf(
            "clothing_store", "department_store", "variety_store",
            "home_goods_store", "shopping_mall", "shoe_store",
            "jewelry_store", "electronics_store", "furniture_store",
            "hardware_store", "convenience_store"
        ),
        "gallery" to listOf("art_gallery", "museum")
    )

    private suspend fun getGooglePlaces(
        location: String,
        types: List<String>
    ): List<GooglePlaceItem> {

        return types.flatMap { type ->
            try {
                RetrofitClient.apiService.getNearbyPlaces(
                    location = location,
                    type = type,
                    apiKey = Constants.GOOGLE_MAP_API_KEY,
                    language = "ko"
                ).results
            } catch (e: Exception) {
                emptyList()
            }
        }
    }
    suspend fun getNearbyPlaces(lat: Double, lng: Double): List<Place> {
        val location = "$lat,$lng"
        val results = mutableListOf<Place>()

        categoryTypeMap.forEach { (categoryName, typeList) ->
            val googlePlaces = getGooglePlaces(location, typeList)
                .distinctBy { it.placeId }
                .map { it.toPlace(categoryName) }

            val enhancedPlaces = googlePlaces.map { place ->
                val detail = fetchGooglePlaceDetails(place.placeId)?.result

                val landscapePhoto = detail?.photos
                    ?.filter { photo ->
                        val w = photo.width ?: 0
                        val h = photo.height ?: 0
                        w > h
                    }
                    ?.maxByOrNull { it.width ?: 0 }
                    ?.photoReference

                val photoUrl = landscapePhoto?.let { ref ->
                    buildPhotoUrl(ref)
                }

                place.copy(
                    photoUrls = if (photoUrl != null) listOf(photoUrl) else emptyList()
                )
            }

            results.addAll(enhancedPlaces)
        }

        val userPlaces = loadUserCreatedPlaces()
        results.addAll(userPlaces)

        return results
    }

    private suspend fun fetchGooglePlaceDetails(placeId: String): GooglePlaceDetailResponse? {
        return try {
            RetrofitClient.apiService.getPlaceDetails(
                placeId = placeId,
                apiKey = Constants.GOOGLE_MAP_API_KEY,
                language = "ko"
            )
        } catch (e: Exception) {
            null
        }
    }

    suspend fun getPlaceReviews(placeId: String): List<GoogleReview> {

        val googleApiReviews = runCatching {
            fetchGooglePlaceDetails(placeId)
                ?.result
                ?.reviews
                ?: emptyList()
        }.getOrElse { emptyList() }

        val googleReviews = googleApiReviews.map { api ->
            GoogleReview(
                author = api.authorName ?: "",
                rating = api.rating ?: 0,
                time = api.relativeTimeDescription ?: "",
                content = api.text ?: "",
                profilePhotoUrl = api.profilePhotoUrl
            )
        }

        val koreanReviews = googleReviews.filter { rv ->
            rv.content.any { it in '가'..'힣' }
        }

        val userSnapshot = db.collection("reviews")
            .whereEqualTo("placeId", placeId)
            .get()
            .await()

        val userReviews = userSnapshot.documents.mapNotNull {
            it.toObject(Review::class.java)
        }

        val convertedUserReviews = userReviews.map { r ->
            GoogleReview(
                author = r.userId,
                rating = r.rating.toInt(),
                time = r.createdAt?.toDate().toString(),
                content = r.content
            )
        }

        return koreanReviews + convertedUserReviews
    }

    suspend fun getPlace(placeId: String): Place? {
        val doc = placeCollection.document(placeId).get().await()
        return doc.toObject(Place::class.java)
    }

    suspend fun getPlaces(placeIds: List<String>): List<Place> {
        if (placeIds.isEmpty()) return emptyList()
        return placeIds.mapNotNull { getPlace(it) }
    }

    private suspend fun loadUserCreatedPlaces(): List<Place> {
        val snapshot = placeCollection.get().await()
        return snapshot.documents.mapNotNull { it.toObject(Place::class.java) }
    }

    suspend fun addUserPlace(place: Place) {
        placeCollection.document(place.placeId).set(place).await()
    }

    fun buildPhotoUrl(photoRef: String): String {
        return "https://maps.googleapis.com/maps/api/place/photo" +
                "?maxwidth=400" +
                "&photo_reference=$photoRef" +
                "&key=${Constants.GOOGLE_MAP_API_KEY}"
    }
}
