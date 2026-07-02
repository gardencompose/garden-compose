package io.github.gardencompose.gtk.components

import androidx.compose.runtime.Composable
import io.github.gardencompose.modifier.Modifier
import org.gnome.gtk.EditableLabel as GtkEditableLabel

/**
 * Creates a [org.gnome.gtk.EditableLabel] to display an editable label.
 *
 * @param text The text in the entry.
 * @param onTextChange Callback triggered when the text is changed.
 * @param modifier Compose [Modifier] for layout and styling.
 * @param editable Whether the text can be edited.
 * @param alignment The alignment for the contents of the entry.
 * @param enableUndo Whether changes to this entry will be saved for undo/redo actions.
 * @param maxWidthChars The desired maximum width of the entry, in characters.
 * @param widthChars Number of characters to leave space for in the entry.
 */
@Composable
fun EditableLabel(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    editable: Boolean = true,
    alignment: Float = 0f,
    enableUndo: Boolean = true,
    maxWidthChars: Int = -1,
    widthChars: Int = -1,
) {
    Editable(
        creator = { GtkEditableComposeNode(GtkEditableLabel()) },
        updater = {},
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
