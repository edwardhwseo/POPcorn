package com.mobiledevproj.popcorn

import android.os.Bundle
import android.util.Log
import android.widget.Toast
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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.mobiledevproj.popcorn.ui.theme.POPcornTheme

class RegistrationActivity : ComponentActivity() {

    private lateinit var databaseReference: DatabaseReference

    // Reference to the Firebase Database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize Firebase Database reference
        databaseReference = FirebaseDatabase.getInstance().reference

        setContent {
            POPcornTheme {
                RegistrationForm {
                    navigateBack()
                }
            }
        }
    }

    private fun navigateBack() {
        finish() // Close the current activity (RegistrationActivity)
    }

    private fun registerUserToRealTimeDatabase(
        username: String,
        firstName: String,
        lastName: String,
    ) {
        // Create a unique key for the user
        val userId = databaseReference.child("users").push().key

        // Create a HashMap to hold user data
        val userData = HashMap<String, Any>()
        userData["username"] = username
        userData["firstName"] = firstName
        userData["lastName"] = lastName

        // Check if userId is not null and then push user data to Firebase
        userId?.let {
            databaseReference.child("users").child(it).setValue(userData)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        TODO("Handle successful registration")
                        // Registration successful
                        // You can add further actions or navigate to another activity here
                    } else {
                        TODO("Handle failed registration")
                        // Registration failed
                        // Handle the error
                    }
                }
        }
    }

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private fun registerUserToFirebase(
        username: String,
        email: String,
        password: String,
        firstName: String,
        lastName: String
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser

                    val userData = HashMap<String, Any>()
                    userData["username"] = username
                    userData["firstName"] = firstName
                    userData["lastName"] = lastName
                    userData["favorites"] = HashMap<String, Boolean>()

                    user?.uid?.let { userId ->
                        databaseReference.child("users").child(userId).setValue(userData)
                            .addOnCompleteListener { dbTask ->
                                if (dbTask.isSuccessful) {
                                    showToast("Registration successful!")
                                    navigateBack()
                                } else {
                                    showToast("Failed to save user data. ${dbTask.exception?.message}")
                                }
                            }
                    }
                } else {
                    showToast("Registration failed. Please try again. ${task.exception?.message}")
                }
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
        val confirmPasswordState = remember { mutableStateOf("") }

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

            OutlinedTextField(
                value = confirmPasswordState.value,
                onValueChange = { confirmPasswordState.value = it },
                label = { Text("Confirm Password") },
                visualTransformation = PasswordVisualTransformation()
            )

            Button(
                onClick = {
                    val email = emailState.value
                    val password = passwordState.value
                    val confirmPassword = confirmPasswordState.value
                    val username = usernameState.value
                    val firstName = firstNameState.value
                    val lastName = lastNameState.value

                    // Check if passwords match before registering
                    if (password == confirmPassword) {
                        // Call the function to register the user on Firebase
                        registerUserToFirebase(username, email, password, firstName, lastName)
                    } else {
                        // Passwords don't match, show a Toast message
                        showToast("Passwords do not match. Please try again.")
                    }
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
    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
