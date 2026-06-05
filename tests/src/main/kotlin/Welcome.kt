import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.gtk.ImageSource
import io.github.gardencompose.gtk.components.*
import io.github.gardencompose.modifier.Modifier
import io.github.gardencompose.modifier.alignment
import io.github.gardencompose.modifier.cssClasses
import io.github.gardencompose.modifier.margin
import io.github.gardencompose.useGioResource
import org.gnome.gtk.Align
import org.gnome.gtk.Orientation
import org.gnome.gtk.PackType

fun main(args: Array<String>) {
    useGioResource("resources.gresource") {
        adwApplication("my.example.hello-app", args) {
            ApplicationWindow(
                "compose-4-gtk",
                onClose = ::exitApplication,
                defaultWidth = 600,
                defaultHeight = 400,
            ) {
                Overlay(
                    mainChild = {
                        CenterBox(orientation = Orientation.VERTICAL) {
                            VerticalBox(Modifier.margin(16)) {
                                Image(
                                    image = ImageSource.Icon("heart-filled-symbolic"),
                                    iconSize = ImageSize.Specific(96),
                                    modifier = Modifier.margin(16),
                                )
                                Label(
                                    "Kotlin + GTK + ADW",
                                    Modifier.margin(16).alignment(Align.CENTER).cssClasses("title-1"),
                                )
                            }
                        }
                    },
                    overlays = {
                        WindowControls(
                            side = PackType.START,
                            modifier = Modifier.alignment(Align.START, Align.START),
                        )
                        WindowControls(
                            side = PackType.END,
                            modifier = Modifier.alignment(Align.END, Align.START),
                        )
                    },
                )
            }
        }
    }
}
