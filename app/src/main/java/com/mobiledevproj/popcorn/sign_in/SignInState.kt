package com.mobiledevproj.popcorn.sign_in

data class SignInState(
    val isSignInSuccessful: Boolean = false,
    val signInError: String? = null
)
