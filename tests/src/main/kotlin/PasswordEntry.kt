import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.adw.components.HeaderBar
import io.github.gardencompose.gtk.components.Button
import io.github.gardencompose.gtk.components.Entry
import io.github.gardencompose.gtk.components.ControlledPasswordEntry
import io.github.gardencompose.gtk.components.Frame
import io.github.gardencompose.gtk.components.PasswordEntry
import io.github.gardencompose.gtk.components.VerticalBox
import io.github.gardencompose.modifier.Modifier
import io.github.gardencompose.modifier.cssClasses
import io.github.gardencompose.modifier.margin

fun main(args: Array<String>) {
    adwApplication("my.example.hello-app", args) {
        ApplicationWindow(title = "Password Entry", onClose = ::exitApplication) {
            VerticalBox {
                HeaderBar(modifier = Modifier.cssClasses("flat"))

                var username by remember { mutableStateOf("") }
                var password by remember { mutableStateOf("") }
                var visibility by remember { mutableStateOf(false) }

                fun login() {
                    if (username == "I love" && password == "Garden Compose") {
                        exitApplication()
                    }
                }

                VerticalBox(modifier = Modifier.margin(8), spacing = 8) {
                    Entry(text = username, onTextChange = { username = it }, placeholderText = "Username")
                    PasswordEntry(
                        text = password,
                        onTextChange = { password = it },
                        onActivate = { login() },
                        placeholderText = "Password",
                        showPeekIcon = true,
                    )
                    Frame {
                        VerticalBox(modifier = Modifier.margin(8), spacing = 8) {
                            ControlledPasswordEntry(
                                text = password,
                                onTextChange = { password = it },
                                onActivate = { login() },
                                placeholderText = "Password (Controlled)",
                                showPeekIcon = true,
                                onVisibilityChange = { },
                                visibility = visibility,
                            )
                            Button(label = "Switch visibility", onClick = { visibility = !visibility })
                        }
                    }
                    Button(label = "Login", onClick = { login() })
                }
            }
        }
    }
}
