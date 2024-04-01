package cat.nxtventory.meow.signout

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cat.nxtventory.meow.firebase.UserDataManager
import cat.nxtventory.ui.theme.myTypography


@Composable
fun SignOutScreen(innerPadding: PaddingValues) {

    val context = LocalContext.current

    MaterialTheme(
        typography = myTypography // Applying custom typography here
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Button(
                modifier = Modifier
                    .width(250.dp)
                    .height(60.dp),
                onClick = {
                    UserDataManager.signOut(context) {
                        Log.d("MyApp", "Signed out successfully")
                    }
                }
            ) {
                Text(
                    text = "Signout",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SignOutScreenPreview() {
    SignOutScreen(innerPadding = PaddingValues())
}