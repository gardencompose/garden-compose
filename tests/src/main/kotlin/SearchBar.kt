import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.adw.components.HeaderBar
import io.github.gardencompose.gtk.components.Button
import io.github.gardencompose.gtk.components.Label
import io.github.gardencompose.gtk.components.Revealer
import io.github.gardencompose.gtk.components.SearchBar
import io.github.gardencompose.gtk.components.SearchEntry
import io.github.gardencompose.gtk.components.VerticalBox
import io.github.gardencompose.modifier.Modifier
import io.github.gardencompose.modifier.cssClasses
import io.github.gardencompose.modifier.margin
import org.gnome.gtk.RevealerTransitionType

fun main(args: Array<String>) {
    adwApplication("my.example.hello-app", args) {
        ApplicationWindow(title = "Search Bar", onClose = ::exitApplication) {
            VerticalBox {
                HeaderBar(modifier = Modifier.cssClasses("flat"))

                var text by remember { mutableStateOf("") }
                var open by remember { mutableStateOf(false) }

                SearchBar(showCloseButton = true, searchMode = open, onSearchModeChange = { open = it }) {
                    SearchEntry(text = text, onTextChange = { text = it })
                }

                VerticalBox(modifier = Modifier.margin(16), spacing = 16) {
                    Label(text = "Search bar opened: $open")
                    Revealer(
                        reveal = !open,
                        transitionType = RevealerTransitionType.CROSSFADE,
                        transitionDuration = 250,
                    ) {
                        Button(label = "Show search bar", onClick = { open = true })
                    }
                }
            }
        }
    }
}
