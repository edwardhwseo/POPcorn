package com.mobiledevproj.popcorn.sign_in

// Represents the result of a sign-in attempt.
data class SignInResult(
    val data: UserData?,
    val errorMessage: String?
)

// Represents the user data returned by the sign-in attempt.
data class UserData(
    val userId: String,
    val username: String?,
)
