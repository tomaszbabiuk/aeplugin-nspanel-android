package ae.geekhome.panel.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController

@Composable
inline fun <reified VM : ViewModel> createViewModel(navController: NavController): VM {
    val vm = hiltViewModel<VM>()

    if (vm is RouteNavigator) {
        val routeNavigator = vm as RouteNavigator
        val viewStateAsState by routeNavigator.navigationState.collectAsState()
        updateNavigationState(navController, viewStateAsState, routeNavigator::onNavigated)
    }

    return vm
}

fun updateNavigationState(
    navHostController: NavController,
    navigationState: NavigationState,
    onNavigated: (navState: NavigationState) -> Unit,
) {
    when (navigationState) {
        is NavigationState.NavigateToRoute -> {
            navHostController.navigate(navigationState.route)
            onNavigated(navigationState)
        }
        is NavigationState.PopToRoute -> {
            navHostController.popBackStack(navigationState.staticRoute, false)
            onNavigated(navigationState)
        }
        is NavigationState.NavigateUp -> {
            navHostController.navigateUp()
            onNavigated(navigationState)
        }
        is NavigationState.Idle -> {}
    }
}
