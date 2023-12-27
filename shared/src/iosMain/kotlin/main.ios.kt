import androidx.compose.runtime.ExperimentalComposeApi
import androidx.compose.ui.window.ComposeUIViewController

@OptIn(ExperimentalComposeApi::class)
fun MainViewController() = ComposeUIViewController(
    configure = {
        // By default platformLayers = false - it means stable behavior
        platformLayers = true
    }
) { App() }
