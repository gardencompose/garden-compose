import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.adw.components.HeaderBar
import io.github.gardencompose.gtk.components.*
import io.github.gardencompose.modifier.Modifier
import io.github.gardencompose.modifier.sizeRequest
import org.gnome.gtk.Orientation

fun main(args: Array<String>) {
    adwApplication("my.example.hello-app", args) {
        ApplicationWindow("Test", onClose = ::exitApplication) {
            Box(orientation = Orientation.VERTICAL) {
                HeaderBar()

                var show by remember { mutableStateOf(false) }
                ToggleButton("Show  text", show, {
                    show = !show
                })
                if (show) {
                    Label("A random label that can be hidden", modifier = Modifier.sizeRequest(300, 150))
                }
            }
        }
    }
}
