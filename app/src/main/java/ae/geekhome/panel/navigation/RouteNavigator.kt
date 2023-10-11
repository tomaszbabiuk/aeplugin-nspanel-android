package ae.geekhome.panel.navigation

import androidx.annotation.VisibleForTesting
import java.util.UUID
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

sealed class NavigationState {
    object Idle : NavigationState()

    data class NavigateToRoute(val route: String, val id: String = UUID.randomUUID().toString()) :
        NavigationState()

    data class PopToRoute(val staticRoute: String, val id: String = UUID.randomUUID().toString()) :
        NavigationState()

    data class NavigateUp(val id: String = UUID.randomUUID().toString()) : NavigationState()
}

interface RouteNavigator {
    fun onNavigated(state: NavigationState)
    fun navigateUp()
    fun popToRoute(route: String)
    fun navigateToRoute(route: String)

    val navigationState: StateFlow<NavigationState>
}

class MyRouteNavigator @Inject constructor() : RouteNavigator {

    init {
        println("Init route navigator")
    }

    override val navigationState: MutableStateFlow<NavigationState> =
        MutableStateFlow(NavigationState.Idle)

    override fun onNavigated(state: NavigationState) {
        navigationState.compareAndSet(state, NavigationState.Idle)
    }

    override fun popToRoute(route: String) = navigate(NavigationState.PopToRoute(route))

    override fun navigateUp() = navigate(NavigationState.NavigateUp())

    override fun navigateToRoute(route: String) = navigate(NavigationState.NavigateToRoute(route))

    @VisibleForTesting
    fun navigate(state: NavigationState) {
        navigationState.value = state
    }
}
