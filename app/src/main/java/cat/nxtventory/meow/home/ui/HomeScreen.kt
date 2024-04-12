package cat.nxtventory.meow.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.People
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
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
        val user = "Sushanth"
        Column(
            modifier = Modifier
                .padding(25.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Hello, $user !",
                style = MaterialTheme.typography.titleMedium
            )
        }
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
                .background(color = MaterialTheme.colorScheme.surfaceContainer)
                .padding(30.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            HomeScreenSaleCard("25,075.96", "154")
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                EarningsCard("5,450.26", "2,985")
                Spacer(modifier = Modifier.width(15.dp))
                InventoryItemCard("2,459")
            }
            Spacer(modifier = Modifier.height(20.dp))
            CustomerCard()
            Spacer(modifier = Modifier.height(20.dp))
            GraphCard()
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
            .width(650.dp)
            .height(150.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            val dayAndDate = "Fri, 12th April 2024"
            Text(
                text = dayAndDate,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "₹ ${todaysaleamount}",
                style = MaterialTheme.typography.displayMedium,
            )
            Text(
                text = "No.Of Bills: $bills",
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Composable
fun EarningsCard(
    todaysaleamount: String,
    bills: String,
) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = "Earnings",
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "₹ ${todaysaleamount}",
                style = MaterialTheme.typography.headlineLarge,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
fun CustomerCard() {
    Card(
        modifier = Modifier
            .width(650.dp)
            .height(50.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.width(15.dp))
            Icon(
                imageVector = Icons.Filled.People,
                contentDescription = "people"
            )
            Spacer(modifier = Modifier.width(25.dp))
            Box(
                modifier = Modifier.weight(0.7f)
            ) {
                Text(
                    text = "Customers",
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Box(
                modifier = Modifier.weight(0.3f),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "69",
                    style = MaterialTheme.typography.titleMedium
                )
            }

        }
    }
}

@Composable
fun GraphCard() {
    Card(
        modifier = Modifier
            .width(650.dp)
            .height(200.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
    ) {

    }
}

@Composable
fun InventoryItemCard(
    inventoryItems: String
) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceAround
        ) {
            Text(
                text = "Inventory",
                style = MaterialTheme.typography.titleSmall
            )
            Row {
                Icon(
                    imageVector = Icons.Filled.Inventory,
                    contentDescription = "inventory"
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = inventoryItems,
                    style = MaterialTheme.typography.headlineLarge,
                )
            }

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

