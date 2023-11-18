package ae.geekhome.panel.coap.impl

import ae.geekhome.panel.navigation.RouteNavigator
import ae.geekhome.panel.ui.dashboard.DashboardDestination
import dagger.hilt.android.scopes.ActivityRetainedScoped
import eu.automateeverything.tabletsplugin.interop.DashboardDto
import eu.automateeverything.tabletsplugin.interop.DashboardItem
import javax.inject.Inject
import kotlinx.serialization.BinaryFormat
import kotlinx.serialization.encodeToByteArray
import org.eclipse.californium.core.CoapResource
import org.eclipse.californium.core.coap.CoAP
import org.eclipse.californium.core.server.resources.CoapExchange

@ActivityRetainedScoped
class DashboardResource
@Inject
constructor(private val binaryFormat: BinaryFormat, private val routeNavigator: RouteNavigator) :
    CoapResource("dashboard") {
    @Volatile private var resource = DashboardDto("welcome", 0, DashboardItem(), null)

    val dashboardId: Long
        get() = resource.id

    init {
        isObservable = true
        attributes.title = "Active dashboard data"
    }

    override fun handleGET(exchange: CoapExchange) {
        val dataAsCbor = binaryFormat.encodeToByteArray(resource)
        exchange.respond(CoAP.ResponseCode.CONTENT, dataAsCbor)
    }

    override fun handlePUT(exchange: CoapExchange?) {
        val payload = exchange!!.requestPayload
        resource = binaryFormat.decodeFromByteArray(DashboardDto.serializer(), payload)

        routeNavigator.navigateToRoute(DashboardDestination.buildRoute(payload))

        exchange.respond(CoAP.ResponseCode.CHANGED)
        changed()
    }

    fun updateButtonRef(buttonRef: String) {
        resource = DashboardDto(resource.title, resource.id, resource.content, buttonRef)
        changed()
    }
}
