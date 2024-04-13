package cat.nxtventory.meow.welcome

import android.content.res.Configuration
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
import androidx.compose.material3.Surface
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
import cat.nxtventory.ui.theme.NxtVentoryTheme

class WelcomeScreen : Screen {
    @Composable
    override fun Content() {
        WelcomeScreenUI()
    }

}

@Composable
private fun WelcomeScreenUI() {

    val navigator = LocalNavigator.current

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
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
            ContentUI()
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


@Composable
private fun TopBarUI() {
    Text(
        text = "NxtVentory",
        style = MaterialTheme.typography.displayMedium,
    )
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        text = "Smarter inventory, faster workflow.",
        style = MaterialTheme.typography.titleLarge,
    )
}

@Composable
fun ContentUI() {
    Icon(
        painter = painterResource(id = R.drawable.nxtventory),
        contentDescription = "welcome_logo"
    )
}

@Composable
private fun BottomBarUI(
    navigator: Navigator?
) {

    SignInButton { navigator?.replace(SignInScreen()) }
    Spacer(modifier = Modifier.height(20.dp))
    CreateAccountButton { navigator?.replace(SignUpScreen()) }
    Spacer(modifier = Modifier.height(50.dp))

}

@Composable
fun SignInButton(
    onClick: () -> Unit
) {
    Button(
        modifier = Modifier
            .width(250.dp)
            .height(60.dp),
        onClick = { onClick() }
    ) {
        Text(
            text = "Sign in",
            style = MaterialTheme.typography.titleMedium
        )
    }
}

@Composable
fun CreateAccountButton(
    onClick: () -> Unit
) {
    OutlinedButton(
        modifier = Modifier
            .width(250.dp)
            .height(60.dp),
        onClick = { onClick() }
    ) {
        Text(
            text = "Create account",
            style = MaterialTheme.typography.titleMedium
        )
    }
}


@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Preview(showSystemUi = true)
@Composable
private fun UniveralPreview() {
    NxtVentoryTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            WelcomeScreenUI()

        }
    }
}
