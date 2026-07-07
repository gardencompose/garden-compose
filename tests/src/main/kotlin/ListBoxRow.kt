import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.adw.components.HeaderBar
import io.github.gardencompose.gtk.components.Box
import io.github.gardencompose.gtk.components.HorizontalBox
import io.github.gardencompose.gtk.components.Label
import io.github.gardencompose.gtk.components.ListBox
import io.github.gardencompose.gtk.components.ListBoxRow
import io.github.gardencompose.gtk.components.VerticalBox
import io.github.gardencompose.modifier.Modifier
import io.github.gardencompose.modifier.cssClasses
import io.github.gardencompose.modifier.expandHorizontally
import io.github.gardencompose.modifier.margin

fun main(args: Array<String>) {
    adwApplication("my.example.hello-app", args) {
        ApplicationWindow(title = "List Box Row", onClose = ::exitApplication) {
            VerticalBox {
                HeaderBar(modifier = Modifier.cssClasses("flat"))

                HorizontalBox {
                    ListBox(modifier = Modifier.expandHorizontally(true)) {
                        for (i in 0..10) {
                            ListBoxRow {
                                Box(
                                    modifier = Modifier.margin(start = 8),
                                ) {
                                    Label("$i: ListBoxRow")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
