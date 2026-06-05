import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.adw.components.HeaderBar
import io.github.gardencompose.gtk.ImageSource
import io.github.gardencompose.gtk.components.IconButton
import io.github.gardencompose.gtk.components.Picture
import io.github.gardencompose.gtk.components.VerticalBox
import io.github.gardencompose.modifier.Modifier
import io.github.gardencompose.modifier.cssClasses
import io.github.gardencompose.modifier.expand
import io.github.gardencompose.useGioResource
import org.gnome.gtk.ContentFit

fun main(args: Array<String>) {
    useGioResource("resources.gresource") {
        adwApplication("my.example.hello-app", args) {
            ApplicationWindow(
                "Lulù",
                onClose = ::exitApplication,
                defaultWidth = 400,
                defaultHeight = 400,
            ) {
                VerticalBox {
                    HeaderBar(
                        startWidgets = {
                            IconButton(
                                // The vector icon is embedded into the gresources file
                                icon = ImageSource.Icon("heart-filled-symbolic"),
                                // The "accent-colored" CSS class is defined in the gresources file
                                modifier = Modifier.cssClasses("accent-colored"),
                                onClick = { println("TODO: pet the dog") },
                            )
                        },
                    )
                    Picture(
                        // The image is embedded into the gresources file
                        ImageSource.forResource("/my/example/hello-app/images/lulu.jpg"),
                        contentFit = ContentFit.COVER,
                        modifier = Modifier.expand(),
                    )
                }
            }
        }
    }
}
