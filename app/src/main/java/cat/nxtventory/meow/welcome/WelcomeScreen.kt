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
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cat.nxtventory.R
import cat.nxtventory.meow.signin.ui.SignInScreen
import cat.nxtventory.meow.signup.ui.SignUpScreen
import cat.nxtventory.ui.theme.myTypography

class WelcomeScreen : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        WelcomeScreenUI(navigator)
    }

}

@Composable
private fun WelcomeScreenUI(navigator: Navigator?) {

    MaterialTheme(
        typography = myTypography
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
                BottomBarUI(navigator)
            }
        }
    }
}

@Composable
private fun TopBarUI() {
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
private fun BottomBarUI(navigator: Navigator?) {

    Button(
        modifier = Modifier
            .width(250.dp)
            .height(60.dp),
        onClick = {
            navigator?.push(SignInScreen())
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
            navigator?.push(SignUpScreen())
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
fun WelcomeScreenPreview() {

    val navigator = LocalNavigator.current
    WelcomeScreenUI(navigator)

}
