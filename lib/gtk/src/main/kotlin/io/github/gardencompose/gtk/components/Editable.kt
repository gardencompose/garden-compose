package io.github.gardencompose.gtk.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.Updater
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import io.github.gardencompose.Gtk
import io.github.gardencompose.GtkApplier
import io.github.gardencompose.LeafComposeNode
import io.github.gardencompose.modifier.Modifier
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.gnome.gobject.GObject
import org.gnome.gobject.GObjects
import org.gnome.gtk.Widget
import org.javagi.gobject.SignalConnection
import org.gnome.gtk.Editable as GtkEditable

internal open class GtkEditableComposeNode<W>(
    gObject: W,
) : LeafComposeNode<W>(gObject) where W : Widget, W : GtkEditable {
    var onDeleteSignalHandler: SignalConnection<GtkEditable.DeleteTextCallback>? = null
    var onInsertSignalHandler: SignalConnection<GtkEditable.InsertTextCallback>? = null
}

private data class TentativeCursorPosition(
    val position: Int,
    val condition: (String) -> Boolean,
)

private data class PendingDelete(val startPos: Int, val endPos: Int) {
    fun countDeletedCharacters(end: Int): Int {
        return if (end > startPos) {
            (end - startPos).coerceAtMost(endPos - startPos)
        } else {
            0
        }
    }

    fun apply(str: String): String {
        return when {
            startPos == str.length -> str
            endPos < 0 -> str.take(startPos)
            else -> str.removeRange(startPos until endPos)
        }
    }
}

@Composable
internal fun <E : GtkEditableComposeNode<*>> Editable(
    creator: () -> E,
    updater: Updater<E>.() -> Unit,
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    editable: Boolean = true,
    alignment: Float = 0f,
    enableUndo: Boolean = true,
    maxWidthChars: Int = -1,
    widthChars: Int = -1,
) {
    val cs = rememberCoroutineScope { Dispatchers.Gtk }
    val text by rememberUpdatedState(text)
    val onTextChange by rememberUpdatedState(onTextChange)
    var tentativeCursorPosition by remember { mutableStateOf<TentativeCursorPosition?>(null) }
    var pendingDelete by remember { mutableStateOf<PendingDelete?>(null) }

    fun process() {
        val pd = pendingDelete
        if (pd != null) {
            val newText = pd.apply(text)
            tentativeCursorPosition = TentativeCursorPosition(
                position = pd.startPos,
                condition = { it.length == newText.length },
            )
            pendingDelete = null
            onTextChange(newText)
        }
    }

    ComposeNode<E, GtkApplier>(
        factory = {
            val composeNode = creator()
            val editable = composeNode.widget.delegate!!

            val onInsertText = editable.onInsertText { textToInsert, _, position ->
                val pd = pendingDelete
                var prevText = text
                var newPosition = position.get() ?: return@onInsertText
                if (pd != null) {
                    prevText = pd.apply(prevText)
                    newPosition -= pd.countDeletedCharacters(newPosition)
                    pendingDelete = null
                }
                val newText = buildString(prevText.length + textToInsert.length) {
                    append(prevText)
                    insert(newPosition, textToInsert)
                }
                onTextChange(newText)
                tentativeCursorPosition = TentativeCursorPosition(
                    position = newPosition + textToInsert.length,
                    condition = { it.length == newText.length },
                )
                GObjects.signalStopEmissionByName(editable as GObject, "insert-text")
            }

            val onDeleteText = editable.onDeleteText { startPos, endPos ->
                require(pendingDelete == null)
                pendingDelete = PendingDelete(startPos, endPos)
                // onInsertText might get called immediately after.
                // I need to be able to handle those things in quick succession.
                cs.launch { process() }
                GObjects.signalStopEmissionByName(editable as GObject, "delete-text")
            }

            composeNode.onInsertSignalHandler = onInsertText
            composeNode.onDeleteSignalHandler = onDeleteText
            composeNode
        },
        update = {
            set(modifier) { applyModifier(it) }
            set(text to tentativeCursorPosition) { (text, pos) ->
                if (this.widget.text != text) {
                    this.onDeleteSignalHandler?.block()
                    this.onInsertSignalHandler?.block()
                    this.widget.text = text
                    this.onDeleteSignalHandler?.unblock()
                    this.onInsertSignalHandler?.unblock()
                    if (pos != null && pos.condition(text)) {
                        this.widget.position = pos.position
                    }
                }
            }
            set(editable) { this.widget.editable = it }
            set(alignment) { this.widget.alignment = it }
            set(enableUndo) { this.widget.enableUndo = it }
            set(maxWidthChars) { this.widget.maxWidthChars = it }
            set(widthChars) { this.widget.widthChars = it }
            updater()
        },
    )
}
