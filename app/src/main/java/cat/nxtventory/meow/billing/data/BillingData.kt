package cat.nxtventory.meow.billing.data

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

data class InventoryItem(
    val id: Int,
    val name: String,
    val quantity: Int,
    val mrp: Double,
    val sp: Double,
) {
    val totalCost: Double = quantity * sp
}

val inventoryItems = listOf(
    InventoryItem(1, "Peanut Butter", 3, 71.25, 61.75),
    InventoryItem(2, "Chocolate Cake", 5, 36.25, 25.25),
    InventoryItem(3, "Vanilla Icecream", 10, 61.50, 51.50),
    InventoryItem(4, "Strawberry Jam", 2, 40.50, 35.25),
    InventoryItem(5, "Lemonade Stand", 1, 10.50, 8.25),
    InventoryItem(6, "Blueberry Muffin", 1, 201.50, 180.25),
    InventoryItem(7, "Potato Chips", 4, 20.25, 15.25),
    InventoryItem(8, "Chicken Salad", 3, 20.75, 15.25),
    InventoryItem(9, "Fish Fry", 5, 15.25, 12.50),
    InventoryItem(10, "Coffee Beans", 3, 30.50, 25.25),
    InventoryItem(11, "Apple Pie", 1, 251.25, 201.25),
    InventoryItem(12, "Banana Bread", 1, 300.50, 251.25),
    InventoryItem(13, "Rice Pudding", 2, 100.50, 90.25),
    InventoryItem(14, "Pasta Sauce", 2, 71.50, 60.75),
    InventoryItem(15, "Chocolate Milk", 1, 201.25, 180.25),
    InventoryItem(16, "Tea Leaves", 1, 101.25, 90.25),
    InventoryItem(17, "Brown Sugar", 2, 80.25, 70.25),
    InventoryItem(18, "Rock Salt", 1, 15.25, 10.50),
    InventoryItem(19, "Black Pepper", 1, 30.50, 25.50),
    InventoryItem(20, "Butter Cookies", 1, 100.75, 90.50)
)

class BillingModel : ViewModel() {
    private val _bills = mutableStateOf(listOf("Bill 1"))
    val bills: List<String> by _bills

    private val _selectedBillIndex = mutableStateOf(0)
    var selectedBillIndex: Int by _selectedBillIndex

    fun addBill() {
        _bills.value = _bills.value.toMutableList().apply {
            add("Bill ${_bills.value.size + 1}")
        }
    }

    fun removeBill(index: Int) {
        if (_bills.value.size > 1) {
            _bills.value = _bills.value.toMutableList().apply {
                removeAt(index)
            }
            // Adjust selection after deletion
            _selectedBillIndex.value =
                if (_selectedBillIndex.value == index) -1 else _selectedBillIndex.value
        }
    }
}