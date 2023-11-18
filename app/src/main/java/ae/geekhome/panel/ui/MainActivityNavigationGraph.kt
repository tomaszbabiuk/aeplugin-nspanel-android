package ae.geekhome.panel.ui

import ae.geekhome.panel.navigation.Destination
import ae.geekhome.panel.ui.dashboard.DashboardDestination
import ae.geekhome.panel.ui.welcome.WelcomeDestination

enum class MainActivityNavigationGraph(val destination: Destination) {
    Welcome(WelcomeDestination()),
    Message(DashboardDestination());

    companion object {
        fun asGraph(): List<Destination> {
            return values().map { it.destination }
        }
    }
}
