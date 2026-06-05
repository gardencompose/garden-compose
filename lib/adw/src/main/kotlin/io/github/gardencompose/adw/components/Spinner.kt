package io.github.gardencompose.adw.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import io.github.gardencompose.GtkApplier
import io.github.gardencompose.GtkComposeWidget
import io.github.gardencompose.LeafComposeNode
import io.github.gardencompose.modifier.Modifier
import org.gnome.adw.Spinner as AdwSpinner

/**
 * Creates a [org.gnome.adw.Spinner] that displays an icon-size spinning animation.
 *
 * @param modifier Compose [Modifier] for layout and styling.
 */
@Composable
fun Spinner(
    modifier: Modifier = Modifier,
) {
    ComposeNode<GtkComposeWidget<AdwSpinner>, GtkApplier>({
        LeafComposeNode(AdwSpinner.builder().build())
    }) {
        set(modifier) { applyModifier(it) }
    }
}
