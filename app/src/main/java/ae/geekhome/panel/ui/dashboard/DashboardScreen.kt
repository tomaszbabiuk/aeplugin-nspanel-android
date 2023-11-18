package ae.geekhome.panel.ui.dashboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import eu.automateeverything.tabletsplugin.interop.DashboardItem

@Composable
fun DashboardScreen(vm: DashboardViewModel) {
    DashboardScreen(vm.dashboard, vm::onButtonClicked)
}

@Composable
fun DashboardScreen(dashboard: DashboardItem, onButtonClick: (String) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        if (dashboard.singleColumn != null) {
            Column {
                dashboard.singleColumn!!.children.forEach {
                    if (it.text != null) {
                        Text(
                            it.text!!.text,
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }

                    if (it.headline != null) {
                        Text(
                            it.headline!!.text,
                            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                            style = MaterialTheme.typography.headlineLarge,
                            textAlign = TextAlign.Center
                        )
                    }

                    if (it.button != null) {
                        Button(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            onClick = { onButtonClick(it.button!!.ref) }
                        ) {
                            Text(it.button!!.text)
                        }
                    }
                }
            }
        }
    }
}

// @Preview(showBackground = true, widthDp = 240, heightDp = 240)
// @Composable
// fun MessageScreenPreview() {
//    DashboardScreen(
//        "message",
//        arrayOf("option 1", "option 2", "option 3", "option 4", "option 5", "option 6")
//    ) {}
// }
