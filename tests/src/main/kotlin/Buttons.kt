import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.adw.components.ButtonContent
import io.github.gardencompose.adw.components.HeaderBar
import io.github.gardencompose.gtk.ImageSource
import io.github.gardencompose.gtk.components.Box
import io.github.gardencompose.gtk.components.Button
import io.github.gardencompose.gtk.components.Label
import io.github.gardencompose.gtk.components.ToggleButton
import io.github.gardencompose.modifier.Modifier
import io.github.gardencompose.modifier.margin
import org.gnome.gtk.Orientation

fun main(args: Array<String>) {
    adwApplication("my.example.hello-app", args) {
        ApplicationWindow("Test", onClose = ::exitApplication) {
            Box(orientation = Orientation.VERTICAL) {
                HeaderBar()

                Box(
                    modifier = Modifier.margin(16),
                    orientation = Orientation.VERTICAL,
                    spacing = 16,
                ) {
                    Button(label = "Button", onClick = { println("Clicked!") })
                    Button(label = "Button (no frame)", onClick = { println("Clicked!") }, hasFrame = false)
                    Button(onClick = { println("Clicked!") }) {
                        Label("Button (custom child)")
                    }
                    Button(onClick = { println("Clicked!") }) {
                        ButtonContent("", icon = ImageSource.Icon("media-playback-start-symbolic"))
                    }
                    Button(onClick = { println("Clicked!") }) {
                        ButtonContent(
                            label = "Button",
                            icon = ImageSource.Icon("media-playback-start-symbolic"),
                        )
                    }
                    var active by remember { mutableStateOf(false) }
                    ToggleButton("Toggle button", active, onToggle = { active = !active })
                    ToggleButton("Toggle button (no frame)", active, hasFrame = false, onToggle = { active = !active })
                    ToggleButton(active, onToggle = { active = !active }) {
                        Label("Toggle button (custom child)")
                    }
                }
            }
        }
    }
}
