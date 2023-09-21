package ae.geekhome.panel.coap.impl

import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.serialization.BinaryFormat
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToByteArray
import org.eclipse.californium.core.CoapResource
import org.eclipse.californium.core.coap.CoAP
import org.eclipse.californium.core.server.resources.CoapExchange

@Serializable data class SceneAction(val sceneId: String, val optionId: String? = null)

@Singleton
class ActiveSceneResource @Inject constructor(private val binaryFormat: BinaryFormat) :
    CoapResource("actions") {
  @Volatile private var resource = SceneAction("welcome")

  init {
    isObservable = true
    attributes.title = "Active scene with selected options"
  }

  override fun handleGET(exchange: CoapExchange) {
    val dataAsCbor = binaryFormat.encodeToByteArray(resource)
    exchange.respond(CoAP.ResponseCode.CHANGED, dataAsCbor)
  }

  fun newAction(sceneId: String, optionId: String? = null) {
    resource = SceneAction(sceneId, optionId)
    changed()
  }
}
