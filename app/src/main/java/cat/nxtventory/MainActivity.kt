package cat.nxtventory

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator
import cat.nxtventory.meow.firebase.UserDataManager
import cat.nxtventory.meow.nxtventory.data.Country
import cat.nxtventory.meow.nxtventory.data.NxtVentoryUser
import cat.nxtventory.meow.nxtventory.data.supabase
import cat.nxtventory.meow.nxtventory.ui.NxtVentory
import cat.nxtventory.meow.welcome.WelcomeScreen
import cat.nxtventory.ui.theme.NxtVentoryTheme
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val isLoggedIn = UserDataManager.isLoggedIn(this)
        setContent {
            NxtVentoryTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
//                    MyApp(isLoggedIn
                    Column {
                        repeat(10) {

                            Text(text = "Hello")
                        }
                    }
                    MyApp(isLoggedIn)
                }
            }
        }
    }
}

@Composable
fun MyApp(isLoggedIn: Boolean) {
    if (isLoggedIn) {
        Log.d("MyApp", "User is signed in")
        Navigator(NxtVentory())
    } else {
        Navigator(WelcomeScreen())
//        { SlideTransition(navigator = it) }
    }
}

@Composable
fun CountriesList() {
    var countries by remember { mutableStateOf<List<Country>>(listOf()) }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            countries = supabase.from("countries")
                .select()
                .decodeList<Country>()
        }
    }
    LazyColumn {
        items(
            countries,
            key = { country ->
                country.id
            },
        ) { country ->
            Text(
                country.name,
                modifier = Modifier.padding(8.dp),
            )
        }
    }
}

@Composable
fun UsersList() {
    var users by remember { mutableStateOf<List<NxtVentoryUser>>(listOf()) }
    LaunchedEffect(Unit) {
        withContext(Dispatchers.IO) {
            users = supabase.from("users")
                .select()
                .decodeList<NxtVentoryUser>()
        }
    }
    LazyColumn {
        items(
            users,
            key = { user ->
                user.id
            },
        ) { user ->
            Text(
                user.name,
                modifier = Modifier.padding(8.dp),
            )
        }
    }
}
