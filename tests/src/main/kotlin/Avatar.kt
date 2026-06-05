import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.adw.components.Avatar
import io.github.gardencompose.adw.components.HeaderBar
import io.github.gardencompose.gtk.ImageSource
import io.github.gardencompose.gtk.components.VerticalBox
import io.github.gardencompose.useGioResource

fun main(args: Array<String>) {
    useGioResource("resources.gresource") {
        adwApplication("my.example.hello-app", args) {
            ApplicationWindow(title = "Avatar", onClose = ::exitApplication, defaultWidth = 600, defaultHeight = 540) {
                VerticalBox(
                    spacing = 16,
                ) {
                    HeaderBar()
                    Avatar(
                        text = "John Smith",
                        image = ImageSource.forResource("/my/example/hello-app/images/lulu.jpg"),
                        size = 100,
                    )
                    Avatar(
                        text = "John Doe",
                        image = null,
                        showInitials = true,
                        size = 100,
                    )
                    Avatar(
                        text = "",
                        image = null,
                        size = 100,
                    )
                    Avatar(
                        text = "",
                        image = ImageSource.Icon("folder-symbolic"),
                        size = 100,
                    )
                }
            }
        }
    }
}
