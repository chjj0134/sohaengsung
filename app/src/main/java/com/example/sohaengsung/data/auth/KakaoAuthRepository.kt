package com.example.sohaengsung.data.auth

import android.content.Context
import com.kakao.sdk.user.UserApiClient
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

class KakaoAuthRepository {

    suspend fun loginWithKakao(context: Context): AuthResult? {
        return suspendCancellableCoroutine { continuation ->

            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null || token == null) {
                    continuation.resume(null)
                    return@loginWithKakaoTalk
                }

                UserApiClient.instance.me { user, meError ->
                    if (meError != null || user == null) {
                        continuation.resume(null)
                        return@me
                    }

                    val uid = user.id.toString()
                    val nickname = user.kakaoAccount?.profile?.nickname
                    val profilePic = user.kakaoAccount?.profile?.profileImageUrl

                    continuation.resume(
                        AuthResult(
                            uid = uid,
                            nickname = nickname,
                            profilePic = profilePic,
                            isNewUser = true
                        )
                    )
                }
            }
        }
    }
}
