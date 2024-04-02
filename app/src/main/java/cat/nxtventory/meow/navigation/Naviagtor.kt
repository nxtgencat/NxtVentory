package cat.nxtventory.meow.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import cat.nxtventory.meow.billing.ui.BillingScreen
import cat.nxtventory.meow.home.ui.HomeScreen
import cat.nxtventory.meow.inventory.InventoryScreen
import cat.nxtventory.meow.loyalty.LoyaltyScreen
import cat.nxtventory.meow.navigation.data.Screen
import cat.nxtventory.meow.refunds.RefundScreen
import cat.nxtventory.meow.settings.SettingsScreen
import cat.nxtventory.meow.signout.SignOutScreen
import cat.nxtventory.meow.vendor.VendorScreen

@Composable
fun Navigate(
    navController: NavHostController,
    innerPadding: PaddingValues,
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(innerPadding = innerPadding)
        }
        composable(Screen.Billing.route) {
            BillingScreen(innerPadding = innerPadding)
        }
        composable(Screen.Inventory.route) {
            InventoryScreen(innerPadding = innerPadding)
        }
        composable(Screen.Refund.route) {
            RefundScreen(innerPadding = innerPadding)
        }
        composable(Screen.Loyalty.route) {
            LoyaltyScreen(innerPadding = innerPadding)
        }
        composable(Screen.Vendor.route) {
            VendorScreen(innerPadding = innerPadding)
        }
        composable(Screen.Settings.route) {
            SettingsScreen(innerPadding = innerPadding)
        }
        composable(Screen.Account.route) {
            SignOutScreen(innerPadding = innerPadding)
        }
    }
}