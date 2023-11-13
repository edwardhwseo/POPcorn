import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.mobiledevproj.popcorn.R

class RegistrationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        val btnRegister: Button = findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener {
            registerUser()
        }
    }

    private fun registerUser() {
        val userName: String = findViewById<EditText>(R.id.getUserName).text.toString()
        val firstName: String = findViewById<EditText>(R.id.getFirstName).text.toString()
        val lastName: String = findViewById<EditText>(R.id.getLastName).text.toString()
        val email: String = findViewById<EditText>(R.id.getEmail).text.toString()
        val password: String = findViewById<EditText>(R.id.getPassword).text.toString()

        // Perform what to do with these values
    }

}
