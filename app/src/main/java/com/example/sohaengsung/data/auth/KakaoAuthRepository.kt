package com.example.sohaengsung.data.auth

import android.app.Activity
import com.google.firebase.firestore.FirebaseFirestore
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class KakaoAuthRepository {

    private val firestore = FirebaseFirestore.getInstance()

    suspend fun loginWithKakao(activity: Activity): AuthResult? =
        suspendCancellableCoroutine { cont ->

            val loginCallback: (Throwable?) -> Unit = loginCallback@{ error ->
                if (error != null) {
                    cont.resume(null)
                    return@loginCallback
                }

                UserApiClient.instance.me { user, meError ->
                    if (meError != null || user == null) {
                        cont.resume(null)
                        return@me
                    }

                    val uid = "kakao_${user.id}"
                    val nickname = user.kakaoAccount?.profile?.nickname
                    val profilePic = user.kakaoAccount?.profile?.profileImageUrl

                    val userRef = firestore.collection("users").document(uid)

                    userRef.get()
                        .addOnSuccessListener { snapshot ->
                            val isNewUser = !snapshot.exists()

                            if (isNewUser) {
                                userRef.set(
                                    mapOf(
                                        "uid" to uid,
                                        "nickname" to nickname,
                                        "profilePic" to profilePic,
                                        "level" to 1,
                                        "activityScore" to 0
                                    )
                                )
                            }

                            cont.resume(
                                AuthResult(
                                    uid = uid,
                                    nickname = nickname,
                                    profilePic = profilePic,
                                    isNewUser = isNewUser
                                )
                            )
                        }
                        .addOnFailureListener {
                            cont.resume(null)
                        }
                }
            }

            if (UserApiClient.instance.isKakaoTalkLoginAvailable(activity)) {
                UserApiClient.instance.loginWithKakaoTalk(activity) { _, error ->
                    loginCallback(error)
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(activity) { _, error ->
                    loginCallback(error)
                }
            }
        }
}
