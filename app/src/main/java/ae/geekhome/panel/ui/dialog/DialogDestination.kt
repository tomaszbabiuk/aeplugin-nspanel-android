package ae.geekhome.panel.ui.dialog

import ae.geekhome.panel.R
import ae.geekhome.panel.navigation.Destination
import ae.geekhome.panel.navigation.createViewModel
import android.content.Context
import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import kotlinx.serialization.Serializable
import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.decodeFromHexString
import kotlinx.serialization.encodeToHexString

class DialogDestination : Destination() {
    override val arguments: List<NamedNavArgument>
        get() = listOf(navArgument(ARG_CBOR) { type = NavType.StringType })
    @Composable
    override fun Content(
        navController: NavController,
    ) {
        val vm =
            createViewModel<DialogViewModel>(
                navController = navController,
            )
        DialogScreen(vm = vm)
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

    @Serializable
    data class DialogNavParams(
        val title: String,
        val headline: String,
        val options: Array<String>
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as DialogNavParams

            if (title != other.title) return false
            if (headline != other.headline) return false
            if (!options.contentEquals(other.options)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = title.hashCode()
            result = 31 * result + headline.hashCode()
            result = 31 * result + options.contentHashCode()
            return result
        }
    }

    companion object {
        const val ARG_CBOR = "cbor"

        fun buildRoute(title: String, headline: String, options: Array<String>): String {
            val params = DialogNavParams(title, headline, options)
            val cborHex = Cbor.encodeToHexString(params)

            val prefix = buildRoutePrefix(DialogDestination::class.java)
            return "${prefix}?$ARG_CBOR=${cborHex}"
        }

        fun decodeNavParams(cborHex: String): DialogNavParams {
            return Cbor.decodeFromHexString(cborHex)
        }
    }
}
