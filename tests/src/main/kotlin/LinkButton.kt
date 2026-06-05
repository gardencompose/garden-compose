import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.adw.components.HeaderBar
import io.github.gardencompose.adw.components.StatusPage
import io.github.gardencompose.gtk.components.Box
import io.github.gardencompose.gtk.components.LinkButton
import io.github.gardencompose.gtk.components.VerticalBox
import io.github.gardencompose.modifier.Modifier
import io.github.gardencompose.modifier.alignment
import io.github.gardencompose.modifier.cssClasses
import io.github.gardencompose.modifier.margin
import org.gnome.gtk.Align

fun main(args: Array<String>) {
    adwApplication("my.example.hello-app", args) {
        ApplicationWindow("Test", onClose = ::exitApplication) {
            VerticalBox {
                HeaderBar()

                StatusPage(title = "Link Button", description = "Links to travel the web") {
                    VerticalBox(spacing = 16) {
                        Box(modifier = Modifier.cssClasses("card").alignment(Align.CENTER)) {
                            VerticalBox(modifier = Modifier.margin(16)) {
                                val uri = "https://github.com/garden-compose/garden-compose"
                                LinkButton(
                                    "Link to the GitHub repo",
                                    uri = uri,
                                )
                            }
                        }

                        Box(modifier = Modifier.cssClasses("card").alignment(Align.CENTER)) {
                            VerticalBox(modifier = Modifier.margin(16)) {
                                val uri = "https://docs.gtk.org/gtk4/"
                                LinkButton(
                                    "Link to the GTK documentation",
                                    uri = uri,
                                ) {
                                    println("Visiting $uri")
                                    false
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
