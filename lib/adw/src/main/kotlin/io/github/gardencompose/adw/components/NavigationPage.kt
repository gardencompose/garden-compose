package io.github.gardencompose.adw.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import io.github.gardencompose.GtkApplier
import io.github.gardencompose.GtkComposeWidget
import io.github.gardencompose.SingleChildComposeNode
import io.github.gardencompose.modifier.Modifier
import org.gnome.adw.NavigationPage as AdwNavigationPage

/**
 * Creates a [org.gnome.adw.NavigationPage], a page within [NavigationView] or [NavigationSplitView].
 *
 * @param title The title of the page.
 * @param modifier Compose [Modifier] for layout and styling.
 * @param tag A tag used to identify the page.
 * @param canPop  Whether the page can be popped from navigation stack.
 * @param content A composable widget that's displayed inside the page.
 */
@Composable
fun NavigationPage(
    title: String,
    modifier: Modifier = Modifier,
    tag: String? = null,
    canPop: Boolean = true,
    content: @Composable () -> Unit = {},
) {
    ComposeNode<GtkComposeWidget<AdwNavigationPage>, GtkApplier>(
        factory = {
            SingleChildComposeNode(
                widget = AdwNavigationPage(),
                set = { child = it },
            )
        },
        update = {
            set(title) { this.widget.title = it }
            set(modifier) { applyModifier(it) }
            set(tag) { this.widget.tag = it }
            set(canPop) { this.widget.canPop = it }
        },
        content = content,
    )
}
