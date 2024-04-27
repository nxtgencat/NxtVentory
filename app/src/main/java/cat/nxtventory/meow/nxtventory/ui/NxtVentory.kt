package cat.nxtventory.meow.nxtventory.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.twotone.Notifications
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cat.nxtventory.R
import cat.nxtventory.meow.billing.ui.BillnigScreenBottomBar
import cat.nxtventory.meow.navigation.Navigate
import cat.nxtventory.meow.navigation.ScaffScreen
import cat.nxtventory.meow.nxtventory.data.navDraweritems
import cat.nxtventory.ui.theme.NxtVentoryTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NxtVentory : Screen {
    @Composable
    override fun Content() {
        NxtVentoryUI()
    }
}

@Composable
fun NxtVentoryUI() {

    val navigator = LocalNavigator.current
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val context = LocalContext.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val topBarTitle =
        if (currentRoute != null) {
            navDraweritems[navDraweritems.indexOfFirst {
                it.route == currentRoute
            }].title
        } else {
            navDraweritems[0].title
        }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = drawerState.isOpen,
        drawerContent = {
            NavigationDrawer(
                currentRoute,
                navController,
                scope,
                drawerState
            )
        }
    ) {
        Scaffold(
            topBar = {
                NxtVentoryTopBar(
                    topBarTitle,
                    scope = scope,
                    drawerState = drawerState
                )
            },
            content = { innerPadding ->
                Navigate(navController = navController, innerPadding = innerPadding)
            },
            floatingActionButton = {
                when (currentRoute) {
                    ScaffScreen.Billing.route, ScaffScreen.Settings.route, ScaffScreen.Account.route -> {}
                    else -> {
                        NxtVentoryFAB(navController)
                    }
                }
            },
            bottomBar = {
                when (currentRoute) {
                    ScaffScreen.Billing.route -> BillnigScreenBottomBar()
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NxtVentoryTopBar(
    topBarTitle: String,
    scope: CoroutineScope,
    drawerState: DrawerState,
) {
    TopAppBar(
        title = {
            Text(
                text = topBarTitle,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = BottomAppBarDefaults.containerColor),
        navigationIcon = {
            IconButton(
                modifier = Modifier.padding(start = 20.dp, end = 10.dp),
                onClick = {
                    scope.launch { drawerState.open() }
                }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.menu),
                    contentDescription = null
                )

            }
        },
        actions = {
            IconButton(
                modifier = Modifier.padding(end = 20.dp),
                onClick = { }
            ) {
                Icon(
                    imageVector = Icons.TwoTone.Notifications,
                    contentDescription = "notifications"
                )
            }
        }
    )
}


@Composable
fun NxtVentoryFAB(navController: NavController) {
    FloatingActionButton(
        modifier = Modifier.padding(20.dp),
        onClick = {
            navController.navigate(ScaffScreen.Billing.route) {
                popUpTo(navController.graph.startDestinationId) {
                    saveState = true
                }
                launchSingleTop = true
                restoreState = true
            }
        },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "fab"
        )
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
            NxtVentoryUI()
        }
    }
}