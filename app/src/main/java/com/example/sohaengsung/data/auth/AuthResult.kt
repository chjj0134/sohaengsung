package com.example.sohaengsung.data.auth

data class AuthResult(
    val uid: String,
    val nickname: String?,
    val profilePic: String?,
    val isNewUser: Boolean = false
)
