package ae.geekhome.panel.ui.welcome

import ae.geekhome.panel.coap.CoapService
import ae.geekhome.panel.navigation.RouteNavigator
import ae.geekhome.panel.ui.dialog.DialogDestination
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.Inet4Address
import java.net.Inet6Address
import javax.inject.Inject
import org.eclipse.californium.elements.util.NetworkInterfacesUtil

@HiltViewModel
class WelcomeViewModel
@Inject
constructor(private val coapService: CoapService, routeNavigator: RouteNavigator) :
    ViewModel(), CoapService.ServerStateChangedListener, RouteNavigator by routeNavigator {
    val ip4Address: Inet4Address = NetworkInterfacesUtil.getMulticastInterfaceIpv4()
    val ip6Address: Inet6Address = NetworkInterfacesUtil.getMulticastInterfaceIpv6()
    val state = mutableStateOf(coapService.state)
    val port = coapService.port

    init {
        coapService.stateListener = this
        state.value = coapService.state
        coapService.start()
    }

    override fun onCleared() {
        coapService.stop()
        coapService.stateListener = null
        super.onCleared()
    }

    override fun onServerStateChanged(state: CoapService.ServerState) {
        this@WelcomeViewModel.state.value = state
    }

    fun onGoToMessage() {
        val title = "title"
        val content = "This is some long message"
        val options =
            arrayOf(
                "option1",
                "option2",
                "option3",
                "option4",
                "option5",
                "option6",
                "option7",
                "option8",
                "option9",
                "option10",
                "option11",
                "option12",
            )
        navigateToRoute(DialogDestination.buildRoute(title, content, options))
    }
}
