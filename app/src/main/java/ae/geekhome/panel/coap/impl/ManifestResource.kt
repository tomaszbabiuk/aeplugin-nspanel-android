package ae.geekhome.panel.coap.impl

import eu.automateeverything.data.coap.VersionManifestDto
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.serialization.BinaryFormat
import kotlinx.serialization.encodeToByteArray
import org.eclipse.californium.core.CoapResource
import org.eclipse.californium.core.coap.CoAP
import org.eclipse.californium.core.server.resources.CoapExchange

@Singleton
class ManifestResource @Inject constructor(private val binaryFormat: BinaryFormat) :
    CoapResource("automateeverything") {
  @Volatile private var resource = VersionManifestDto(1, 0, "temporary_id_todo")

  init {
    attributes.title =
        "Discovery descriptor to confirm that this is a tablet control for Automate Everything"
    attributes.resourceTypes
  }

  override fun handleGET(exchange: CoapExchange) {
    val dataAsCbor = binaryFormat.encodeToByteArray(resource)
    exchange.respond(CoAP.ResponseCode.CONTENT, dataAsCbor)
  }
}
