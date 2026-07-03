package io.github.gardencompose.gtk.components

import androidx.compose.runtime.Composable
import io.github.gardencompose.modifier.Modifier
import org.gnome.gtk.InputHints
import org.gnome.gtk.InputPurpose
import org.javagi.gobject.SignalConnection
import org.gnome.gtk.SearchEntry as GtkSearchEntry

private class GtkSearchEntryComposeNode(
    gObject: GtkSearchEntry,
) : GtkEditableComposeNode<GtkSearchEntry>(gObject) {
    var onActivate: SignalConnection<GtkSearchEntry.ActivateCallback>? = null
    var onSearchChange: SignalConnection<GtkSearchEntry.SearchChangedCallback>? = null
    var onSearchStart: SignalConnection<GtkSearchEntry.SearchStartedCallback>? = null
    var onSearchStop: SignalConnection<GtkSearchEntry.StopSearchCallback>? = null
    var onNextMatch: SignalConnection<GtkSearchEntry.NextMatchCallback>? = null
    var onPreviousMatch: SignalConnection<GtkSearchEntry.PreviousMatchCallback>? = null
}

/**
 * Creates a [org.gnome.gtk.SearchEntry], an entry with functionalities for searching.
 *
 * @param text The text in the entry.
 * @param onTextChange Callback triggered when the text is changed.
 * @param modifier Compose [Modifier] for layout and styling.
 * @param editable Whether the text can be edited.
 * @param alignment The alignment for the contents of the entry.
 * @param enableUndo Whether changes to this entry will be saved for undo/redo actions.
 * @param maxWidthChars The desired maximum width of the entry, in characters.
 * @param widthChars Number of characters to leave space for in the entry.
 * @param onActivate Callback triggered when the entry is activated (for example: pressing the `Enter` key).
 * @param onSearchChange Callback triggered when search changes with an added delay of [searchDelay].
 * @param onSearchStart Callback triggered when the user starts searching.
 * @param onSearchStop Callback triggered when the user stops searching.
 * @param onNextMatch Callback triggered when the user initiates a move to the next match.
 * @param onPreviousMatch Callback triggered when the user initiates a move to the previous match.
 * @param inputHints Additional hints which allow input methods to fine-tune their behavior.
 * @param inputPurpose The input purpose which can be used by input methods to adjust their behavior.
 * @param placeholderText The text displayed when the entry is empty and unfocused.
 * @param searchDelay The delay in milliseconds from last keypress to the search changed signal.
 */
@Composable
fun SearchEntry(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    editable: Boolean = true,
    alignment: Float = 0f,
    enableUndo: Boolean = true,
    maxWidthChars: Int = -1,
    widthChars: Int = -1,
    onActivate: () -> Unit = {},
    onSearchChange: () -> Unit = {},
    onSearchStart: () -> Unit = {},
    onSearchStop: () -> Unit = {},
    onNextMatch: () -> Unit = {},
    onPreviousMatch: () -> Unit = {},
    inputHints: Set<InputHints> = setOf(InputHints.NONE),
    inputPurpose: InputPurpose = InputPurpose.FREE_FORM,
    placeholderText: String? = null,
    searchDelay: Int = 150,
) {
    Editable(
        creator = { GtkSearchEntryComposeNode(GtkSearchEntry()) },
        updater = {
            set(onActivate) {
                this.onActivate?.disconnect()
                this.onActivate = this.widget.onActivate {
                    this.onActivate?.block()
                    onActivate()
                    this.onActivate?.unblock()
                }
            }
            set(onSearchChange) {
                this.onSearchChange?.disconnect()
                this.onSearchChange = this.widget.onSearchChanged {
                    this.onSearchChange?.block()
                    onSearchChange()
                    this.onSearchChange?.unblock()
                }
            }
            set(onSearchStart) {
                this.onSearchStart?.disconnect()
                this.onSearchStart = this.widget.onSearchStarted {
                    this.onSearchStart?.block()
                    onSearchStart()
                    this.onSearchStart?.unblock()
                }
            }
            set(onSearchStop) {
                this.onSearchStop?.disconnect()
                this.onSearchStop = this.widget.onStopSearch {
                    this.onSearchStop?.block()
                    onSearchStop()
                    this.onSearchStop?.unblock()
                }
            }
            set(onNextMatch) {
                this.onNextMatch?.disconnect()
                this.onNextMatch = this.widget.onNextMatch {
                    this.onNextMatch?.block()
                    onNextMatch()
                    this.onNextMatch?.unblock()
                }
            }
            set(onPreviousMatch) {
                this.onPreviousMatch?.disconnect()
                this.onPreviousMatch = this.widget.onPreviousMatch {
                    this.onPreviousMatch?.block()
                    onPreviousMatch()
                    this.onPreviousMatch?.unblock()
                }
            }
            set(inputHints) { this.widget.inputHints = it }
            set(inputPurpose) { this.widget.inputPurpose = it }
            set(placeholderText) { this.widget.placeholderText = it }
            set(searchDelay) { this.widget.searchDelay = it }
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
