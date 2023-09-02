package ae.geekhome.panel.coap

import org.eclipse.californium.core.CoapResource
import org.eclipse.californium.core.coap.CoAP
import org.eclipse.californium.core.server.resources.CoapExchange

class PubSubResource : CoapResource("pub") {
    @Volatile
    private var resource = "initial value"

    init {
        isObservable = true
        attributes.title = "pub-sub Resource"
    }

    override fun handleGET(exchange: CoapExchange) {
        exchange.respond(resource)
    }

    override fun handlePOST(exchange: CoapExchange) {
        resource = exchange.requestText
        exchange.respond(CoAP.ResponseCode.CHANGED)
        changed()
    }
}