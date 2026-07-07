import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.adw.components.HeaderBar
import io.github.gardencompose.gtk.components.Box
import io.github.gardencompose.gtk.components.Button
import io.github.gardencompose.gtk.components.ControlledListBox
import io.github.gardencompose.gtk.components.Label
import io.github.gardencompose.gtk.components.ListBoxRow
import io.github.gardencompose.gtk.components.ScrolledWindow
import io.github.gardencompose.gtk.components.ToggleButton
import io.github.gardencompose.gtk.components.VerticalBox
import io.github.gardencompose.modifier.Modifier
import io.github.gardencompose.modifier.cssClasses
import io.github.gardencompose.modifier.expand
import io.github.gardencompose.modifier.expandHorizontally
import io.github.gardencompose.modifier.margin

fun main(args: Array<String>) {
    adwApplication("my.example.hello-app", args) {
        ApplicationWindow(
            title = "Controlled List Box",
            onClose = ::exitApplication,
            defaultWidth = 800,
            defaultHeight = 800,
        ) {
            VerticalBox {
                HeaderBar(modifier = Modifier.cssClasses("flat"))

                var selectionEnabled by remember { mutableStateOf(true) }

                var selectedRow by remember { mutableStateOf<Int?>(null) }
                val selectedRows = remember { mutableStateSetOf<Int>() }

                Box {
                    Panel(title = "Single") {
                        ControlledListBox(
                            selectedRow = selectedRow,
                            onSelectRow = {
                                if (selectionEnabled) {
                                    println("Selected row: $it")
                                    selectedRow = it
                                }
                            },
                        ) {
                            Rows()
                        }
                    }
                    Panel(title = "Multiple") {
                        ControlledListBox(
                            selectedRows = selectedRows,
                            onSelectRows = {
                                if (selectionEnabled) {
                                    println("Selected rows: $it")
                                    selectedRows.clear()
                                    selectedRows.addAll(it)
                                }
                            },
                        ) {
                            Rows()
                        }
                    }
                    Panel(title = "None") {
                        ControlledListBox {
                            Rows()
                        }
                    }
                }
                VerticalBox(modifier = Modifier.margin(8), spacing = 8) {
                    ToggleButton(label = "Toggle Selection", active = selectionEnabled, onToggle = {
                        selectionEnabled = !selectionEnabled
                    })
                    Button(label = "Unselect Everything", onClick = {
                        selectedRows.clear()
                        selectedRow = null
                    })
                }
            }
        }
    }
}

@Composable
private fun Rows() {
    for (i in 0..10) {
        ListBoxRow {
            Box(
                modifier = Modifier.margin(start = 8).expandHorizontally(true),
            ) {
                Label("Row $i")
            }
        }
    }
}

@Composable
private fun Panel(title: String, content: @Composable () -> Unit) {
    VerticalBox(Modifier.expand()) {
        HeaderBar(title = { Label(title) }, showEndTitleButtons = false, showStartTitleButtons = false)
        ScrolledWindow(Modifier.expand()) {
            content()
        }
    }
}
