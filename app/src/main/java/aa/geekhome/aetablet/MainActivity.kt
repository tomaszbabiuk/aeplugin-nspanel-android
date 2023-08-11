package aa.geekhome.aetablet

import aa.geekhome.aetablet.ui.theme.AutomateEverythingTabletTheme
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      AutomateEverythingTabletTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
          Greeting("Android")
        }
      }
    }
  }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
  Text(text = "Hello $name!", modifier = modifier)
}

@Composable
fun Dialog(headline: String, options: Array<String>) {
  Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
    Spacer(modifier = Modifier.weight(1f))
    Text(headline)
    options.forEach { Button(onClick = {}) { Text(it) } }
    Spacer(modifier = Modifier.weight(1f))
  }
}

@Preview(showBackground = true, widthDp = 400, heightDp = 400)
@Composable
fun DialogPreview() {
  AutomateEverythingTabletTheme { Dialog("Lorem ipsum", arrayOf("Option A", "Option B")) }
}
