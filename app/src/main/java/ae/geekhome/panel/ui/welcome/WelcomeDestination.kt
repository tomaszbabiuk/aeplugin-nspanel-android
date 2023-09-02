package ae.geekhome.panel.ui.welcome

import ae.geekhome.panel.R
import ae.geekhome.panel.navigation.Destination
import ae.geekhome.panel.navigation.createViewModel
import androidx.compose.runtime.Composable
import androidx.navigation.NavController

class WelcomeDestination : Destination() {
    @Composable
    override fun Content(
        navController: NavController,
    ) {
        val vm =
            createViewModel<WelcomeViewModel>(
                navController = navController,
            )
        WelcomeScreen(vm = vm)
    }
    override val titleRes: Int
        get() = R.string.screen_welcome

    companion object {
        fun buildRoute(): String {
            return buildRoutePrefix(WelcomeDestination::class.java)
        }
    }
}