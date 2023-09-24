package ae.geekhome.panel.coap.impl

import ae.geekhome.panel.coap.CoapService
import android.util.Log
import java.net.InetSocketAddress
import javax.inject.Inject
import javax.inject.Singleton
import org.eclipse.californium.core.CoapResource
import org.eclipse.californium.core.CoapServer
import org.eclipse.californium.core.coap.CoAP
import org.eclipse.californium.core.network.CoapEndpoint
import org.eclipse.californium.core.server.resources.MyIpResource
import org.eclipse.californium.elements.UDPConnector
import org.eclipse.californium.elements.UdpMulticastConnector
import org.eclipse.californium.elements.config.Configuration
import org.eclipse.californium.elements.util.NetworkInterfacesUtil

@Singleton
class CaliforniumCoapService @Inject constructor(private vararg val resources: CoapResource) :
    CoapService {
    override val port = 5683
    private var server: CoapServer? = null

    @Volatile override var state = CoapService.ServerState.Stopped

    override var stateListener: CoapService.ServerStateChangedListener? = null

    private suspend fun changeState(newState: CoapService.ServerState) {
        state = newState
        stateListener?.onServerStateChanged(newState)
    }
    override suspend fun start() {
        if (state == CoapService.ServerState.Stopped) {
            changeState(CoapService.ServerState.Starting)
            val config: Configuration = Configuration.createStandardWithoutFile()
            val server = CoapServer(config)
            val multicast = NetworkInterfacesUtil.getMulticastInterface()
            if (multicast == null) {
                setupUdp(server, config)
            } else {
                setupUdpIpv4(server, config)
                setupUdpIpv6(server, config)
            }
            resources.forEach { server.add(it) }
            server.add(MyIpResource(MyIpResource.RESOURCE_NAME, true))
            startServer(server)
        }
    }

    override suspend fun stop() {
        stopServer()
    }

    private suspend fun startServer(server: CoapServer) {
        server.start()
        this.server = server
        changeState(CoapService.ServerState.Listening)
    }

    private suspend fun stopServer() {
        if (state != CoapService.ServerState.Stopped) {
            changeState(CoapService.ServerState.Stopping)
            val coapServer = server
            if (coapServer != null) {
                server = null
                //                executor.execute { coapServer.destroy() }
                coapServer.destroy()
            }

            changeState(CoapService.ServerState.Stopped)
        }
    }

    private fun setupUdp(server: CoapServer, config: Configuration) {
        val connector = UDPConnector(InetSocketAddress(port), config)
        setupUdp(server, config, connector)
    }

    private fun setupUdpIpv4(server: CoapServer, config: Configuration) {
        val multicast = NetworkInterfacesUtil.getMulticastInterface()
        val address4 = NetworkInterfacesUtil.getMulticastInterfaceIpv4()

        val connector = UDPConnector(InetSocketAddress(address4, port), config)
        connector.reuseAddress = true
        val builder = UdpMulticastConnector.Builder()
        builder.setLocalAddress(CoAP.MULTICAST_IPV4, port)
        builder.setMulticastReceiver(true)
        builder.addMulticastGroup(CoAP.MULTICAST_IPV4, multicast)
        builder.setConfiguration(config)
        val multicastConnector = builder.build()
        connector.addMulticastReceiver(multicastConnector)
        Log.i("coap", "multicast receiver " + CoAP.MULTICAST_IPV4 + " started on " + address4)
        setupUdp(server, config, connector)
    }

    private fun setupUdpIpv6(server: CoapServer, config: Configuration) {
        val multicast = NetworkInterfacesUtil.getMulticastInterface()
        val address6 = NetworkInterfacesUtil.getMulticastInterfaceIpv6()

        val connector = UDPConnector(InetSocketAddress(address6, port), config)
        connector.reuseAddress = true
        val builder = UdpMulticastConnector.Builder()
        builder.setLocalAddress(CoAP.MULTICAST_IPV6_SITELOCAL, port)
        builder.setMulticastReceiver(true)
        builder.addMulticastGroup(CoAP.MULTICAST_IPV6_SITELOCAL, multicast)
        builder.setConfiguration(config)
        val multicastConnector = builder.build()
        connector.addMulticastReceiver(multicastConnector)
        Log.i(
            "coap",
            "multicast receiver " + CoAP.MULTICAST_IPV6_SITELOCAL + " started on " + address6
        )
        setupUdp(server, config, connector)
    }

    private fun setupUdp(server: CoapServer, config: Configuration, connector: UDPConnector) {
        val builder = CoapEndpoint.Builder()
        builder.setConfiguration(config)
        builder.setConnector(connector)
        server.addEndpoint(builder.build())
    }
}
