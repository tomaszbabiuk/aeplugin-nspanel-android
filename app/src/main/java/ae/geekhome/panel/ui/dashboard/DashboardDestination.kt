package ae.geekhome.panel.ui.dashboard

import ae.geekhome.panel.R
import ae.geekhome.panel.navigation.Destination
import ae.geekhome.panel.navigation.createViewModel
import ae.geekhome.panel.utils.toHex
import android.content.Context
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import eu.automateeverything.tabletsplugin.interop.DashboardDto
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.decodeFromHexString

class DashboardDestination : Destination() {
    override val arguments: List<NamedNavArgument>
        get() = listOf(navArgument(ARG_CBOR) { type = NavType.StringType })
    @Composable
    override fun Content(
        navController: NavController,
    ) {
        val vm =
            createViewModel<DashboardViewModel>(
                navController = navController,
            )
        DashboardScreen(vm = vm)
    }
    override val titleRes: Int
        get() = R.string.dialog_title

    override fun composeTitle(context: Context, arguments: Bundle?): String {
        val params = arguments?.getString(ARG_CBOR)
        if (params != null) {
            val decoded = decodeNavParams(params)
            return decoded.title
        }

        return super.composeTitle(context, arguments)
    }

    companion object {
        const val ARG_CBOR = "cbor"

        fun buildRoute(dashboardDtoCBOR: ByteArray): String {
            val prefix = buildRoutePrefix(DashboardDestination::class.java)
            return "${prefix}?$ARG_CBOR=${dashboardDtoCBOR.toHex()}"
        }

        fun decodeNavParams(cborHex: String): DashboardDto {
            return Cbor.decodeFromHexString(cborHex)
        }
    }
}
