package cat.nxtventory.meow.navigation.data

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
import androidx.compose.ui.graphics.vector.ImageVector

enum class Screen(val route: String) {
    Home("home"),
    Billing("billing"),
    Inventory("inventory"),
    Refund("refund"),
    Loyalty("loyalty"),
    Vendor("vendor"),
    Settings("settings"),
    Account("account"),
}

data class NavigationItem(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unSelectedIcon: ImageVector,
)

val navDraweritems = listOf(
    NavigationItem(
        title = "Home",
        route = Screen.Home.route,
        selectedIcon = Icons.Filled.Home,
        unSelectedIcon = Icons.Outlined.Home,
    ),
    NavigationItem(
        title = "Billing",
        route = Screen.Billing.route,
        selectedIcon = Icons.Filled.Receipt,
        unSelectedIcon = Icons.Outlined.Receipt,
    ),
    NavigationItem(
        title = "Inventory",
        route = Screen.Inventory.route,
        selectedIcon = Icons.Filled.Inventory,
        unSelectedIcon = Icons.Outlined.Inventory,
    ),
    NavigationItem(
        title = "Refunds",
        route = Screen.Refund.route,
        selectedIcon = Icons.Filled.Wallet,
        unSelectedIcon = Icons.Outlined.Wallet,
    ),
    NavigationItem(
        title = "Loyalty",
        route = Screen.Loyalty.route,
        selectedIcon = Icons.Filled.Loyalty,
        unSelectedIcon = Icons.Outlined.Loyalty,
    ),
    NavigationItem(
        title = "Vendor",
        route = Screen.Vendor.route,
        selectedIcon = Icons.Filled.People,
        unSelectedIcon = Icons.Outlined.People,
    ),
    NavigationItem(
        title = "Settings",
        route = Screen.Settings.route,
        selectedIcon = Icons.Filled.Settings,
        unSelectedIcon = Icons.Outlined.Settings,
    ),
    NavigationItem(
        title = "Account",
        route = Screen.Account.route,
        selectedIcon = Icons.Filled.Person,
        unSelectedIcon = Icons.Outlined.Person,
    )
)