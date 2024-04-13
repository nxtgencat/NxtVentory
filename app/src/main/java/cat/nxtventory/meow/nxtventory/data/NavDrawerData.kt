package cat.nxtventory.meow.nxtventory.data

import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Inventory
import androidx.compose.material.icons.filled.Loyalty
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Inventory
import androidx.compose.material.icons.outlined.Loyalty
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Wallet
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import cat.nxtventory.meow.firebase.UserDataManager
import cat.nxtventory.meow.navigation.ScaffScreen
import kotlinx.coroutines.launch

data class NavigationItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
)

val navDraweritems = listOf(
    NavigationItem(
        title = "Home",
        route = ScaffScreen.Home.route,
        selectedIcon = Icons.Filled.Home,
        unSelectedIcon = Icons.Outlined.Home,
    ),
    NavigationItem(
        title = "Billing",
        route = ScaffScreen.Billing.route,
        selectedIcon = Icons.Filled.Receipt,
        unSelectedIcon = Icons.Outlined.Receipt,
    ),
    NavigationItem(
        title = "Inventory",
        route = ScaffScreen.Inventory.route,
        selectedIcon = Icons.Filled.Inventory,
        unSelectedIcon = Icons.Outlined.Inventory,
    ),
    NavigationItem(
        title = "Refunds",
        route = ScaffScreen.Refund.route,
        selectedIcon = Icons.Filled.Wallet,
        unSelectedIcon = Icons.Outlined.Wallet,
    ),
    NavigationItem(
        title = "Loyalty",
        route = ScaffScreen.Loyalty.route,
        selectedIcon = Icons.Filled.Loyalty,
        unSelectedIcon = Icons.Outlined.Loyalty,
    ),
    NavigationItem(
        title = "Vendor",
        route = ScaffScreen.Vendor.route,
        selectedIcon = Icons.Filled.People,
        unSelectedIcon = Icons.Outlined.People,
    ),
    NavigationItem(
        title = "Settings",
        route = ScaffScreen.Settings.route,
        selectedIcon = Icons.Filled.Settings,
        unSelectedIcon = Icons.Outlined.Settings,
    ),
    NavigationItem(
        title = "Account",
        route = ScaffScreen.Account.route,
        selectedIcon = Icons.Filled.Person,
        unSelectedIcon = Icons.Outlined.Person,
    )
)

class NavDrawerModel : ViewModel() {

    var userDetails by mutableStateOf<Map<String, Any>?>(null)
        private set  // Make the state private for better control


    fun loadUserDetails(context: Context) {
        viewModelScope.launch {  // Use viewModelScope for coroutines
            val result = try {
                UserDataManager.getUserDetails(context)
            } catch (exception: Exception) {
                // Handle errors appropriately, e.g., log, show UI message
                null
            }
            userDetails = result
        }
    }
}