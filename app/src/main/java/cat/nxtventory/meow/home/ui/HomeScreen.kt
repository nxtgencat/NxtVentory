package cat.nxtventory.meow.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cat.nxtventory.ui.theme.myTypography


@Composable
fun HomeScreen(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Column(
            modifier = Modifier
                .padding(30.dp)
                .fillMaxSize(),
        ) {
            HomeScreenSaleCard("25,075.96", "154")
            Spacer(modifier = Modifier.height(25.dp))
            HomeScreenInventoryCard("12,59,075.96", "2,985")
        }
    }
}

@Composable
fun HomeScreenSaleCard(
    todaysaleamount: String,
    bills: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        )
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Today",
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "₹${todaysaleamount}",
                style = MaterialTheme.typography.displayMedium,
            )
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = "No.Of Bills: $bills",
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Composable
fun HomeScreenInventoryCard(
    todaysaleamount: String,
    bills: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        )
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Inventory Value",
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "₹${todaysaleamount}",
                style = MaterialTheme.typography.displayMedium,
            )
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = "No.Of Items: $bills",
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    MaterialTheme(
        typography = myTypography // Applying custom typography here
    ) {
        HomeScreen(innerPadding = PaddingValues())
    }
}

