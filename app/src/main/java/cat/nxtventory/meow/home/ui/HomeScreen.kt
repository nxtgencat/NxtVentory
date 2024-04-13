package cat.nxtventory.meow.home.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import cat.nxtventory.meow.home.data.GetTodaysDayAndDateLegacy
import cat.nxtventory.meow.home.data.HomeScreenSampleData.instockItems
import cat.nxtventory.meow.home.data.HomeScreenSampleData.todayBills
import cat.nxtventory.meow.home.data.HomeScreenSampleData.todayEarnigs
import cat.nxtventory.meow.home.data.HomeScreenSampleData.todaySaleAmount
import cat.nxtventory.meow.home.data.HomeScreenSampleData.user
import cat.nxtventory.ui.theme.NxtVentoryTheme

@Composable
fun HomeScreen(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
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
            HomeScreenSaleCard(todaySaleAmount, todayBills)
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                EarningsCard(todayEarnigs)
                Spacer(modifier = Modifier.width(10.dp))
                InventoryItemCard(instockItems)
            }
            Spacer(modifier = Modifier.height(10.dp))
//            CustomerCard()
            Spacer(modifier = Modifier.height(10.dp))
//            GraphCard()
        }
    }
}

@Composable
fun HomeScreenSaleCard(
    todaysaleamount: Double,
    todayBills: Int,
) {
    Card(
        modifier = Modifier
            .width(330.dp)
            .height(150.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = GetTodaysDayAndDateLegacy(),
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "₹%,.2f".format(todaysaleamount),
                style = MaterialTheme.typography.displayMedium,
            )
            Text(
                text = "No.Of Bills: $todayBills",
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Composable
fun EarningsCard(
    todayEarnigs: Double
) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        shape = RoundedCornerShape(20.dp)

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
                text = "₹%,.2f".format(todayEarnigs),

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
            .height(60.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        shape = RoundedCornerShape(20.dp)

    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Spacer(modifier = Modifier.width(20.dp))
                Box {
                    Icon(
                        imageVector = Icons.Filled.People,
                        contentDescription = "people"
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
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
        shape = RoundedCornerShape(25.dp)

    ) {

    }
}

@Composable
fun InventoryItemCard(
    inventoryItems: Int
) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(100.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background,
        ),
        shape = RoundedCornerShape(20.dp)

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
                    text = inventoryItems.toString(),
                    style = MaterialTheme.typography.headlineLarge,
                )
            }

        }
    }
}


@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(showSystemUi = true)
@Composable
private fun UniveralPreview() {
    NxtVentoryTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            HomeScreen(innerPadding = PaddingValues())

        }
    }
}
