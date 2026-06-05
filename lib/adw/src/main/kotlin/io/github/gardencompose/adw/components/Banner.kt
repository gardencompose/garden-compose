package io.github.gardencompose.adw.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import io.github.gardencompose.GtkApplier
import io.github.gardencompose.LeafComposeNode
import io.github.gardencompose.modifier.Modifier
import org.gnome.adw.Banner
import org.javagi.gobject.SignalConnection

private class AdwBannerComposeNode(
    gObject: Banner,
) : LeafComposeNode<Banner>(gObject) {
    var onButtonClicked: SignalConnection<Banner.ButtonClickedCallback>? = null
}

/**
 * Creates a [org.gnome.adw.Banner], a bar with contextual information.
 *
 * @param modifier Compose [Modifier] for layout and styling.
 * @param title Text displayed on the [Banner].
 * @param buttonLabel Text displayed inside the button.
 * @param onButtonClick Callback triggered when the button is clicked.
 * @param revealed Whether the banner is shown or not.
 * @param useMarkup Whether Pango markup for the [title] is enabled.
 */
@Composable
fun Banner(
    modifier: Modifier = Modifier,
    title: String? = null,
    buttonLabel: String? = null,
    onButtonClick: (() -> Unit)? = null,
    revealed: Boolean = true,
    useMarkup: Boolean = true,
) {
    ComposeNode<AdwBannerComposeNode, GtkApplier>({
        AdwBannerComposeNode(Banner.builder().build())
    }) {
        set(modifier) { applyModifier(it) }
        set(title) { this.widget.title = it }
        set(buttonLabel) { this.widget.buttonLabel = it }
        set(onButtonClick) {
            this.onButtonClicked?.disconnect()
            if (it != null) {
                this.onButtonClicked = this.widget.onButtonClicked {
                    it()
                }
            } else {
                this.onButtonClicked = null
            }
        }
        set(revealed) { this.widget.revealed = it }
        set(useMarkup) { this.widget.useMarkup = it }
    }
}
