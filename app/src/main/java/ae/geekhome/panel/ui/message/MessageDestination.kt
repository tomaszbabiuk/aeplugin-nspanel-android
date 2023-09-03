package ae.geekhome.panel.ui.message

import ae.geekhome.panel.R
import ae.geekhome.panel.navigation.Destination
import ae.geekhome.panel.navigation.createViewModel
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.navArgument

// import kotlinx.serialization.cbor.Cbor
// import kotlinx.serialization.decodeFromHexString
// import kotlinx.serialization.encodeToHexString

class MessageDestination : Destination() {
    override val arguments: List<NamedNavArgument>
        get() = listOf(navArgument(ARG_CBOR) { type = NavType.StringType })
    @Composable
    override fun Content(
        navController: NavController,
    ) {
        val vm =
            createViewModel<MessageViewModel>(
                navController = navController,
            )
        MessageScreen(vm = vm)
    }
    override val titleRes: Int
        get() = R.string.welcome_title

    data class MessageNavParams(
        val title: String,
        val message: String,
        val options: Array<String>
    ) {
        override fun equals(other: Any?): Boolean {
            if (this === other) return true
            if (javaClass != other?.javaClass) return false

            other as MessageNavParams

            if (title != other.title) return false
            if (message != other.message) return false
            if (!options.contentEquals(other.options)) return false

            return true
        }

        override fun hashCode(): Int {
            var result = title.hashCode()
            result = 31 * result + message.hashCode()
            result = 31 * result + options.contentHashCode()
            return result
        }
    }

    companion object {
        const val ARG_CBOR = "cbor"

        fun buildRoute(title: String, message: String, options: Array<String>): String {
            //            val params = MessageNavParams(title, message, options)
            //            val cborHex = Cbor.encodeToHexString(params)
            //
            //            val prefix = buildRoutePrefix(MessageDestination::class.java)
            //            return "${prefix}?$ARG_CBOR${cborHex}"
            throw error("a")
        }

        fun decodeNavParams(cborHex: String): MessageNavParams {
            // return Cbor.decodeFromHexString(cborHex)
            throw error("a")
        }
    }
}
