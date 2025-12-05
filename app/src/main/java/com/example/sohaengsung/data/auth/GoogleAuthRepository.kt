package com.example.sohaengsung.data.auth

import android.content.Intent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await

class GoogleAuthRepository {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    suspend fun handleGoogleLoginResult(data: Intent?): AuthResult? {
        val task = GoogleSignIn.getSignedInAccountFromIntent(data)

        val account: GoogleSignInAccount = try {
            task.getResult(Exception::class.java)
        } catch (e: Exception) {
            return null
        }

        return firebaseAuthWithGoogle(account)
    }

    private suspend fun firebaseAuthWithGoogle(account: GoogleSignInAccount): AuthResult {
        val credential = GoogleAuthProvider.getCredential(account.idToken, null)
        val result = auth.signInWithCredential(credential).await()

        val uid = result.user?.uid ?: ""
        val nickname = account.displayName
        val profilePic = account.photoUrl?.toString()

        return AuthResult(
            uid = uid,
            nickname = nickname,
            profilePic = profilePic,
            isNewUser = result.additionalUserInfo?.isNewUser ?: false
        )
    }
}
