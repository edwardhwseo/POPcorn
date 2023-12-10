package com.mobiledevproj.popcorn.sign_in

// Represents the result of a sign-in attempt.
data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)
