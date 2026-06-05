package io.github.gardencompose.adw.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import io.github.gardencompose.GtkApplier
import io.github.gardencompose.GtkComposeWidget
import io.github.gardencompose.LeafComposeNode
import io.github.gardencompose.gtk.ImageSource
import io.github.gardencompose.gtk.setImage
import io.github.gardencompose.modifier.Modifier
import org.gnome.adw.Avatar

/**
 * Creates a [org.gnome.adw.Avatar] that displays an image, with a generated fallback.
 *
 * @param image The image used as the avatar.
 * @param text Fallback source string for initials when [image] is `null`.
 * @param modifier Compose [Modifier] for layout and styling.
 * @param showInitials Replaces the initials with the default symbolic icon
 * @param size The size of the avatar
 */
@Composable
fun Avatar(
    image: ImageSource?,
    text: String,
    modifier: Modifier = Modifier,
    showInitials: Boolean = false,
    size: Int = -1,
) {
    ComposeNode<GtkComposeWidget<Avatar>, GtkApplier>({
        LeafComposeNode(Avatar.builder().build())
    }) {
        set(modifier) { applyModifier(it) }
        set(image) { img ->
            this.widget.setImage(
                img,
                getCurrentPaintable = { this.customImage },
                setIcon = { this.iconName = it },
                setPaintable = { this.customImage = it },
            )
        }
        set(text) { this.widget.text = it }
        set(showInitials) { this.widget.showInitials = it }
        set(size) { this.widget.size = it }
    }
}
