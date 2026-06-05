import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.adw.components.HeaderBar
import io.github.gardencompose.adw.components.StatusPage
import io.github.gardencompose.gtk.ImageSource
import io.github.gardencompose.gtk.components.VerticalBox
import io.github.gardencompose.modifier.Modifier
import io.github.gardencompose.modifier.cssClasses
import io.github.gardencompose.modifier.expandVertically

fun main(args: Array<String>) {
    adwApplication("my.example.hello-app", args) {
        ApplicationWindow(
            "StatusPage",
            onClose = ::exitApplication,
            defaultWidth = 800,
            defaultHeight = 900,
        ) {
            VerticalBox {
                HeaderBar()
                StatusPage(
                    title = "Loading",
                    description = "Please wait...",
                    icon = ImageSource.spinner,
                    modifier = Modifier.expandVertically(),
                )
                StatusPage(
                    title = "No Results Found",
                    description = "Try a different search",
                    icon = ImageSource.Icon("system-search-symbolic"),
                    modifier = Modifier
                        .expandVertically()
                        .cssClasses("compact"),
                )
                StatusPage(
                    title = "No Results Found",
                    description = "Try a different search",
                    icon = ImageSource.Icon("system-search-symbolic"),
                    modifier = Modifier
                        .expandVertically(),
                )
            }
        }
    }
}
