import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.adw.components.HeaderBar
import io.github.gardencompose.adw.components.StatusPage
import io.github.gardencompose.gtk.components.Button
import io.github.gardencompose.gtk.components.RadioButton
import io.github.gardencompose.gtk.components.VerticalBox
import io.github.gardencompose.gtk.components.rememberRadioGroupState
import io.github.gardencompose.modifier.Modifier
import io.github.gardencompose.modifier.cssClasses

private val OPTIONS = listOf("Calls", "Missed", "Friends", "Inconsistent")

fun main(args: Array<String>) {
    adwApplication("my.example.hello-app", args) {
        ApplicationWindow(title = "Radio Buttons", onClose = ::exitApplication) {
            VerticalBox {
                HeaderBar(modifier = Modifier.cssClasses("flat"))
                StatusPage(title = "Radio Buttons", description = "Check buttons grouped to make radio-style buttons") {
                    VerticalBox {
                        var selection: String? by remember { mutableStateOf(null) }
                        val radioState = rememberRadioGroupState()

                        OPTIONS.forEach { option ->
                            RadioButton(
                                state = radioState,
                                active = option == selection,
                                label = option,
                                onSelect = { selection = option },
                                inconsistent = option == selection && option == "Inconsistent",
                            )
                        }

                        Button("Clear", onClick = {
                            selection = null
                        })
                    }
                }
            }
        }
    }
}
