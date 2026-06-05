import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.adw.components.HeaderBar
import io.github.gardencompose.adw.components.StatusPage
import io.github.gardencompose.gtk.components.Label
import io.github.gardencompose.gtk.components.Switch
import io.github.gardencompose.gtk.components.VerticalBox
import io.github.gardencompose.modifier.Modifier
import io.github.gardencompose.modifier.alignment
import io.github.gardencompose.modifier.cssClasses
import io.github.gardencompose.modifier.sensitive
import org.gnome.gtk.Align

fun main(args: Array<String>) {
    adwApplication("my.example.hello-app", args) {
        ApplicationWindow(title = "Switch", onClose = ::exitApplication) {
            VerticalBox {
                HeaderBar(modifier = Modifier.cssClasses("flat"))

                StatusPage(title = "Switch", description = "A simple on/off control") {
                    VerticalBox(spacing = 16) {
                        var isSwitchActive by remember { mutableStateOf(true) }

                        VerticalBox(spacing = 8) {
                            Switch(
                                onToggle = { newState ->
                                    isSwitchActive = newState
                                },
                                active = isSwitchActive,
                                modifier = Modifier.alignment(Align.CENTER),
                            )
                            Label(if (isSwitchActive) "On" else "Off")
                        }

                        VerticalBox(spacing = 8) {
                            Switch(
                                active = isSwitchActive,
                                onToggle = { newState ->
                                    println("Doesn't change state")
                                },
                                modifier = Modifier.alignment(Align.CENTER),
                            )
                            Label("Mirror & read-only")
                        }

                        VerticalBox(spacing = 8) {
                            Switch(
                                active = isSwitchActive,
                                onToggle = {},
                                modifier = Modifier
                                    .alignment(Align.CENTER)
                                    .sensitive(false),
                            )
                            Label("Disabled")
                        }
                    }
                }
            }
        }
    }
}
