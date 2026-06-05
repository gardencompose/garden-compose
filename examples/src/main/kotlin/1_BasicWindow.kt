import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.adw.components.HeaderBar
import io.github.gardencompose.adw.components.StatusPage
import io.github.gardencompose.gtk.components.Box
import io.github.gardencompose.gtk.components.Button
import io.github.gardencompose.modifier.Modifier
import io.github.gardencompose.modifier.cssClasses
import org.gnome.gtk.Orientation

fun main(args: Array<String>) {
    adwApplication("my.example.hello-app", args) {
        ApplicationWindow(
            "My first window",
            onClose = ::exitApplication,
        ) {
            Box(orientation = Orientation.VERTICAL) {
                HeaderBar(modifier = Modifier.cssClasses("flat"))
                StatusPage(title = "My first component") {
                    Button(
                        "My first button",
                        onClick = { println("Clicked!") },
                    )
                }
            }
        }
    }
}
