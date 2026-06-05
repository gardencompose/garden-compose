package io.github.gardencompose.adw.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import io.github.gardencompose.GtkApplier
import io.github.gardencompose.GtkComposeWidget
import io.github.gardencompose.LeafComposeNode
import io.github.gardencompose.modifier.Modifier
import org.gnome.adw.WindowTitle

/**
 * Creates a [org.gnome.adw.WindowTitle] that helps to set a window’s title and subtitle.
 *
 * @param title The title to display.
 * @param modifier Compose [Modifier] for layout and styling.
 * @param subtitle The subtitle to display.
 */
@Composable
fun WindowTitle(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
) {
    ComposeNode<GtkComposeWidget<WindowTitle>, GtkApplier>({
        LeafComposeNode(WindowTitle.builder().build())
    }) {
        set(title) { this.widget.title = it }
        set(modifier) { applyModifier(it) }
        set(subtitle) { this.widget.subtitle = it }
    }
}
