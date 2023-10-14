package com.mobiledevproj.popcorn

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.mobiledevproj.popcorn.interfaces.LoginForm
import com.mobiledevproj.popcorn.ui.theme.POPcornTheme

class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent{
            POPcornTheme {
                LoginForm()
            }
        }
    }
}