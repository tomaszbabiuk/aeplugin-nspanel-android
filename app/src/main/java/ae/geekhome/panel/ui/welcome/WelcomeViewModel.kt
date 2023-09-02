package ae.geekhome.panel.ui.welcome

import ae.geekhome.panel.coap.CoapService
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import java.net.Inet4Address
import java.net.Inet6Address
import javax.inject.Inject
import org.eclipse.californium.elements.util.NetworkInterfacesUtil

@HiltViewModel
class WelcomeViewModel @Inject constructor(private val coapService: CoapService) :
    ViewModel(), CoapService.ServerStateChangedListener {
    val ip4Address: Inet4Address = NetworkInterfacesUtil.getMulticastInterfaceIpv4()
    val ip6Address: Inet6Address = NetworkInterfacesUtil.getMulticastInterfaceIpv6()
    val state = mutableStateOf(coapService.state)

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
}
