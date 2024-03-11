package cat.nxtventory.meow.billing.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.semantics.isTraversalGroup
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.traversalIndex
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cat.nxtventory.meow.billing.meow.InventoryItem
import cat.nxtventory.meow.billing.meow.inventoryItems
import cat.nxtventory.ui.theme.OneUISans


@Composable
fun BillingScreen(innerPadding: PaddingValues) {

    var bills by rememberSaveable { mutableStateOf(listOf("Bill 1")) }
    var selectedBillIndex by rememberSaveable { mutableStateOf(0) } // Initially select Bill 1

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(modifier = Modifier.width(30.dp))
            BillLazyRow(
                bills = bills,
                selectedBillIndex = selectedBillIndex,
                onBillClick = { index ->
                    selectedBillIndex = if (index == selectedBillIndex) -1 else index
                },
                onDeleteClick = { index ->
                    if (bills.size > 1) {
                        bills = bills.toMutableList().apply { removeAt(index) }
                        selectedBillIndex =
                            if (selectedBillIndex == index) -1 else selectedBillIndex
                        // Adjust selection after deletion
                        // Update indexes of remaining bills
                        bills = bills.mapIndexed { i, _ -> "Bill ${i + 1}" }.toMutableList()
                    }
                }
            )
            Spacer(modifier = Modifier.width(20.dp))
            AddBillButton(
                onClick = {
                    bills = bills.toMutableList().apply { add("Bill ${bills.size + 1}") }
                    // Preserve selection on FAB click
                }
            )
        }
        Column {
            ItemSearch()
        }

        Spacer(modifier = Modifier.height(20.dp))
        Column {
            InventoryTitles()
            Spacer(modifier = Modifier.height(10.dp))
            InventoryList(inventoryItems = inventoryItems)
        }

    }
}

@Composable
fun BillnigScreenBottomBar() {
    Row(
        modifier = Modifier
            .height(125.dp)
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .background(MaterialTheme.colorScheme.primary)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        var totalItems = inventoryItems.size
        var totalQuantity = 0
        var totalMRP = 0.0
        var totalSP = 0.0
        var totalDisc = 0.0

        inventoryItems.forEach { item ->
            totalMRP += item.mrp * item.quantity
            totalSP += item.sp * item.quantity
            totalQuantity += item.quantity
            totalDisc = totalMRP - totalSP
        }

        val totalAmount = totalSP
        BottombarElements(title = "Qty/Item", desc = "${totalQuantity}/${totalItems}")
        BottombarElements(title = "Discount", desc = "₹${"%.2f".format(totalDisc)}")
        BottombarElements(title = "Amount", desc = "₹${"%.2f".format(totalMRP)}")
    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Composable
fun BottombarElements(title: String, desc: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Text(
            color = MaterialTheme.colorScheme.surfaceVariant,
            text = title,
            fontSize = 14.sp,
            fontFamily = OneUISans,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            color = MaterialTheme.colorScheme.onPrimary,
            text = desc,
            fontSize = 28.sp,
            fontFamily = OneUISans,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(10.dp))
    }
}

@Composable
fun BillLazyRow(
    bills: List<String>,
    selectedBillIndex: Int,
    onBillClick: (Int) -> Unit,
    onDeleteClick: (Int) -> Unit,
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth(0.8f)
            .fillMaxHeight()
            .clip(RoundedCornerShape(50.dp)),
//            .border(1.dp, MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(30.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(bills) { billName ->
            val index = bills.indexOf(billName)
            BillButton(
                billNo = index + 1, // Convert index to bill number
                isBillSelected = index == selectedBillIndex,
                onButtonClick = { onBillClick(index) },
                onDeleteClick = { onDeleteClick(index) }
            )
            Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Composable
fun AddBillButton(
    onClick: () -> Unit,
) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier.size(30.dp)
    ) {
        Icon(Icons.Default.Add, contentDescription = "Add bill")
    }
}

