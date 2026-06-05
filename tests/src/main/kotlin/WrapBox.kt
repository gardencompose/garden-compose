import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.adw.components.Clamp
import io.github.gardencompose.adw.components.HeaderBar
import io.github.gardencompose.adw.components.WrapBox
import io.github.gardencompose.gtk.components.Button
import io.github.gardencompose.gtk.components.VerticalBox
import io.github.gardencompose.modifier.Modifier
import io.github.gardencompose.modifier.cssClasses
import io.github.gardencompose.modifier.margin
import io.github.oshai.kotlinlogging.KotlinLogging

private val logger = KotlinLogging.logger {}

val fruits = arrayOf(
    "Apple",
    "Pear",
    "Banana",
    "Watermelon",
    "Kiwi",
    "Tomato",
    "Pineapple",
    "Orange",
    "Lemon",
    "Cherry",
)

fun main(args: Array<String>) {
    adwApplication("my.example.hello-app", args) {
        ApplicationWindow(title = "Wrap Box", onClose = ::exitApplication, defaultHeight = 450, defaultWidth = 400) {
            VerticalBox {
                HeaderBar()

                Clamp(
                    modifier = Modifier.margin(16),
                ) {
                    WrapBox(
                        align = 0.5f,
                        childSpacing = 8,
                        lineSpacing = 8,
                    ) {
                        for (fruit in fruits) {
                            Button(
                                label = fruit,
                                modifier = Modifier.cssClasses("pill"),
                                onClick = { logger.info { "Clicked on $fruit" } },
                            )
                        }
                    }
                }
            }
        }
    }
}
