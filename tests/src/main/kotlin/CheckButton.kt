import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.adw.components.HeaderBar
import io.github.gardencompose.adw.components.StatusPage
import io.github.gardencompose.gtk.components.*
import io.github.gardencompose.modifier.Modifier
import io.github.gardencompose.modifier.alignment
import io.github.gardencompose.modifier.cssClasses
import io.github.gardencompose.modifier.sensitive
import org.gnome.gtk.Align

fun main(args: Array<String>) {
    adwApplication("my.example.hello-app", args) {
        ApplicationWindow(title = "Check Button", onClose = ::exitApplication) {
            VerticalBox {
                HeaderBar(modifier = Modifier.cssClasses("flat"))

                StatusPage(
                    title = "Check Button",
                    description = "Allow users to control binary options or properties",
                ) {
                    HorizontalBox {
                        VerticalBox(spacing = 8) {
                            var isChecked by remember { mutableStateOf(false) }

                            CheckButton(
                                onActiveRequest = { active -> isChecked = active },
                                modifier = Modifier.alignment(Align.START),
                                active = isChecked,
                                label = "Change me!",
                            )

                            CheckButton(
                                onActiveRequest = { println("Can't change me!") },
                                modifier = Modifier.alignment(Align.START),
                                active = isChecked,
                                label = "Mirror only",
                            )

                            CheckButton(
                                active = isChecked,
                                onActiveRequest = { println("Nothing happens") },
                                modifier = Modifier.alignment(Align.START),
                                inconsistent = true,
                            )

                            CheckButton(
                                onActiveRequest = { active ->
                                    isChecked = active
                                },
                                modifier = Modifier.alignment(Align.START),
                                active = isChecked,
                            ) {
                                HorizontalBox {
                                    Switch(active = isChecked, onToggle = {})
                                    Label("Custom child")
                                }
                            }

                            CheckButton(
                                active = isChecked,
                                label = "Disabled",
                                onActiveRequest = { println("Can't change me!") },
                                modifier = Modifier.alignment(Align.START).sensitive(false),
                            )
                        }

                        VerticalBox(spacing = 8) {
                            val checkedStates = remember { mutableStateListOf(false, false, false, false) }

                            fun allChecked() = checkedStates.all { it }
                            fun someChecked() = checkedStates.any { it }

                            CheckButton(
                                active = allChecked(),
                                label = "Select all",
                                onActiveRequest = { active ->
                                    val newState = !someChecked()
                                    for (i in checkedStates.indices) {
                                        checkedStates[i] = newState
                                    }
                                },
                                modifier = Modifier.alignment(Align.START),
                                inconsistent = someChecked() && !allChecked(),
                            )

                            checkedStates.forEachIndexed { index, isChecked ->
                                CheckButton(
                                    active = isChecked,
                                    label = "Option ${index + 1}",
                                    onActiveRequest = { active ->
                                        checkedStates[index] = active
                                    },
                                    modifier = Modifier.alignment(Align.START),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
