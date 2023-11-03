package com.mobiledevproj.popcorn

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mobiledevproj.popcorn.ui.theme.POPcornTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.res.stringResource
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.ui.layout.ContentScale
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            AppNavigator(navController)
        }
    }
}

@Composable
fun MoviePage(navController: NavHostController) {
    Column {
        Button(onClick = { navController.popBackStack() }) {
            Text("Back to Home")
        }

        Text("All Movies")

        POPcornBottomNavigation(navController)
    }
}

@Composable
fun SocialPage(navController: NavHostController) {
    Column{
        Button(onClick = { navController.popBackStack() }) {
            Text("Back to Home")
        }

        Text("Social Page")

        POPcornBottomNavigation(navController)
    }
}

@Composable
fun ProfilePage(navController: NavHostController) {
    Column {
        Button(onClick = { navController.popBackStack() }) {
            Text("Back to Home")
        }

        Text("Profile Page")

        POPcornBottomNavigation(navController)
    }
}

@Composable
fun AppNavigator(navController: NavHostController) {
    NavHost(navController, startDestination = "popcornPortrait") {
        composable("popcornPortrait") {
            POPcornPortrait(navController)
        }
        composable("moviePage") {
            MoviePage(navController)
        }
        composable("socialPage"){
            SocialPage(navController)
        }
        composable("profilePage") {
            ProfilePage(navController)
        }
    }
}

@Preview(showBackground = true, device = "id:Nexus One", showSystemUi = true)
@Composable
fun GreetingPreview() {
    POPcornTheme {
        Greeting("Android")
    }
}

@Preview(showBackground = true, device = "id:Nexus One", showSystemUi = true)
@Composable
fun GreetingPreviewDark() {
    POPcornTheme(darkTheme = true) {
        Greeting("Android")
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier){
    Surface {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Image(
                painter = painterResource(id = R.mipmap.popcorn_logo_foreground), // Use your image name here
                contentDescription = null, // Provide a content description if needed
                modifier = Modifier.size(128.dp) // Adjust the size as needed
            )
            Text(
                text = "Hello $name, welcome to POPCorn!",
                modifier = modifier
            )
        }
    }
}

// SearchBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    modifier: Modifier = Modifier
) {
    TextField(
        value = "",
        onValueChange = {},
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = null
            )
        },
        colors = TextFieldDefaults.textFieldColors(
            cursorColor = Color.Black,
            placeholderColor = Color.Gray
        ),
        placeholder = {
            Text(stringResource(R.string.placeholder_search))
        },
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 56.dp)
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun SearchBarPreview() {
    POPcornTheme { SearchBar(Modifier.padding(8.dp)) }
}

// POPcorn Element

@Composable
fun POPcornElement(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    destination: String,
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.clickable {
            navController.navigate(destination)
        },
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(drawable),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(88.dp)
                .clip(CircleShape)
        )
        Text(
            text = stringResource(text),
            modifier = Modifier.paddingFromBaseline(top = 24.dp, bottom = 8.dp),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun POPcornElementPreview() {
    val navController = rememberNavController()
    POPcornTheme {
        POPcornElement(
            text = R.string.movies,
            drawable = R.drawable.movie,
            navController = navController,
            destination = "moviePage",
            modifier = Modifier.padding(8.dp)
        )
    }
}

// Dashboard Row

@Composable
fun DashboardRow(navController: NavHostController, modifier: Modifier = Modifier) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        items(dashboardData) { item ->
            val destination = if (item.text == R.string.movies) "moviePage" else "socialPage"
            POPcornElement(item.drawable, item.text, destination, navController)
        }
    }
}

// HomeSection

@Composable
fun HomeSection(
    @StringRes title: Int,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier) {
        Text(
            text = stringResource(title),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .paddingFromBaseline(top = 40.dp, bottom = 16.dp)
                .padding(horizontal = 16.dp)
        )
        content()
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun HomeSectionPreview() {
    val navController = rememberNavController()
    POPcornTheme {
        HomeSection(R.string.Dashboard) {
            DashboardRow(navController = navController)
        }
    }
}

// Favourites Collection

@Composable
fun FavouriteCollectionCard(
    @DrawableRes drawable: Int,
    @StringRes text: Int,
    modifier: Modifier = Modifier
) {
    Surface(
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.width(255.dp)
        ) {
            Image(
                painter = painterResource(drawable),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(80.dp)
            )
            Text(
                text = stringResource(text),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun FavouriteCollectionCardPreview() {
    POPcornTheme {
        FavouriteCollectionCard(
            text = R.string.fc1_blade_runner,
            drawable = R.drawable.blade_runner,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun FavouriteCollectionsGrid(
    modifier: Modifier = Modifier
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier.height(168.dp)
    ) {
        items(favouriteCollectionsData) { item ->
            FavouriteCollectionCard(item.drawable, item.text, Modifier.height(80.dp))
        }
    }
}

// HomeScreen

@Composable
fun HomeScreen(navController: NavHostController, modifier: Modifier = Modifier) {
    Column(
        modifier
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(Modifier.height(16.dp))
        SearchBar(Modifier.padding(horizontal = 16.dp))
        HomeSection(title = R.string.Dashboard) {
            DashboardRow(navController)
        }
        HomeSection(title = R.string.favourite_collection) {
            FavouriteCollectionsGrid()
        }

        Spacer(Modifier.height(16.dp))
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE, heightDp = 180)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    POPcornTheme { HomeScreen(navController) }
}

// Bottom Navigation
@Composable
private fun POPcornBottomNavigation(navController: NavHostController, modifier: Modifier = Modifier) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
        modifier = modifier
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.bottom_navigation_home))
            },
            selected = true,
            onClick = {
                navController.navigate("popcornPortrait")
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
            },
            label = {
                Text(stringResource(R.string.bottom_navigation_profile))
            },
            selected = false,
            onClick = {
                navController.navigate("profilePage")
            }
        )
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFF5F0EE)
@Composable
fun POPcornBottomNavigationPreview() {
    val navController = rememberNavController()
    POPcornTheme { POPcornBottomNavigation(navController, Modifier.padding(top = 24.dp)) }
}

// POPcorn Portrait

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun POPcornPortrait(navController: NavHostController) {
    POPcornTheme {
        Scaffold(
            bottomBar = { POPcornBottomNavigation(navController) }
        ) { padding ->
            HomeScreen(navController)
        }
    }
}

@Preview(widthDp = 360, heightDp = 640)
@Composable
fun POPcornPortraitPreview() {
    val navController = rememberNavController()
    POPcornPortrait(navController)
}

// Dashboard Items
data class DashboardData(@DrawableRes val drawable: Int, @StringRes val text: Int)

val dashboardData = listOf(
    DashboardData(R.drawable.movie, R.string.movies),
    DashboardData(R.drawable.social, R.string.social)
)

// Favourites Collection Data

private val favouriteCollectionsData = listOf(
    R.drawable.blade_runner to R.string.fc1_blade_runner,
    R.drawable.meg to R.string.fc2_meg,
    R.drawable.avengers to R.string.fc3_avengers,
    R.drawable.dark_knight to R.string.fc4_dark_knight
).map { DrawableStringPair(it.first, it.second) }

private data class DrawableStringPair(
    @DrawableRes val drawable: Int,
    @StringRes val text: Int
)