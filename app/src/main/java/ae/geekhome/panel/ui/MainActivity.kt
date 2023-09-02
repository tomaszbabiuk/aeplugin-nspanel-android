package ae.geekhome.panel.ui

import ae.geekhome.panel.coap.CoapService
import ae.geekhome.panel.navigation.NavigationScaffold
import ae.geekhome.panel.ui.theme.AEPanelTheme
import android.os.Bundle
import android.view.Window
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var coapService: CoapService

    override fun onCreate(savedInstanceState: Bundle?) {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.onCreate(savedInstanceState)

        setContent {
            val navController = rememberNavController()
            NavigationScaffold(
                navController = navController,
                destinations = MainActivityNavigationGraph.asGraph(),
                startDestination = MainActivityNavigationGraph.Welcome.destination
            )
        }
    }

    override fun onStart() {
        super.onStart()
        coapService.start()
    }

    override fun onStop() {
        coapService.stop()
        super.onStop()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AEPanelTheme {
        Greeting("Android")
    }
}