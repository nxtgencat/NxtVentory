package cat.nxtventory.meow.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.nxtventory.ui.theme.OneUISans


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
            .height(200.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Today",
                fontSize = 22.sp,
                fontFamily = OneUISans,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "₹${todaysaleamount}",
                fontSize = 44.sp,
                fontFamily = OneUISans,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = "No.Of Bills: ${bills}",
                fontSize = 16.sp,
                fontFamily = OneUISans,
                fontWeight = FontWeight.Bold
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
            .height(200.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxSize()
        ) {
            Text(
                text = "Inventory Value",
                fontSize = 22.sp,
                fontFamily = OneUISans,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "₹${todaysaleamount}",
                fontSize = 44.sp,
                fontFamily = OneUISans,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.padding(top = 10.dp),
                text = "No.Of Items: ${bills}",
                fontSize = 16.sp,
                fontFamily = OneUISans,
                fontWeight = FontWeight.Bold
            )
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(innerPadding = PaddingValues())
}

