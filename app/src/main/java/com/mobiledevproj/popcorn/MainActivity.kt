package com.mobiledevproj.popcorn

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.mobiledevproj.popcorn.ui.theme.POPcornTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            POPcornTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    //Greeting(Message("Edward Seo", "Welcome to POPcorn!"))
                    Conversation(SampleData.conversationSample)
                }
            }
        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun Greeting(msg: Message) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(R.drawable.popcorn),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = msg.author,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(shape = MaterialTheme.shapes.medium, shadowElevation = 1.dp) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun GreetingPreview() {
//    POPcornTheme {
//        Surface{
//            Greeting(msg = Message("Edward Seo Seo", "Welcome to POPcorn!"))
//        }
//    }
//}

@Composable
fun Conversation(messages: List<Message>) {
    LazyColumn {
        items(messages) { message ->
            Greeting(message)
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    POPcornTheme {
        Conversation(SampleData.conversationSample)
    }
}

/**
 * SampleData for Co-Opt 1: Jetpack Compose Basics
 */
object SampleData {
    // Sample conversation data
    val conversationSample = listOf(
        Message(
            "Edward Seo",
            "Welcome to POPcorn!"
        ),
        Message(
            "Edward Seo",
            """List of Recently Watched Movies:
            Oppenheimer
            Insomnia
            Barbie
            Talk To Me""".trim()
        ),
        Message(
            "Julian Macaraeg",
            "What movie's do you like?"
        ),
        Message(
            "Brady Wallace",
            "I like Sci-Fi Movies!"
        ),
        Message(
            "Edward Seo",
            "Check out my list of favourites!"
        ),
    )
}