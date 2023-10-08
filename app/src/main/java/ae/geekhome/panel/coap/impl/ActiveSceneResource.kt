package ae.geekhome.panel.coap.impl

import ae.geekhome.panel.navigation.RouteNavigator
import ae.geekhome.panel.ui.dialog.DialogDestination
import dagger.hilt.android.scopes.ActivityRetainedScoped
import eu.automateeverything.tabletsplugin.interop.ActiveSceneDto
import javax.inject.Inject
import kotlinx.serialization.BinaryFormat
import kotlinx.serialization.encodeToByteArray
import org.eclipse.californium.core.CoapResource
import org.eclipse.californium.core.coap.CoAP
import org.eclipse.californium.core.server.resources.CoapExchange

@ActivityRetainedScoped
class ActiveSceneResource
@Inject
constructor(private val binaryFormat: BinaryFormat, private val routeNavigator: RouteNavigator) :
    CoapResource("activescene") {
    @Volatile private var resource = ActiveSceneDto("welcome", null, null, null)

    init {
        isObservable = true
        attributes.title = "Active scene with selected options"
    }

    override fun handleGET(exchange: CoapExchange) {
        val dataAsCbor = binaryFormat.encodeToByteArray(resource)
        exchange.respond(CoAP.ResponseCode.CHANGED, dataAsCbor)
    }

    override fun handlePUT(exchange: CoapExchange?) {
        resource =
            binaryFormat.decodeFromByteArray(ActiveSceneDto.serializer(), exchange!!.requestPayload)

        if (resource.dialog != null) {
            routeNavigator.navigateToRoute(
                DialogDestination.buildRoute(
                    resource.dialog!!.title,
                    resource.dialog!!.headline,
                    resource.dialog!!.options
                )
            )
        }

        exchange.respond(CoAP.ResponseCode.CHANGED)
    }

    fun newAction(sceneId: String, optionId: Int? = null) {
        resource = ActiveSceneDto(sceneId, optionId, null, null)
        changed()
    }
}
