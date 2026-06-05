import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.adw.components.Banner
import io.github.gardencompose.adw.components.HeaderBar
import io.github.gardencompose.adw.components.HorizontalClamp
import io.github.gardencompose.gtk.components.ToggleButton
import io.github.gardencompose.gtk.components.VerticalBox
import io.github.gardencompose.modifier.Modifier
import io.github.gardencompose.modifier.alignment
import io.github.gardencompose.modifier.expand
import org.gnome.gtk.Align

fun main(args: Array<String>) {
    val bannerTitle = "<i>" +
        "<span foreground=\"red\" size=\"x-large\">Hell</span>" +
        "<span foreground=\"green\" size=\"x-large\">o wo</span>" +
        "<span foreground=\"blue\" size=\"x-large\">rld!</span>" +
        "</i>"

    adwApplication("my.example.hello-app", args) {
        ApplicationWindow(title = "Banner", onClose = ::exitApplication, defaultWidth = 600, defaultHeight = 400) {
            val isRevealed = remember { mutableStateOf(false) }

            VerticalBox {
                HeaderBar()
                Banner(
                    onButtonClick = {
                        isRevealed.value = false
                    },
                    title = bannerTitle,
                    buttonLabel = "Hide me!",
                    revealed = isRevealed.value,
                    useMarkup = true,
                )

                HorizontalClamp(modifier = Modifier.expand(true)) {
                    ToggleButton(
                        modifier = Modifier.alignment(Align.CENTER),
                        label = "${if (isRevealed.value) "Hide" else "Reveal"} the banner",
                        active = isRevealed.value,
                        onToggle = { isRevealed.value = !isRevealed.value },
                    )
                }
            }
        }
    }
}
