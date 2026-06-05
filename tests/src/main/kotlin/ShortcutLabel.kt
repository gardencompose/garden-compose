import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import io.github.gardencompose.LocalApplication
import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.adw.components.HeaderBar
import io.github.gardencompose.adw.components.ShortcutLabel
import io.github.gardencompose.gtk.components.HorizontalBox
import io.github.gardencompose.gtk.components.Label
import io.github.gardencompose.gtk.components.VerticalBox
import io.github.gardencompose.modifier.Modifier
import io.github.gardencompose.modifier.margin
import org.gnome.gio.SimpleAction
import org.gnome.glib.Variant
import org.gnome.glib.VariantType

fun main(args: Array<String>) {
    adwApplication("my.example.hello-app", args) {
        ApplicationWindow(title = "Shortcut Label", onClose = ::exitApplication) {
            VerticalBox {
                HeaderBar()

                val colors = arrayOf("purple", "red", "green", "blue")
                var selectedColor by remember { mutableIntStateOf(0) }

                val accel = "<Control><Shift>H"
                Shortcut(
                    "change-text-color",
                    listOf(accel),
                    { selectedColor = (selectedColor + 1) % colors.size },
                )

                VerticalBox(
                    modifier = Modifier.margin(16),
                    spacing = 16,
                ) {
                    HorizontalBox(spacing = 16) {
                        ShortcutLabel(accel, "Not set")
                        Label(
                            text = "<span foreground=\"${colors[selectedColor]}\">Change text color</span>",
                            useMarkup = true,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Shortcut(
    name: String,
    accelerators: List<String>,
    onActivate: () -> Unit,
) {
    val application = LocalApplication.current
    val currentOnActivate by rememberUpdatedState(onActivate)

    DisposableEffect(name, accelerators) {
        val action = SimpleAction(name, null as VariantType?)
        val connection = action.onActivate { _: Variant? -> currentOnActivate() }
        application.addAction(action)
        application.setAccelsForAction("app.$name", accelerators.toTypedArray())
        onDispose {
            connection.disconnect()
            application.removeAction(name)
            application.setAccelsForAction("app.$name", emptyArray())
        }
    }
}
