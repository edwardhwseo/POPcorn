package com.mobiledevproj.popcorn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.mobiledevproj.popcorn.ui.theme.POPcornTheme

class RegistrationActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            POPcornTheme {
                RegistrationForm{
                    navigateBack()
                }
            }
        }
    }
    private fun navigateBack() {
        finish() // Close the current activity (RegistrationActivity)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistrationForm(onBackPressed: () -> Unit) {
    val usernameState = remember { mutableStateOf("") }
    val firstNameState = remember { mutableStateOf("") }
    val lastNameState = remember { mutableStateOf("") }
    val emailState = remember { mutableStateOf("") }
    val passwordState = remember { mutableStateOf("") }

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        OutlinedTextField(
            value = usernameState.value,
            onValueChange = { usernameState.value = it },
            label = { Text("Username") }
        )

        OutlinedTextField(
            value = firstNameState.value,
            onValueChange = { firstNameState.value = it },
            label = { Text("First Name") }
        )

        OutlinedTextField(
            value = lastNameState.value,
            onValueChange = { lastNameState.value = it },
            label = { Text("Last Name") }
        )

        OutlinedTextField(
            value = emailState.value,
            onValueChange = { emailState.value = it },
            label = { Text("Email") }
        )

        OutlinedTextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation()
        )

        Button(
            onClick = {
                // Perform registration logic here using the entered data
            },
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Register")
        }

        Button(
            onClick = onBackPressed,
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text("Back to Login")
        }
    }
}
