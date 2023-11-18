package ae.geekhome.panel.ui.dashboard

import ae.geekhome.panel.coap.impl.DashboardResource
import ae.geekhome.panel.navigation.RouteNavigator
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import eu.automateeverything.tabletsplugin.interop.DashboardItem
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel
@Inject
constructor(
    savedStateHandle: SavedStateHandle,
    private val routeNavigator: RouteNavigator,
    private val dashboardResource: DashboardResource
) : ViewModel(), RouteNavigator by routeNavigator {

    var dashboard: DashboardItem

    fun onButtonClicked(buttonRef: String) {
        dashboardResource.updateButtonRef(buttonRef)
    }

    init {
        val params: String = savedStateHandle[DashboardDestination.ARG_CBOR]!!
        val paramsDecoded = DashboardDestination.decodeNavParams(params)
        dashboard = paramsDecoded.content
    }
}
