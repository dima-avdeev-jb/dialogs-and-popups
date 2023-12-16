import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    Column(Modifier.fillMaxSize().windowInsetsPadding(WindowInsets.systemBars)) {
        CompositionLocalProvider(
            LocalDensity provides Density(1f, 1f),//todo it doesn't works
            LocalLayoutDirection provides LayoutDirection.Rtl,
        ) {
            Popup {
                CompositionLocalProvider(
//                    LocalDensity provides Density(1f, 1f),//todo it works
//                    LocalLayoutDirection provides LayoutDirection.Rtl,
                ) {
                    Column(Modifier.background(Color.Blue.copy(alpha = 0.7f))) {
                        Text("I am Popup", color = Color.White)
                        Image(
                            painter = painterResource(res = "compose-multiplatform.xml"),
                            contentDescription = null,
                            modifier = Modifier.size(200.dp)
                        )
                    }
                }
            }
        }
        var text by remember { mutableStateOf("Hello Compose") }
        repeat(15) {
            TextField(text, { text = it })
        }
    }
}
