package io.github.gardencompose.adw.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import io.github.gardencompose.GtkApplier
import io.github.gardencompose.GtkComposeWidget
import io.github.gardencompose.SingleChildComposeNode
import io.github.gardencompose.gtk.ImageSource
import io.github.gardencompose.gtk.setImage
import io.github.gardencompose.modifier.Modifier
import org.gnome.adw.StatusPage

/**
 * Creates a [org.gnome.adw.StatusPage] used for empty/error states and similar use-cases.
 *
 * @param title The title to be displayed below the icon.
 * @param modifier Compose [Modifier] for layout and styling.
 * @param description The description markup to be displayed below the title.
 * @param icon The icon to be used.
 * @param content The composable content to display.
 */
@Composable
fun StatusPage(
    title: String,
    modifier: Modifier = Modifier,
    description: String? = null,
    icon: ImageSource? = null,
    content: @Composable () -> Unit = {},
) {
    ComposeNode<GtkComposeWidget<StatusPage>, GtkApplier>(
        factory = {
            SingleChildComposeNode(
                StatusPage.builder().build(),
                set = { child = it },
            )
        },
        update = {
            set(title) { this.widget.title = it }
            set(modifier) { applyModifier(it) }
            set(description) { this.widget.description = it }
            set(icon) { img ->
                this.widget.setImage(
                    img,
                    getCurrentPaintable = { this.paintable },
                    setIcon = { this.iconName = it },
                    setPaintable = { this.paintable = it },
                )
            }
        },
        content = content,
    )
}
