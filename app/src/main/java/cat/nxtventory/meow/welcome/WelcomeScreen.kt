package cat.nxtventory.meow.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.nxtventory.R
import cat.nxtventory.meow.navigation.data.Screen
import cat.nxtventory.ui.theme.myTypography

@Composable
fun WelcomeScreen(navControllerX: NavController) {

    MaterialTheme(
        typography = myTypography // Applying custom typography here
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Column(
                modifier = Modifier
                    .padding(all = 25.dp)
                    .weight(3f),
                verticalArrangement = Arrangement.Center
            ) {
                TopBarUI()
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(4f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.nxtventory),
                    contentDescription = "welcome_logo"
                )
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(3f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                BottomBarUI(navControllerX)
            }
        }
    }
}


@Composable
fun TopBarUI() {
    Text(
        text = "Get started!",
        style = MaterialTheme.typography.displayMedium,
    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = "Best way to manage your inventory",
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
fun BottomBarUI(navControllerX: NavController) {
    Button(
        modifier = Modifier
            .width(250.dp)
            .height(60.dp),
        onClick = {
            navControllerX.navigate(Screen.SignIn.route)
        }
    ) {
        Text(
            text = "Login",
            style = MaterialTheme.typography.titleMedium
        )
    }
    Spacer(modifier = Modifier.height(20.dp))
    OutlinedButton(
        modifier = Modifier
            .width(250.dp)
            .height(60.dp),
        onClick = {
            navControllerX.navigate(Screen.SignUp.route)
        }
    ) {
        Text(
            text = "Create Account",
            style = MaterialTheme.typography.titleMedium
        )
    }
    Spacer(modifier = Modifier.height(50.dp))
}

@Preview(showSystemUi = true)
@Composable
fun WelcomeScreenPreview1() {
    WelcomeScreen(navControllerX = rememberNavController())
}
