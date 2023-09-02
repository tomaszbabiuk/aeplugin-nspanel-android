package ae.geekhome.panel.ui.welcome

import ae.geekhome.panel.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
fun WelcomeScreen(vm: WelcomeViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        Row(modifier = Modifier.align(Alignment.TopCenter)) {
            Column {
                Text(stringResource(id = R.string.welcome_ip4_address))
                Text(stringResource(id = R.string.welcome_ip6_address))
                Text(stringResource(id = R.string.welcome_server_state))
            }

            Column {
                Text(vm.ip4Address.toString())
                Text(vm.ip6Address.toString())
                Text(vm.state.value.toString())
            }
        }
    }
}
