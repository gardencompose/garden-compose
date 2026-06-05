import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.adw.components.HeaderBar
import io.github.gardencompose.adw.components.StatusPage
import io.github.gardencompose.gtk.components.HorizontalBox
import io.github.gardencompose.gtk.components.VerticalBox
import io.github.gardencompose.modifier.Modifier
import io.github.gardencompose.modifier.alignment
import io.github.gardencompose.modifier.cssClasses
import io.github.gardencompose.modifier.expand
import io.github.gardencompose.modifier.sizeRequest
import org.gnome.gtk.Align
import io.github.gardencompose.adw.components.Spinner as AdwSpinner
import io.github.gardencompose.gtk.components.Spinner as GtkSpinner

fun main(args: Array<String>) {
    adwApplication("my.example.hello-app", args) {
        ApplicationWindow(title = "Spinner", onClose = ::exitApplication) {
            VerticalBox {
                HeaderBar(modifier = Modifier.cssClasses("flat"))

                StatusPage(title = "Spinner", description = "Useful to display a loading state") {
                    HorizontalBox(modifier = Modifier.expand().alignment(Align.CENTER), spacing = 8) {
                        AdwSpinner(modifier = Modifier.sizeRequest(48, 48))
                        GtkSpinner(modifier = Modifier.sizeRequest(48, 48), spinning = true)
                    }
                }
            }
        }
    }
}
