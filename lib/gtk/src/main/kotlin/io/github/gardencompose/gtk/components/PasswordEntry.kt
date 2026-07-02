package io.github.gardencompose.gtk.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Updater
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.gardencompose.modifier.Modifier
import org.gnome.gobject.GObject
import org.gnome.gtk.Text
import org.javagi.gobject.SignalConnection
import org.gnome.gtk.PasswordEntry as GtkPasswordEntry

private class GtkPasswordEntryComposeNode(
    gObject: GtkPasswordEntry,
) : GtkEditableComposeNode<GtkPasswordEntry>(gObject) {
    var onActivate: SignalConnection<GtkPasswordEntry.ActivateCallback>? = null
    var onVisibilityChange: SignalConnection<GObject.NotifyCallback>? = null
}

@Composable
private fun BasePasswordEntry(
    creator: () -> GtkPasswordEntryComposeNode,
    updater: Updater<GtkPasswordEntryComposeNode>.() -> Unit,
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onActivate: () -> Unit = {},
    editable: Boolean = true,
    alignment: Float = 0f,
    enableUndo: Boolean = true,
    maxWidthChars: Int = -1,
    widthChars: Int = -1,
    placeholderText: String? = null,
    showPeekIcon: Boolean = false,
) {
    Editable(
        creator = creator,
        updater = {
            set(onActivate) {
                this.onActivate?.disconnect()
                this.onActivate = this.widget.onActivate {
                    this.onActivate?.block()
                    onActivate()
                    this.onActivate?.unblock()
                }
            }
            set(placeholderText) { this.widget.set("placeholder-text", it) }
            set(showPeekIcon) { this.widget.showPeekIcon = it }
            updater()
        },
        text = text,
        onTextChange = onTextChange,
        modifier = modifier,
        editable = editable,
        alignment = alignment,
        enableUndo = enableUndo,
        maxWidthChars = maxWidthChars,
        widthChars = widthChars,
    )
}

/**
 * Creates a [org.gnome.gtk.Entry] used to input text.
 *
 * @param text The text in the entry.
 * @param onTextChange Callback triggered when the text is changed.
 * @param modifier Compose [Modifier] for layout and styling.
 * @param onActivate Callback triggered when the entry is activated (for example: pressing the `Enter` key).
 * @param editable Whether the text can be edited.
 * for the window containing the entry.
 * @param alignment The alignment for the contents of the entry.
 * @param enableUndo Whether changes to this entry will be saved for undo/redo actions.
 * @param maxWidthChars The desired maximum width of the entry, in characters.
 * @param widthChars Number of characters to leave space for in the entry.
 * @param placeholderText Placeholder text displayed when the entry is empty.
 * @param showPeekIcon Whether the peek icon is shown.
 *
 * TODO:
 *  - setExtraMenu
 */
@Composable
fun PasswordEntry(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onActivate: () -> Unit = {},
    editable: Boolean = true,
    alignment: Float = 0f,
    enableUndo: Boolean = true,
    maxWidthChars: Int = -1,
    widthChars: Int = -1,
    placeholderText: String? = null,
    showPeekIcon: Boolean = false,
) {
    BasePasswordEntry(
        creator = { GtkPasswordEntryComposeNode(GtkPasswordEntry()) },
        updater = {},
        text = text,
        onTextChange = onTextChange,
        modifier = modifier,
        onActivate = { onActivate() },
        editable = editable,
        alignment = alignment,
        enableUndo = enableUndo,
        maxWidthChars = maxWidthChars,
        widthChars = widthChars,
        placeholderText = placeholderText,
        showPeekIcon = showPeekIcon,
    )
}

/**
 * Creates a [org.gnome.gtk.Entry] used to input text.
 *
 * @param text The text in the entry.
 * @param onTextChange Callback triggered when the text is changed.
 * @param modifier Compose [Modifier] for layout and styling.
 * @param onActivate Callback triggered when the entry is activated (for example: pressing the `Enter` key).
 * @param editable Whether the text can be edited.
 * for the window containing the entry.
 * @param alignment The alignment for the contents of the entry.
 * @param enableUndo Whether changes to this entry will be saved for undo/redo actions.
 * @param maxWidthChars The desired maximum width of the entry, in characters.
 * @param widthChars Number of characters to leave space for in the entry.
 * @param placeholderText Placeholder text displayed when the entry is empty.
 * @param showPeekIcon Whether the peek icon is shown.
 * @param onVisibilityChange Callback triggered when visibility is changed.
 * @param visibility Whether the text inside the entry is visible or hidden.
 *
 * TODO:
 *  - setExtraMenu
 */
@Composable
fun ControlledPasswordEntry(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onActivate: () -> Unit = {},
    editable: Boolean = true,
    alignment: Float = 0f,
    enableUndo: Boolean = true,
    maxWidthChars: Int = -1,
    widthChars: Int = -1,
    placeholderText: String? = null,
    showPeekIcon: Boolean = false,
    onVisibilityChange: (Boolean) -> Unit = {},
    visibility: Boolean = false,
) {
    var pendingChange by remember { mutableIntStateOf(0) }
    val passwordEntry = remember { GtkPasswordEntry() }
    val textWidget = remember { passwordEntry.firstChild as Text }

    BasePasswordEntry(
        creator = { GtkPasswordEntryComposeNode(passwordEntry) },
        updater = {
            set(onVisibilityChange) {
                this.onVisibilityChange?.disconnect()
                this.onVisibilityChange = textWidget.onNotify("visibility") {
                    pendingChange++
                    it(textWidget.visibility)
                }
            }
            set(visibility to pendingChange) { (visibility, _) ->
                this.onVisibilityChange?.block()
                textWidget.visibility = visibility
                this.onVisibilityChange?.unblock()
            }
        },
        text = text,
        onTextChange = onTextChange,
        modifier = modifier,
        onActivate = { onActivate() },
        editable = editable,
        alignment = alignment,
        enableUndo = enableUndo,
        maxWidthChars = maxWidthChars,
        widthChars = widthChars,
        placeholderText = placeholderText,
        showPeekIcon = showPeekIcon,
    )
}
