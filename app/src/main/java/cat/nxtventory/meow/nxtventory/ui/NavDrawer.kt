package cat.nxtventory.meow.nxtventory.ui

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import cat.nxtventory.R
import cat.nxtventory.meow.firebase.UserDataManager
import cat.nxtventory.meow.nxtventory.data.NavigationItem
import cat.nxtventory.meow.nxtventory.data.navDraweritems
import cat.nxtventory.ui.theme.NxtVentoryTheme
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

    var userDetails by remember { mutableStateOf<Map<String, Any>?>(null) }
    LaunchedEffect(context) {
        userDetails = UserDataManager.getUserDetails(context)
    }

    Column(
        modifier = Modifier
            .padding(start = 20.dp, top = 20.dp, bottom = 10.dp, end = 20.dp)
    ) {
        Text(
            text = "NxtVentory",
            style = MaterialTheme.typography.headlineMedium
        )
    }
    if (userDetails == null) {
        LoadingUserDetail()
    } else {
        HorizontalDivider()
        NavUserDetail(userDetails)
    }
}

@Composable
fun LoadingUserDetail() {
    Column(modifier = Modifier.fillMaxSize()) {
        Row(
            modifier = Modifier
                .height(150.dp)
        ) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }
    }
}

@Composable
fun NavUserDetail(userDetails: Map<String, Any>?) {
    Row(
        modifier = Modifier
            .height(150.dp)
            .padding(top = 30.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(5.dp))
        Column(
            modifier = Modifier.fillMaxHeight(),

            ) {
            Image(
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape),
                painter = painterResource(id = R.drawable.profile),
                contentDescription = "welcome_logo"
            )
            Spacer(modifier = Modifier.height(10.dp))
            userDetails?.let { user ->
                Text(
                    text = user["username"].toString().uppercase(),
                    style = MaterialTheme.typography.labelMedium
                )
            }
        }
        Spacer(modifier = Modifier.width(30.dp))
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            NameUserDetailText(userDetails = userDetails, detail = "name")
            Spacer(modifier = Modifier.height(5.dp))
            UserDetailText(userDetails = userDetails, detail = "store")
            Spacer(modifier = Modifier.height(5.dp))
            UserDetailText(userDetails = userDetails, detail = "role")
        }
    }
}

@Composable
fun NameUserDetailText(userDetails: Map<String, Any>?, detail: String) {
    userDetails?.let { user ->
        Text(
            text = user[detail].toString().replaceFirstChar { it.uppercaseChar() },
            style = MaterialTheme.typography.displaySmall
        )
    }
}

@Composable
fun UserDetailText(userDetails: Map<String, Any>?, detail: String) {
    userDetails?.let { user ->
        Text(
            text = "${detail.replaceFirstChar { it.uppercaseChar() }} : ${user[detail].toString().replaceFirstChar { it.uppercaseChar() }}",
            style = MaterialTheme.typography.labelLarge
        )
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

@Preview(showSystemUi = true, uiMode = UI_MODE_NIGHT_YES)
@Preview(showSystemUi = true)
@Composable
private fun UniveralPreview() {
    NxtVentoryTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            NavigationDrawer(
                currentRoute = null,
                navController = rememberNavController(),
                scope = rememberCoroutineScope(),
                drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DetailScreenPreview() {
    NxtVentoryTheme {
        Row(
            modifier = Modifier
                .height(150.dp)
                .padding(top = 30.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxHeight(),
            ) {
                Image(
                    modifier = Modifier
                        .size(75.dp)
                        .clip(CircleShape),
                    painter = painterResource(id = R.drawable.profile),
                    contentDescription = "welcome_logo"
                )
                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    text = "NXTGENCAT",
                    style = MaterialTheme.typography.labelMedium
                )
            }
            Spacer(modifier = Modifier.width(30.dp))
            Column(
                modifier = Modifier.fillMaxSize(),
            ) {
                Text(
                    text = "Sushanth",
                    style = MaterialTheme.typography.displaySmall
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Sri Surya Super Market",
                    style = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = "Role : Manager",
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}