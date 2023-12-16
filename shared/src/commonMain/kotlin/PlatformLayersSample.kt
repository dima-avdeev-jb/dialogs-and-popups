import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
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
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties

class Screen(val name: String, val content: @Composable () -> Unit)

val screens = listOf(
    Screen("Popup") {
        PopupSample()
    },
    Screen("Dialog") {
        DialogSample()
    },
    Screen("DropDown") {
        DropdownSample()
    },
    Screen("TextField") {
        var text by remember { mutableStateOf("Hello Compose") }
        repeat(15) {
            TextField(text, { text = it })
        }
    },
)

@Composable
fun PlatformLayersSample() {
    var screen by remember { mutableStateOf<Screen?>(null) }
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    if (screen != null) {
                        Icon(
                            Icons.Default.ArrowBack,
                            null,
                            Modifier.clickable {
                                screen = null
                            }
                        )
                    }
                },
                title = {}
            )
        },
        modifier = Modifier.windowInsetsPadding(WindowInsets.systemBars)
    ) {
        Column(Modifier.fillMaxSize()) {
            Box(Modifier.size(50.dp).background(Color.LightGray))

            val s = screen
            if (s != null) {
                s.content()
            } else {
                LazyColumn {
                    items(screens) {
                        Button({
                            screen = it
                        }) {
                            Text(it.name)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PopupSample() {
    ProvideDensityAndLayoutDirection {
        Popup(
            offset = IntOffset(100, 300),
            onDismissRequest = {
                println("Popup onDismissRequest")
            },
            properties = PopupProperties(

            ),
        ) {
            Column(Modifier.background(Color.Blue.copy(alpha = 0.7f))) {
                Text("I am Popup", color = Color.White)
                Image(
                    imageVector = Icons.Default.Email,
                    contentDescription = null,
                    modifier = Modifier.size(200.dp)
                )
                var counter by remember { mutableStateOf(0) }
                Button({ counter++ }) {
                    Text("Count $counter")
                }
            }
        }
    }

    Box(Modifier.fillMaxSize()) {

    }
}

@Composable
fun DialogSample() {
    ProvideDensityAndLayoutDirection {
        var shown: Boolean by remember { mutableStateOf(true) }
        Button(onClick = {
            shown = true
        }) {
            Text("Open Dialog")
        }
        if (shown) {
            Dialog(
                onDismissRequest = {
                    println("Dialog onDismissRequest")
                    shown = false
                },
                properties = DialogProperties(

                ),
            ) {
                Column(Modifier.background(Color.Blue.copy(alpha = 0.7f))) {
                    Text("I am Dialog", color = Color.White)
                    Image(
                        imageVector = Icons.Default.Email,
                        contentDescription = null,
                        modifier = Modifier.size(200.dp)
                    )
                    var counter by remember { mutableStateOf(0) }
                    Button({ counter++ }) {
                        Text("Count $counter")
                    }
                }
            }
        }
    }
}

@Composable
fun DropdownSample() {
    var expanded by remember { mutableStateOf(false) }
    val items = listOf("A", "B", "C", "D", "E", "F")
    val disabledValue = "B"
    var selectedIndex by remember { mutableStateOf(0) }
    Box(modifier = Modifier.fillMaxSize().wrapContentSize(Alignment.TopStart)) {
        Text(
            items[selectedIndex],
            modifier = Modifier.fillMaxWidth().clickable(onClick = { expanded = true }).background(
                Color.Gray
            )
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.fillMaxWidth().background(
                Color.Red
            )
        ) {
            items.forEachIndexed { index, s ->
                DropdownMenuItem(onClick = {
                    selectedIndex = index
                    expanded = false
                }) {
                    val disabledText = if (s == disabledValue) {
                        " (Disabled)"
                    } else {
                        ""
                    }
                    Text(text = s + disabledText)
                }
            }
        }
    }
}

@Composable
fun ProvideDensityAndLayoutDirection(content: @Composable () -> Unit) {
    var provideDensity by remember { mutableStateOf(false) }
    Column {
        Checkbox(provideDensity, { provideDensity = it })
        Text("provideDensity")
    }

    var provideLayoutDirection by remember { mutableStateOf(true) }
    Column {
        Checkbox(provideLayoutDirection, { provideLayoutDirection = it })
        Text("provideLayoutDirection")
    }

    CompositionLocalProvider(
        *buildList {
            if (provideDensity) {
                add(LocalDensity provides Density(2f, 1f))//todo it doesn't works on Android
            }
            if(provideLayoutDirection) {
                add(LocalLayoutDirection provides LayoutDirection.Rtl)
            }
        }.toTypedArray()
    ) {
        content()
    }
}