@Composable
fun BillButton(
    billNo: Int,
    isBillSelected: Boolean,
    onButtonClick: () -> Unit,
    onDeleteClick: () -> Unit,
) {
    Button(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp),
        colors = if (isBillSelected) ButtonDefaults.buttonColors() else ButtonDefaults.outlinedButtonColors(),
        onClick = onButtonClick
    ) {
        Text(
            modifier = Modifier.padding(end = 10.dp),
            fontFamily = OneUISans,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            text = "Bill $billNo"
        )
        if (isBillSelected) {
            Icon(
                modifier = Modifier
                    .size(20.dp)
                    .clickable(onClick = onDeleteClick),
                imageVector = Icons.Filled.Close,
                contentDescription = "close"
            )
        }
    }
}

@Composable
fun ItemSearch() {
    var itemSearch by rememberSaveable { mutableStateOf("") }
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 20.dp, end = 20.dp),
            shape = RoundedCornerShape(30.dp),
            singleLine = true,
            value = itemSearch,
            onValueChange = {
                itemSearch = it
            },
            label = {
                Text(
                    text = "Item Search"
                )
            },
            trailingIcon = {
                Icon(
                    tint = MaterialTheme.colorScheme.primary,
                    imageVector = Icons.Filled.QrCode,
                    contentDescription = null
                )
            }
        )
    }
}

@Composable
fun InventoryTitles() {
    Spacer(modifier = Modifier.height(5.dp))
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ItemInfo(text = "S.No", width = 0.15f)
        ItemInfo(text = "Item", width = 0.35f)
        ItemInfo(text = "Qty", width = 0.21f)
        ItemInfo(text = "MRP", width = 0.33f)
        ItemInfo(text = "SP", width = 0.5f)
        ItemInfo(text = "Total", width = 0.7f)

    }
    Spacer(modifier = Modifier.height(5.dp))
    Divider(color = MaterialTheme.colorScheme.secondary)
}

@Composable
fun InventoryList(inventoryItems: List<InventoryItem>) {
    LazyColumn {

        items(inventoryItems) { item ->
            InventoryListRow(item)
            Spacer(modifier = Modifier.height(10.dp))
        }
    }
}

@Composable
fun InventoryListRow(item: InventoryItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp, end = 10.dp, top = 10.dp, bottom = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        ItemInfo(text = item.id.toString(), width = 0.1f)
        ItemInfo(text = item.name, width = 0.4f)
        ItemInfo(text = item.quantity.toString(), width = 0.2f)
        ItemInfo(text = item.mrp.toString(), width = 0.3f)
        ItemInfo(text = item.sp.toString(), width = 0.5f)
        ItemInfo(text = item.totalCost.toString(), width = 1f)

    }
}

@Composable
fun ItemInfo(text: String, width: Float) {
    Text(
        fontFamily = OneUISans,
        fontSize = 15.sp,
        fontWeight = FontWeight.Bold,
        text = text,
        maxLines = 1,
        modifier = Modifier.fillMaxWidth(width)
    )
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemSearchBar() {

    var searchHistory = remember { mutableStateListOf("") }
    var itemName by rememberSaveable { mutableStateOf("") }
    var isSearching by rememberSaveable { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .semantics { isTraversalGroup = true }
    ) {
        SearchBar(
            modifier = Modifier
                .align(Alignment.TopCenter)
                .semantics { traversalIndex = -1f },
            query = itemName,
            onQueryChange = { itemName = it },
            onSearch = { isSearching = false },
            active = isSearching,
            onActiveChange = {
                isSearching = it
            },
            placeholder = { Text("Search Item") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            trailingIcon = { Icon(Icons.Default.MoreVert, contentDescription = null) },
        ) {
            searchHistory.forEach {
                if (it.isNotEmpty()) {
                    Row(modifier = Modifier.padding(all = 14.dp)) {
                        Icon(imageVector = Icons.Default.History, contentDescription = null)
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(text = it)
                    }
                }
            }
        }
    }
}


@Preview(showSystemUi = true)
@Composable
fun BillingScreenPreview() {
    BillingScreen(innerPadding = PaddingValues())
}

@Preview(showBackground = true)
@Composable
fun BillnigScreenBottomBarPreview() {
    BillnigScreenBottomBar()

}