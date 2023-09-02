package ae.geekhome.panel.coap

import org.eclipse.californium.core.CoapResource
import org.eclipse.californium.core.server.resources.CoapExchange

class HelloWorldResource : CoapResource("helloWorld") {
    init {
        attributes.title = "Hello-World Resource"
    }

    override fun handleGET(exchange: CoapExchange) {
        exchange.respond("Hello world")
    }
}