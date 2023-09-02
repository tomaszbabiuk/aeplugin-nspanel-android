package ae.geekhome.panel.coap

import android.util.Log
import org.eclipse.californium.core.CoapServer
import org.eclipse.californium.core.coap.CoAP
import org.eclipse.californium.core.network.CoapEndpoint
import org.eclipse.californium.core.server.resources.MyIpResource
import org.eclipse.californium.elements.UDPConnector
import org.eclipse.californium.elements.UdpMulticastConnector
import org.eclipse.californium.elements.config.Configuration
import org.eclipse.californium.elements.util.NetworkInterfacesUtil
import java.net.InetSocketAddress
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Inject


class CaliforniumCoapService @Inject constructor() : CoapService {

    private val executor: Executor = Executors.newSingleThreadExecutor()
    private val port = 5683

    private var server: CoapServer? = null
    private var stop = false

    override fun start() {
        Log.i("coap", "onStartCommand service")
        executor.execute {
            val config: Configuration = Configuration.createStandardWithoutFile()
            val server = CoapServer(config)
            val multicast = NetworkInterfacesUtil.getMulticastInterface()
            if (multicast == null) {
                setupUdp(server, config)
            } else {
                setupUdpIpv4(server, config)
                setupUdpIpv6(server, config)
            }
            server.add(HelloWorldResource())
            server.add(MyIpResource(MyIpResource.RESOURCE_NAME, true))
            startServer(server)
        }
    }

    override fun stop() {
        stopServer()
    }

    @Synchronized
    private fun startServer(server: CoapServer) {
        if (!stop) {
            server.start()
            this.server = server
        }
    }

    @Synchronized
    private fun stopServer() {
        stop = true
        val coapServer = server
        if (coapServer != null) {
            server = null
            executor.execute { coapServer.destroy() }
        }
    }

    private fun setupUdp(server: CoapServer, config: Configuration) {
        val connector = UDPConnector(InetSocketAddress(port), config)
        setupUdp(server, config, connector)
    }

    private fun setupUdpIpv4(server: CoapServer, config: Configuration) {
        val multicast = NetworkInterfacesUtil.getMulticastInterface()
        val address4 = NetworkInterfacesUtil.getMulticastInterfaceIpv4()

        // listen on the same port requires to enable address reuse
        val connector = UDPConnector(InetSocketAddress(address4, port), config)
        connector.reuseAddress = true
        val builder = UdpMulticastConnector.Builder()
        builder.setLocalAddress(CoAP.MULTICAST_IPV4, port)
        builder.setMulticastReceiver(true)
        builder.addMulticastGroup(CoAP.MULTICAST_IPV4, multicast)
        builder.setConfiguration(config)
        val multicastConnector = builder.build()
        connector.addMulticastReceiver(multicastConnector)
        Log.i(
            "coap", "multicast receiver " + CoAP.MULTICAST_IPV4 +
                    " started on " + address4
        )
        setupUdp(server, config, connector)
    }

    private fun setupUdpIpv6(server: CoapServer, config: Configuration) {
        val multicast = NetworkInterfacesUtil.getMulticastInterface()
        val address6 = NetworkInterfacesUtil.getMulticastInterfaceIpv6()

        // listen on the same port requires to enable address reuse
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
            "coap", "multicast receiver " + CoAP.MULTICAST_IPV6_SITELOCAL +
                    " started on " + address6
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