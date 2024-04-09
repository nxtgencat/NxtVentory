package cat.nxtventory.meow.navigation.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.nxtventory.R
import cat.nxtventory.meow.firebase.UserDataManager.getUsername
import cat.nxtventory.meow.navigation.data.NavigationItem
import cat.nxtventory.meow.navigation.data.navDraweritems
import cat.nxtventory.ui.theme.myTypography
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun NavigationDrawer(
    currentRoute: String?,
    navController: NavController,
    scope: CoroutineScope,
    drawerState: DrawerState,
) {
    ModalDrawerSheet(
        modifier = Modifier
            .width(325.dp)
            .fillMaxHeight()
            .verticalScroll(rememberScrollState())
    ) {
        NavDrawHeader()
        HorizontalDivider()
        Spacer(Modifier.height(20.dp))
        NavDrawBody(
            navDraweritems = navDraweritems,
            currentRoute = currentRoute
        ) { currentNavigationItem ->
            navController.navigate(currentNavigationItem.route) {
                navController.graph.startDestinationRoute?.let { startDestinationRoute ->
                    popUpTo(startDestinationRoute) {
                        saveState = true
                    }
                }
                launchSingleTop = true
                restoreState = true
                scope.launch { drawerState.close() }
            }
        }
    }
}

@Composable
fun NavDrawHeader() {
    val context = LocalContext.current
    val username = getUsername(context)
    Spacer(modifier = Modifier.height(10.dp))
    Text(
        modifier = Modifier.padding(start = 20.dp),
        text = "NxtVentory",
        style = MaterialTheme.typography.headlineMedium
    )
    Spacer(modifier = Modifier.height(10.dp))
    HorizontalDivider()
    Column(
        modifier = Modifier
            .height(150.dp)
            .padding(20.dp)
    ) {
        Image(
            modifier = Modifier
                .size(75.dp)
                .clip(CircleShape),
            painter = painterResource(id = R.drawable.profile),
            contentDescription = "welcome_logo"
        )
        Spacer(modifier = Modifier.height(10.dp))
        if (username != null) {
            Text(
                text = username.uppercase(),
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}

@Composable
fun NavDrawBody(
    navDraweritems: List<NavigationItem>,
    currentRoute: String?,
    onClick: (NavigationItem) -> Unit,
) {
    navDraweritems.forEachIndexed { index, item ->
        NavigationDrawerItem(
            modifier = Modifier.padding(
                start = 30.dp,
                bottom = 5.dp,
                end = 30.dp
            ),
            label = {
                Text(
                    text = item.title,
                    style = MaterialTheme.typography.titleSmall
                )
            },
            selected = currentRoute == item.route,
            onClick = {
                onClick(item)
            },
            icon = {
                Icon(
                    imageVector = if (currentRoute == item.route) {
                        item.selectedIcon
                    } else {
                        item.unSelectedIcon
                    },
                    contentDescription = item.title
                )
            }
        )
        if (index < navDraweritems.size - 1 && index == 5) {
            Spacer(Modifier.height(20.dp))
            HorizontalDivider()
            Spacer(Modifier.height(20.dp))
        }
    }
}


@Preview(showBackground = true, widthDp = 250, heightDp = 860)
@Composable
fun NavigationDrawerPreview() {
    MaterialTheme(
        typography = myTypography // Applying custom typography here
    ) {
        NavigationDrawer(
            currentRoute = null,
            navController = rememberNavController(),
            scope = rememberCoroutineScope(),
            drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        )
    }
}