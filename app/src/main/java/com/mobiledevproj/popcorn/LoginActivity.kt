package com.mobiledevproj.popcorn

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.mobiledevproj.popcorn.interfaces.LoginForm
import com.mobiledevproj.popcorn.ui.theme.POPcornTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()

            POPcornTheme {
                val context = LocalContext.current
                var isLoggedIn by remember { mutableStateOf(false) }

                if (isLoggedIn) {
                    // If user is logged in, navigate to MainActivity
                    startActivity(Intent(context, MainActivity::class.java))
                    finish()
                } else {
                    val navigateToRegistration = {
                        startActivity(Intent(context, RegistrationActivity::class.java))
                    }

                    LoginForm(onRegisterClicked = navigateToRegistration)
                }
            }
        }
    }
}
