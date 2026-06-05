import androidx.compose.runtime.remember
import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.adw.components.HeaderBar
import io.github.gardencompose.adw.components.ToastOverlay
import io.github.gardencompose.gtk.components.CenterBox
import io.github.gardencompose.gtk.components.DropDown
import io.github.gardencompose.gtk.components.Label
import io.github.gardencompose.gtk.components.VerticalBox
import io.github.gardencompose.gtk.components.rememberSingleSelectionModel
import io.github.gardencompose.modifier.Modifier
import io.github.gardencompose.modifier.alignment
import io.github.gardencompose.modifier.expand
import org.gnome.adw.Toast
import org.gnome.gobject.GObject
import org.gnome.gtk.Align

fun main(args: Array<String>) {
    adwApplication("my.example.hello-app", args) {
        ApplicationWindow(title = "DropDown", onClose = ::exitApplication, defaultWidth = 600, defaultHeight = 400) {
            VerticalBox {
                HeaderBar()
                ToastOverlay {
                    val items = remember {
                        List(100) { index ->
                            DropDownItem("Custom item #$index")
                        }
                    }
                    val model = rememberSingleSelectionModel(items)
                    CenterBox(Modifier.expand()) {
                        DropDown(
                            modifier = Modifier.alignment(Align.CENTER),
                            model = model,
                            item = {
                                Label("Dropdown widget for " + it.name)
                            },
                            selectedItem = {
                                Label(it.name)
                            },
                            onSelectionChanges = {
                                addToast(Toast.builder().setTitle("Selected item ${it.name}").build())
                            },
                        )
                    }
                }
            }
        }
    }
}

private data class DropDownItem(val name: String) : GObject()
