import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.adw.components.HeaderBar
import io.github.gardencompose.adw.components.ToolbarView
import io.github.gardencompose.gtk.ImageSource
import io.github.gardencompose.gtk.components.*
import io.github.gardencompose.modifier.Modifier
import io.github.gardencompose.modifier.alignment
import io.github.gardencompose.modifier.expandVertically
import org.gnome.gio.File
import org.gnome.gtk.Align
import org.gnome.gtk.ContentFit
import org.gnome.gtk.PackType

fun main(args: Array<String>) {
    adwApplication("my.example.hello-app", args) {
        ApplicationWindow("Test", onClose = ::exitApplication, defaultWidth = 800) {
            ToolbarView(
                bottomBar = {
                    HeaderBar(
                        startWidgets = { Button("Start", onClick = {}) },
                        endWidgets = { Button("End", onClick = {}) },
                        title = {
                            Label("Custom title")
                        },
                    )
                },
            ) {
                Overlay(
                    mainChild = {
                        Picture(
                            ImageSource.forFile(File.newForPath("tests/src/main/gresources/images/lulu.jpg")),
                            contentFit = ContentFit.COVER,
                            modifier = Modifier.expandVertically(),
                        )
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
