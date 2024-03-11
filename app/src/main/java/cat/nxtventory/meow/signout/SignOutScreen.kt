package cat.nxtventory.meow.signout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.nxtventory.ui.theme.OneUISans


@Composable
fun SignOutScreen(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        SignOutScreenCard("SignOut")
    }
}

@Composable
fun SignOutScreenCard(
    title: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = title,
                fontSize = 44.sp,
                fontFamily = OneUISans,
                fontWeight = FontWeight.Bold
            )

        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun SignOutScreenPreview() {
    SignOutScreen(innerPadding = PaddingValues())
}