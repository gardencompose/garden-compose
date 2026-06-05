import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.adw.components.HeaderBar
import io.github.gardencompose.gtk.components.Label
import io.github.gardencompose.gtk.components.ListView
import io.github.gardencompose.gtk.components.ScrolledWindow
import io.github.gardencompose.gtk.components.SelectionMode
import io.github.gardencompose.gtk.components.VerticalBox
import io.github.gardencompose.modifier.Modifier
import io.github.gardencompose.modifier.expand

fun main(args: Array<String>) {
    adwApplication("my.example.hello-app", args) {
        ApplicationWindow(
            "Test",
            onClose = ::exitApplication,
            defaultWidth = 800,
            defaultHeight = 800,
        ) {
            VerticalBox {
                HeaderBar(title = { Label(text = "ListView with 10 thousand items") })
                ScrolledWindow(Modifier.expand()) {
                    ListView(
                        items = 10000,
                        selectionMode = SelectionMode.Multiple,
                    ) { index ->
                        Label("Item #$index")
                    }
                }
            }
        }
    }
}
