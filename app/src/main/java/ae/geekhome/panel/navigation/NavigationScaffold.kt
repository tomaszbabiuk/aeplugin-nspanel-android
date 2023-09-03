package ae.geekhome.panel.navigation

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationScaffold(
    navController: NavHostController,
    destinations: List<Destination>,
    startDestination: Destination
) {
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.primary) {
        Scaffold(topBar = { TopBar(navController, destinations) }) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = startDestination.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                destinations.forEach { destination ->
                    composable(route = destination.route, deepLinks = destination.deepLinks) {
                        destination.Content(navController = navController)
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(navController: NavHostController, navigationCommands: List<Destination>) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentNavigationCommand = navigationCommands.find { it.route == currentRoute }

    val hasBackStack = navController.previousBackStackEntry != null

    if (currentNavigationCommand != null) {
        TopAppBar(
            title = {
                Text(
                    text =
                        currentNavigationCommand.composeTitle(
                            LocalContext.current,
                            navBackStackEntry?.arguments
                        )
                )
            },
            navigationIcon = {
                if (hasBackStack) {
                    Icon(
                        Icons.Default.ArrowBack,
                        "",
                        modifier = Modifier.clickable { navController.popBackStack() }
                    )
                }
            }
        )
    }

    BackHandler {
        if (hasBackStack) {
            navController.popBackStack()
        }
    }
}
