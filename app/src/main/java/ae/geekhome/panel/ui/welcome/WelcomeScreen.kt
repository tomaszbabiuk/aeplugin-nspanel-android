package ae.geekhome.panel.ui.welcome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun WelcomeScreen(vm: WelcomeViewModel) {
    Column {
        Row {
            Text("Address IP4: ")
            Text(vm.ip4Address.toString())
        }
        Row {
            Text("Address IP6: ")
            Text(vm.ip6Address.toString())
        }
        Row {
            Text("Server state: ")
            Text(vm.state.value.toString())
        }
    }
}
