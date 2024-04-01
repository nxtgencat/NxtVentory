package cat.nxtventory.meow.billing.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.icons.filled.QrCode
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import cat.nxtventory.meow.billing.data.BillingModel
import cat.nxtventory.meow.billing.data.InventoryItem
import cat.nxtventory.meow.billing.data.inventoryItems
import cat.nxtventory.ui.theme.myTypography


@Composable
fun BillingScreen(innerPadding: PaddingValues) {
    val viewModel: BillingModel = viewModel()
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
                bills = viewModel.bills,
                selectedBillIndex = viewModel.selectedBillIndex,
                onBillClick = { index ->
                    viewModel.selectedBillIndex =
                        if (index == viewModel.selectedBillIndex) -1 else index
                },
                onDeleteClick = { index ->
                    viewModel.removeBill(index)
                }
            )
            Spacer(modifier = Modifier.width(20.dp))
            AddBillButton(
                onClick = {
                    viewModel.addBill()
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
            .clip(RoundedCornerShape(topStart = 25.dp, topEnd = 25.dp))
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {

        val totalItems = inventoryItems.size
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
            text = title,
            style = MaterialTheme.typography.labelLarge
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = desc,
            style = MaterialTheme.typography.displaySmall
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
            style = MaterialTheme.typography.titleSmall,
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
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 40.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            modifier = Modifier
                .weight(0.9f),
            shape = RoundedCornerShape(50.dp),
            textStyle = MaterialTheme.typography.titleSmall,
            singleLine = true,
            value = itemSearch,
            onValueChange = {
                itemSearch = it
            },
            label = {
                Text(
                    text = "Item Search",
                    style = MaterialTheme.typography.titleSmall
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
        Spacer(modifier = Modifier.weight(0.1f))
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
    HorizontalDivider()
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
        style = MaterialTheme.typography.titleSmall,
        text = text,
        maxLines = 1,
        modifier = Modifier.fillMaxWidth(width)
    )
}

@Preview(showSystemUi = true)
@Composable
fun BillingScreenPreview() {
    MaterialTheme(
        typography = myTypography // Applying custom typography here
    ) {
        BillingScreen(innerPadding = PaddingValues())
    }
}

@Preview(showBackground = true)
@Composable
fun BillnigScreenBottomBarPreview() {
    MaterialTheme(
        typography = myTypography // Applying custom typography here
    ) {
        BillnigScreenBottomBar()
    }
}