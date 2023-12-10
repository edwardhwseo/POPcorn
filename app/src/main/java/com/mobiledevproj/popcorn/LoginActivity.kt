package com.mobiledevproj.popcorn

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.FirebaseApp
import com.mobiledevproj.popcorn.interfaces.LoginForm
import com.mobiledevproj.popcorn.sign_in.GoogleAuthUiClient
import com.mobiledevproj.popcorn.sign_in.SignInViewModel
import com.mobiledevproj.popcorn.ui.theme.POPcornTheme
import kotlinx.coroutines.launch

class LoginActivity : ComponentActivity() {

    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext),
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            POPcornTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                ){
                    val navController = rememberNavController()
                    var isLoggedIn by remember { mutableStateOf(false) }

                    NavHost(navController = navController, startDestination = "sign_in") {
                        composable("sign_in") {
                            val viewModel = viewModel<SignInViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()
                            val context = LocalContext.current

                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if (result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUiClient.signInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )

                            LaunchedEffect(key1 = state.isSignInSuccessful) {
                                if (state.isSignInSuccessful) {
                                    Toast.makeText(
                                        context,
                                        context.getString(R.string.sign_in_successful),
                                        Toast.LENGTH_LONG
                                    ).show()
                                    startActivity(Intent(context, MainActivity::class.java))
                                    finish()
                                }
                            }

                            LoginForm(state = state, onSignInClick = {
                                lifecycleScope.launch {
                                    val signIntentSender = googleAuthUiClient.signIn()
                                    launcher.launch(
                                        IntentSenderRequest.Builder(
                                            signIntentSender ?: return@launch
                                        ).build()
                                    )
                                }
                            })
                        }
                    }
                }
            }
        }
    }
}


