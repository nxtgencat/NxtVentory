package cat.nxtventory

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import cat.nxtventory.meow.firebase.UserDataManager
import cat.nxtventory.meow.navigation.NavigateX
import cat.nxtventory.ui.theme.NxtVentoryTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        val isLoggedIn = UserDataManager.isLoggedIn(this)
        setContent {
            NxtVentoryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    if (isLoggedIn) {
                        Log.d("MyApp", "User is signed in")
                        NxtVentory(navControllerX = rememberNavController())
                    } else {
                        MyApp()
                    }
                }
            }
        }
    }
}

@Composable
fun MyApp() {
    val navControllerX = rememberNavController()
    NavigateX(navControllerX)
}