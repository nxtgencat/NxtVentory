package cat.nxtventory.meow.welcome

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.nxtventory.R
import cat.nxtventory.meow.navigation.data.Screen
import cat.nxtventory.ui.theme.OneUISans

@Composable
fun WelcomeScreen(navControllerX: NavController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            modifier = Modifier.size(150.dp),
            painter = painterResource(id = R.drawable.nxtventory),
            contentDescription = "welcome_logo"
        )
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Welcome",
            fontSize = 40.sp,
            fontFamily = OneUISans,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(30.dp))
        Button(
            modifier = Modifier
                .width(250.dp)
                .height(50.dp),
            onClick = {
                navControllerX.navigate(Screen.SignIn.route)
            }
        ) {
            Text(text = "Login")
        }
        Spacer(modifier = Modifier.height(20.dp))
        OutlinedButton(
            modifier = Modifier
                .width(250.dp)
                .height(50.dp),
            onClick = {
                navControllerX.navigate(Screen.SignUp.route)
            }
        ) {
            Text(text = "Create Account")
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun WelcomeScreenPreview() {
    WelcomeScreen(navControllerX = rememberNavController())
}