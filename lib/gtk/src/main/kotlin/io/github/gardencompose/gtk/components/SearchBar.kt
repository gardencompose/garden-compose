package io.github.gardencompose.gtk.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.gardencompose.GtkApplier
import io.github.gardencompose.SingleChildComposeNode
import io.github.gardencompose.modifier.Modifier
import org.gnome.gobject.GObject
import org.javagi.gobject.SignalConnection
import org.gnome.gtk.SearchBar as GtkSearchBar

private class GtkSearchBarComposeNode(
    widget: GtkSearchBar,
) : SingleChildComposeNode<GtkSearchBar>(widget, { child = it }) {
    var onSearchModeChange: SignalConnection<GObject.NotifyCallback>? = null
}

/**
 * Creates a [org.gnome.gtk.SearchBar] that shows a search bar typically containing a search entry.
 *
 * @param modifier Compose [Modifier] for layout and styling.
 * @param searchMode Whether the search mode is on and the search bar shown.
 * @param showCloseButton Whether to show the close button in the search bar.
 * @param onSearchModeChange Callback triggered when the search mode changes.
 * @param content The composable content to display.
 */
@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    searchMode: Boolean = true,
    showCloseButton: Boolean = false,
    onSearchModeChange: (Boolean) -> Unit = { },
    content: @Composable () -> Unit,
) {
    var pendingChange by remember { mutableIntStateOf(0) }
    ComposeNode<GtkSearchBarComposeNode, GtkApplier>(
        factory = {
            GtkSearchBarComposeNode(GtkSearchBar.builder().build())
        },
        update = {
            set(searchMode to pendingChange) { (mode, _) ->
                this.onSearchModeChange?.block()
                this.widget.searchMode = mode
                this.onSearchModeChange?.unblock()
            }
            set(showCloseButton) { this.widget.showCloseButton = it }
            set(onSearchModeChange) {
                this.onSearchModeChange?.disconnect()
                this.onSearchModeChange = this.widget.onNotify("search-mode-enabled") {
                    pendingChange++
                    it(this.widget.searchMode)
                }
            }
            set(modifier) { applyModifier(it) }
        },
        content = content,
    )
}
