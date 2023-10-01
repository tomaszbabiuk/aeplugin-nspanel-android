package ae.geekhome.panel.coap.impl

import eu.automateeverything.data.coap.ActiveSceneDto
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.serialization.BinaryFormat
import kotlinx.serialization.encodeToByteArray
import org.eclipse.californium.core.CoapResource
import org.eclipse.californium.core.coap.CoAP
import org.eclipse.californium.core.server.resources.CoapExchange

@Singleton
class ActiveSceneResource @Inject constructor(private val binaryFormat: BinaryFormat) :
    CoapResource("actions") {
    @Volatile private var resource = ActiveSceneDto("welcome")

    init {
        isObservable = true
        attributes.title = "Active scene with selected options"
    }

    override fun handleGET(exchange: CoapExchange) {
        val dataAsCbor = binaryFormat.encodeToByteArray(resource)
        exchange.respond(CoAP.ResponseCode.CHANGED, dataAsCbor)
    }

    fun newAction(sceneId: String, optionId: Int? = null) {
        resource = ActiveSceneDto(sceneId, optionId)
        changed()
    }
}
