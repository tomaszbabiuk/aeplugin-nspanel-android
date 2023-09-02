package ae.geekhome.panel.ui

import ae.geekhome.panel.navigation.Destination
import ae.geekhome.panel.ui.welcome.WelcomeDestination

enum class MainActivityNavigationGraph(val destination: Destination) {
    Welcome(WelcomeDestination());

    companion object {
        fun asGraph(): List<Destination> {
            return values().map { it.destination }
        }
    }
}