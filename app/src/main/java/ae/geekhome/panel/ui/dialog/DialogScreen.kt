package ae.geekhome.panel.ui.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DialogScreen(vm: DialogViewModel) {
    DialogScreen(vm.content, vm.options, vm::onOptionClicked)
}

@Composable
fun DialogScreen(message: String, options: Array<String>, onOptionClick: (Int) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Text(
                message,
                modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            Box {
                Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
                    options.forEachIndexed { index, option ->
                        Button(
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            onClick = { onOptionClick(index) }
                        ) {
                            Text(option)
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 240, heightDp = 240)
@Composable
fun MessageScreenPreview() {
    DialogScreen(
        "message",
        arrayOf("option 1", "option 2", "option 3", "option 4", "option 5", "option 6"),
        {}
    )
}
