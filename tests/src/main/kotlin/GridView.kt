import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.adw.components.HeaderBar
import io.github.gardencompose.adw.components.ToolbarView
import io.github.gardencompose.gtk.ImageSource
import io.github.gardencompose.gtk.components.Button
import io.github.gardencompose.gtk.components.GridView
import io.github.gardencompose.gtk.components.HorizontalBox
import io.github.gardencompose.gtk.components.Image
import io.github.gardencompose.gtk.components.ImageSize
import io.github.gardencompose.gtk.components.Label
import io.github.gardencompose.gtk.components.ScrolledWindow
import io.github.gardencompose.gtk.components.VerticalBox
import io.github.gardencompose.gtk.components.rememberMultiSelectionModel
import io.github.gardencompose.modifier.Modifier
import io.github.gardencompose.modifier.expand
import io.github.gardencompose.modifier.margin
import io.github.oshai.kotlinlogging.KotlinLogging
import org.gnome.gobject.GObject
import kotlin.math.max

private val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {
    adwApplication("my.example.hello-app", args) {
        ApplicationWindow(title = "GridView", onClose = ::exitApplication, defaultWidth = 600, defaultHeight = 540) {
            VerticalBox {
                var itemSize by remember { mutableIntStateOf(5) }
                val items = remember(itemSize) {
                    List(itemSize) { index ->
                        CustomGridItem("Item #$index")
                    }
                }

                HeaderBar()

                ToolbarView(
                    topBar = {
                        HorizontalBox(
                            spacing = 8,
                            modifier = Modifier.margin(8),
                        ) {
                            Button(
                                label = "Add item",
                                onClick = { itemSize += 1 },
                            )
                            Button(
                                label = "Remove item",
                                onClick = { itemSize = max(0, itemSize - 1) },
                            )
                        }
                    },
                ) {
                    ScrolledWindow(
                        modifier = Modifier
                            .expand(true),
                    ) {
                        GridView(
                            model = rememberMultiSelectionModel(items),
                            enableRubberband = true,
                            onActivate = { logger.info { "activated item #$it" } },
                        ) { customItem ->
                            VerticalBox(
                                modifier = Modifier.margin(16),
                            ) {
                                Image(
                                    image = ImageSource.Icon("folder"),
                                    iconSize = ImageSize.Specific(96),
                                )
                                Label(
                                    text = customItem.name,
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

private data class CustomGridItem(val name: String) : GObject()
