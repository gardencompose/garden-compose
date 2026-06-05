import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.adw.components.HeaderBar
import io.github.gardencompose.gtk.ImageSource
import io.github.gardencompose.gtk.components.Entry
import io.github.gardencompose.gtk.components.Label
import io.github.gardencompose.gtk.components.ToggleButton
import io.github.gardencompose.gtk.components.VerticalBox
import io.github.gardencompose.modifier.Modifier
import io.github.gardencompose.modifier.cssClasses
import io.github.gardencompose.modifier.margin
import io.github.gardencompose.useGioResource

fun main(args: Array<String>) {
    useGioResource("resources.gresource") {
        adwApplication("my.example.hello-app", args) {
            ApplicationWindow(title = "Entry", onClose = ::exitApplication) {
                VerticalBox(
                    spacing = 8,
                    modifier = Modifier.margin(8),
                ) {
                    HeaderBar(modifier = Modifier.cssClasses("flat"))

                    var tempInfo by remember { mutableStateOf("") }
                    var info by remember { mutableStateOf("") }
                    var visible by remember { mutableStateOf(true) }

                    Label(text = "Saved info: $info")
                    Label(text = "Press enter to save", modifier = Modifier.cssClasses("dimmed"))

                    Entry(
                        text = tempInfo,
                        onTextChange = { tempInfo = it },
                        onActivate = { info = tempInfo },
                        primaryIcon = if (visible) ImageSource.Icon("heart-filled-symbolic") else null,
                        onPrimaryIconPress = { println("Liked \"$tempInfo\"!") },
                        secondaryIcon = if (visible) ImageSource.Icon("system-search-symbolic") else null,
                        onSecondaryIconPress = { println("Searching for \"$tempInfo\"...") },
                    )

                    ToggleButton(active = visible, label = "Toggle icons", onToggle = { visible = !visible })
                }
            }
        }
    }
}
