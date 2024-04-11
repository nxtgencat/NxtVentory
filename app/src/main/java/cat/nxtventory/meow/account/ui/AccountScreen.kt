package cat.nxtventory.meow.account.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cat.nxtventory.meow.firebase.UserDataManager
import cat.nxtventory.ui.theme.myTypography


@Composable
fun SignOutScreen(innerPadding: PaddingValues) {
    val signOutProgress = rememberSaveable { mutableStateOf(false) }
    val signedOut = rememberSaveable { mutableStateOf(false) }
    val context = LocalContext.current

    MaterialTheme(
        typography = myTypography
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

            Button(
                modifier = Modifier
                    .width(250.dp)
                    .height(60.dp),
                enabled = !signOutProgress.value && !signedOut.value,
                onClick = {
                    UserDataManager.signOut(context) {
                        signOutProgress.value = true
                        Log.d("MyApp", "Signed out successfully")
                        Toast.makeText(
                            context,
                            "Signed out successfully.",
                            Toast.LENGTH_SHORT
                        ).show()
                        signedOut.value = true

                    }
                }
            ) {
                if (signOutProgress.value && !signedOut.value) {
                    CircularProgressIndicator()
                } else {
                    Text(
                        text = "Sign out",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SignOutScreenPreview() {
    SignOutScreen(innerPadding = PaddingValues())
}
