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
import androidx.compose.ui.text.style.TextAlign

@Composable
fun WelcomeScreen(vm: WelcomeViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.align(Alignment.TopCenter)) {
            StatusRow(stringResource(id = R.string.welcome_ip4_address), vm.ip4Address.toString())
            StatusRow(stringResource(id = R.string.welcome_ip6_address), vm.ip6Address.toString())
            StatusRow(stringResource(id = R.string.welcome_port), vm.port.toString())
            StatusRow(stringResource(id = R.string.welcome_server_state), vm.state.value.toString())
        }
    }
}

@Composable
fun StatusRow(left: String, right: String) {
    Row {
        Text(left, modifier = Modifier.weight(1f), textAlign = TextAlign.End)
        Text(right, modifier = Modifier.weight(1.5f))
    }
}
