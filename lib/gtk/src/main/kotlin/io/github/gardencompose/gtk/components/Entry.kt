package io.github.gardencompose.gtk.components

import androidx.compose.runtime.Composable
import io.github.gardencompose.gtk.ImageSource
import io.github.gardencompose.modifier.Modifier
import org.gnome.gtk.EntryIconPosition
import org.gnome.gtk.Image
import org.gnome.gtk.InputHints
import org.gnome.gtk.InputPurpose
import org.gnome.pango.AttrList
import org.gnome.pango.TabArray
import org.javagi.gobject.SignalConnection
import org.gnome.gtk.Entry as GtkEntry

private class GtkEntryComposeNode(
    gObject: GtkEntry,
) : GtkEditableComposeNode<GtkEntry>(gObject) {
    var onActivate: SignalConnection<GtkEntry.ActivateCallback>? = null
    var onPrimaryIconPress: SignalConnection<GtkEntry.IconPressCallback>? = null
    var onSecondaryIconPress: SignalConnection<GtkEntry.IconPressCallback>? = null
}

private val emptyAttributes = AttrList()

/**
 * Creates a [org.gnome.gtk.Entry] used to input text.
 *
 * @param text The text in the entry.
 * @param onTextChange Callback triggered when the text is changed.
 * @param modifier Compose [Modifier] for layout and styling.
 * @param attributes A list of Pango attributes to apply to the text of the entry.
 * @param placeholderText The text displayed when the entry is empty and unfocused.
 * @param editable Whether the text can be edited.
 * @param visibility Whether the contents of the entry are visible.
 * @param activatesDefault Whether pressing `Enter` in will activate the default widget
 * for the window containing the entry.
 * @param alignment The alignment for the contents of the entry.
 * @param hasFrame Whether the entry has a frame.
 * @param inputHints Additional hints which allow input methods to fine-tune their behavior.
 * @param inputPurpose The input purpose which can be used by input methods to adjust their behavior.
 * @param invisibleChar The character to use in place of the actual text when visibility is `false`.
 * @param maxLength The maximum allowed length of the contents.
 * @param progressFraction The level at which the progress indicator of the entry is filled.
 * @param tabs A list of tabstops to apply to the text of the entry.
 * @param enableUndo Whether changes to this entry will be saved for undo/redo actions.
 * @param maxWidthChars The desired maximum width of the entry, in characters.
 * @param widthChars Number of characters to leave space for in the entry.
 *
 * TODO:
 *  - setExtraMenu
 *  - overwriteMode
 *  - pulse
 */
@Composable
fun Entry(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    editable: Boolean = true,
    visibility: Boolean = true,
    alignment: Float = 0f,
    enableUndo: Boolean = true,
    maxWidthChars: Int = -1,
    widthChars: Int = -1,
    attributes: AttrList = emptyAttributes,
    onActivate: () -> Unit = {},
    primaryIcon: ImageSource.Icon? = null,
    primaryIconSensitive: Boolean = true,
    onPrimaryIconPress: () -> Unit = {},
    secondaryIcon: ImageSource.Icon? = null,
    secondaryIconSensitive: Boolean = true,
    onSecondaryIconPress: () -> Unit = {},
    placeholderText: String? = null,
    activatesDefault: Boolean = false,
    hasFrame: Boolean = true,
    inputHints: InputHints = InputHints.NONE,
    inputPurpose: InputPurpose = InputPurpose.FREE_FORM,
    invisibleChar: Char? = null,
    maxLength: Int = 0,
    progressFraction: Double = 0.0,
    tabs: TabArray? = null,
) {
    Editable(
        creator = { GtkEntryComposeNode(GtkEntry()) },
        updater = {
            set(attributes) { this.widget.attributes = it }
            set(onActivate) {
                this.onActivate?.disconnect()
                this.onActivate = this.widget.onActivate {
                    this.onActivate?.block()
                    onActivate()
                    this.onActivate?.unblock()
                }
            }
            set(primaryIcon to primaryIconSensitive to onPrimaryIconPress) {
                applyIcon(primaryIcon, primaryIconSensitive, onPrimaryIconPress, EntryIconPosition.PRIMARY)
            }
            set(secondaryIcon to secondaryIconSensitive to onSecondaryIconPress) {
                applyIcon(secondaryIcon, secondaryIconSensitive, onSecondaryIconPress, EntryIconPosition.SECONDARY)
            }
            set(placeholderText) { this.widget.placeholderText = it }
            set(editable) { this.widget.editable = it }
            set(visibility) { this.widget.visibility = it }
            set(activatesDefault) { this.widget.activatesDefault = it }
            set(alignment) { this.widget.alignment = it }
            set(hasFrame) { this.widget.hasFrame = it }
            set(inputHints) { this.widget.setInputHints(it) }
            set(inputPurpose) { this.widget.inputPurpose = it }
            set(invisibleChar) {
                if (it == null) {
                    this.widget.unsetInvisibleChar()
                } else {
                    this.widget.invisibleChar = it.code
                }
            }
            set(maxLength) { this.widget.maxLength = it }
            set(progressFraction) { this.widget.progressFraction = it }
            set(tabs) { this.widget.tabs = it }
            set(enableUndo) { this.widget.enableUndo = it }
            set(maxWidthChars) { this.widget.maxWidthChars = it }
            set(widthChars) { this.widget.widthChars = it }
        },
        text = text,
        onTextChange = onTextChange,
        modifier = modifier,
        editable = editable,
        visibility = visibility,
        alignment = alignment,
        enableUndo = enableUndo,
        maxWidthChars = maxWidthChars,
        widthChars = widthChars,
    )
}

private fun GtkEntryComposeNode.applyIcon(
    icon: ImageSource.Icon?,
    sensitive: Boolean,
    onPress: () -> Unit,
    entryIconPosition: EntryIconPosition,
) {
    val child = if (entryIconPosition == EntryIconPosition.PRIMARY) this.widget.firstChild else this.widget.lastChild
    if (icon != null) {
        if (child is Image) child.visible = true
        widget.setIconFromIconName(entryIconPosition, icon.iconName)
    } else {
        if (child is Image) child.visible = false
        widget.setIconFromIconName(entryIconPosition, null)
    }
    widget.setIconSensitive(entryIconPosition, sensitive)

    when (entryIconPosition) {
        EntryIconPosition.PRIMARY -> {
            onPrimaryIconPress?.disconnect()
            onPrimaryIconPress = widget.onIconPress { position ->
                if (position == EntryIconPosition.PRIMARY) {
                    onPrimaryIconPress?.block()
                    onPress()
                    onPrimaryIconPress?.unblock()
                }
            }
        }

        EntryIconPosition.SECONDARY -> {
            onSecondaryIconPress?.disconnect()
            onSecondaryIconPress = widget.onIconPress { position ->
                if (position == EntryIconPosition.SECONDARY) {
                    onSecondaryIconPress?.block()
                    onPress()
                    onSecondaryIconPress?.unblock()
                }
            }
        }
    }
}
