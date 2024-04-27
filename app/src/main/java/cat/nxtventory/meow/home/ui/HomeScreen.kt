package cat.nxtventory.meow.home.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
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
import cat.nxtventory.meow.home.data.HomeScreenSampleData
import cat.nxtventory.meow.home.data.HomeScreenSampleData.SaleAmount
import cat.nxtventory.meow.home.data.HomeScreenSampleData.availableInventoryItems
import cat.nxtventory.meow.home.data.HomeScreenSampleData.customers
import cat.nxtventory.meow.home.data.HomeScreenSampleData.earnings
import cat.nxtventory.meow.home.data.HomeScreenSampleData.numberOfBills
import cat.nxtventory.meow.home.data.HomeScreenSampleData.user
import cat.nxtventory.ui.theme.NxtVentoryTheme

@Composable
fun HomeScreen(innerPadding: PaddingValues) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .padding(start = 40.dp, top = 20.dp, end = 40.dp)
        ) {
            Text(
                modifier = Modifier.weight(0.7f),
                text = "Hello, $user !",
                style = MaterialTheme.typography.titleMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Box(
                modifier = Modifier
                    .weight(0.5f)
                    .fillMaxWidth(),
               contentAlignment = Alignment.CenterEnd
            ) {
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(20.dp))
                        .background(MaterialTheme.colorScheme.surfaceContainerHigh)
                        .clickable { },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier
                            .padding(start = 15.dp, end = 5.dp, top = 2.dp, bottom = 2.dp),
                        text = "Today",
                        style = MaterialTheme.typography.headlineSmall
                    )
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = null
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                }
            }
        }
        Column(
            modifier = Modifier
                .padding(25.dp)
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            GlanceCard(SaleAmount, numberOfBills)
            Spacer(modifier = Modifier.height(15.dp))
            EarningsCard(earnings, availableInventoryItems, customers)
        }
    }
}

@Composable
fun GlanceCard(
    saleAmount: Double,
    numberOfBills: Int,
) {
    Card(
        modifier = Modifier.width(350.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = GetTodaysDayAndDateLegacy(),
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "₹%,.2f".format(saleAmount),
                style = MaterialTheme.typography.displayMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "No.Of Bills: $numberOfBills",
                style = MaterialTheme.typography.titleSmall
            )
        }
    }
}

@Composable
fun EarningsCard(
    earnings: Double,
    availableInventoryItems: Int,
    customers: Int,
) {
    Card(
        modifier = Modifier.width(350.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
        ) {
            Text(
                text = "Earnings",
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "₹%,.2f".format(earnings),
                style = MaterialTheme.typography.displayMedium,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row {
                InventoryItemCard(availableInventoryItems)
                Spacer(modifier = Modifier.width(10.dp))
                CustomerCard(customers)
            }
        }
    }
}

@Composable
fun CustomerCard(
    customers: Int
) {
    Card(
        modifier = Modifier.width(150.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background,),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
        ) {
            Text(
                text = "Customers",
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Icon(
                    imageVector = Icons.Filled.People,
                    contentDescription = "customers"
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "$customers",
                    style = MaterialTheme.typography.headlineLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    }
}

@Composable
fun InventoryItemCard(
    availableInventoryItems: Int
) {
    Card(
        modifier = Modifier.width(150.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background,),
        shape = RoundedCornerShape(20.dp)
    ) {
        Column(
            modifier = Modifier.padding(15.dp),
        ) {
            Text(
                text = "Inventory",
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row {
                Icon(
                    imageVector = Icons.Filled.Inventory,
                    contentDescription = "inventory"
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = availableInventoryItems.toString(),
                    style = MaterialTheme.typography.headlineLarge,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 1
                )
            }
        }
    }
}

@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(showSystemUi = true)
@Composable
private fun UniversalPreview() {
    NxtVentoryTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            HomeScreen(innerPadding = PaddingValues())

        }
    }
}
