package cat.nxtventory

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.Navigator
import cat.nxtventory.meow.navigation.Navigate
import cat.nxtventory.meow.navigation.data.navDraweritems
import cat.nxtventory.meow.navigation.ui.NavigationDrawer
import cat.nxtventory.ui.theme.myTypography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class NxtVentory : Screen {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.current
        NxtVentoryUI(navigator)
    }
}

@Composable
fun NxtVentoryUI(navigator: Navigator?) {

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
    MaterialTheme(
        typography = myTypography
    ) {
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

                },
                bottomBar = {

                }
            )
        }
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
                modifier = Modifier.padding(start = 10.dp),
                onClick = {
                    scope.launch { drawerState.open() }
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Menu,
                    contentDescription = null
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

        },
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        elevation = FloatingActionButtonDefaults.bottomAppBarFabElevation()
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "Localized description"
        )
    }
}


@Preview(showSystemUi = true)
@Composable
fun NxtVentoryPreview() {
    val navigator = LocalNavigator.current
    NxtVentoryUI(navigator)
}