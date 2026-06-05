import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.adw.components.HeaderBar
import io.github.gardencompose.gtk.components.Label
import io.github.gardencompose.gtk.components.ScrolledWindow
import io.github.gardencompose.gtk.components.TextView
import io.github.gardencompose.gtk.components.VerticalBox
import io.github.gardencompose.modifier.Modifier
import io.github.gardencompose.modifier.expand

const val MAX_CHARS = 64

fun main(args: Array<String>) {
    adwApplication("my.example.hello-app", args) {
        ApplicationWindow(title = "Text", onClose = ::exitApplication) {
            VerticalBox {
                HeaderBar()

                var text by remember { mutableStateOf("") }

                Label(text = "${text.length} / $MAX_CHARS")

                ScrolledWindow(modifier = Modifier.expand()) {
                    TextView(
                        text = text,
                        modifier = Modifier.expand(),
                        onTextChange = { if (it.length <= MAX_CHARS) text = it },
                    )
                }
            }
        }
    }
}
