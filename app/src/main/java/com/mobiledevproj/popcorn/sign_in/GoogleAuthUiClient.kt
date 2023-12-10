package com.mobiledevproj.popcorn.sign_in

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import androidx.navigation.NavController
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.BeginSignInRequest.GoogleIdTokenRequestOptions
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.mobiledevproj.popcorn.R
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

// Manages Google Sign-in functionality using Google Identity API and Firebase Authentication.
class GoogleAuthUiClient(
    private val context: Context,
    private val oneTapClient: SignInClient
)
{
    private val auth = FirebaseAuth.getInstance()

    // Initiates the Google Sign-In process.
    suspend fun signIn(): IntentSender? {
        val result = try {
            oneTapClient.beginSignIn(
            buildSignInRequest()
            ).await()
        }
        catch(e: Exception){
            e.printStackTrace()
            if(e is CancellationException) throw e
            null
        }
        return result?.pendingIntent?.intentSender
    }

    // Handles the sign-in process with the received intent.
    suspend fun signInWithIntent(intent: Intent): SignInResult{
        val credential = oneTapClient.getSignInCredentialFromIntent(intent)
        val googleIdToken = credential.googleIdToken
        val googleCredentials = GoogleAuthProvider.getCredential(googleIdToken, null)
        return try
        {
            val user = auth.signInWithCredential(googleCredentials).await().user
            SignInResult(
                data = user?.run {
                    UserData(
                        userId = uid,
                        username = displayName
                    )
                },
                errorMessage = null
            )
        }
        catch (e:Exception)
        {
            e.printStackTrace()
            if(e is CancellationException) throw e
            SignInResult(null, e.message)
        }
    }

    // Signs out the user from the app and revokes the Google Sign-In token.
    suspend fun signOut(){
        try {
            oneTapClient.signOut().await()
            auth.signOut()
        }
        catch(e: Exception) {
            e.printStackTrace()
            if(e is CancellationException) throw e
        }
    }

    // Returns the currently signed-in user.
    fun getSignedInUser(): UserData? = auth.currentUser?.run{
        UserData(
            userId = uid,
            username = displayName
        )
    }

// Builds a request for initiating the Google Sign-In process.
    private fun buildSignInRequest(): BeginSignInRequest {
        return BeginSignInRequest.Builder()
            .setGoogleIdTokenRequestOptions(
            GoogleIdTokenRequestOptions.builder()
                .setSupported(true)
                .setFilterByAuthorizedAccounts(false)
                .setServerClientId(context.getString(R.string.web_client_id))
                .build()
        )
                .setAutoSelectEnabled(true)
                .build()
    }
}