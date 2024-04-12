package cat.nxtventory.meow.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cat.nxtventory.meow.account.ui.SignOutScreen
import cat.nxtventory.meow.billing.ui.BillingScreen
import cat.nxtventory.meow.home.ui.HomeScreen
import cat.nxtventory.meow.inventory.ui.InventoryScreen
import cat.nxtventory.meow.loyalty.ui.LoyaltyScreen
import cat.nxtventory.meow.refunds.ui.RefundScreen
import cat.nxtventory.meow.settings.ui.SettingsScreen
import cat.nxtventory.meow.vendor.VendorScreen

enum class ScaffScreen(val route: String) {
    Home("home"),
    Billing("billing"),
    Inventory("inventory"),
    Refund("refund"),
    Loyalty("loyalty"),
    Vendor("vendor"),
    Settings("settings"),
    Account("account"),
    Welcome("welcome")
}

@Composable
fun Navigate(
    navController: NavHostController,
    innerPadding: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = ScaffScreen.Home.route
    ) {
        composable(ScaffScreen.Home.route) {
            HomeScreen(innerPadding = innerPadding)
        }
        composable(ScaffScreen.Billing.route) {
            BillingScreen(innerPadding = innerPadding)
        }
        composable(ScaffScreen.Inventory.route) {
            InventoryScreen(innerPadding = innerPadding)
        }
        composable(ScaffScreen.Refund.route) {
            RefundScreen(innerPadding = innerPadding)
        }
        composable(ScaffScreen.Loyalty.route) {
            LoyaltyScreen(innerPadding = innerPadding)
        }
        composable(ScaffScreen.Vendor.route) {
            VendorScreen(innerPadding = innerPadding)
        }
        composable(ScaffScreen.Settings.route) {
            SettingsScreen(innerPadding = innerPadding)
        }
        composable(ScaffScreen.Account.route) {
            SignOutScreen(innerPadding = innerPadding)
        }
    }
}