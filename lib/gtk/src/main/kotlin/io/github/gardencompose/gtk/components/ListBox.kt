package io.github.gardencompose.gtk.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Updater
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.gardencompose.GtkApplier
import io.github.gardencompose.GtkComposeNode
import io.github.gardencompose.GtkComposeWidget
import io.github.gardencompose.GtkContainerComposeNode
import io.github.gardencompose.SingleChildComposeNode
import io.github.gardencompose.VirtualComposeNode
import io.github.gardencompose.VirtualComposeNodeContainer
import io.github.gardencompose.modifier.Modifier
import org.gnome.gtk.Adjustment
import org.gnome.gtk.ListBoxRow
import org.gnome.gtk.ListTabBehavior
import org.gnome.gtk.SelectionMode
import org.gnome.gtk.Widget
import org.javagi.gobject.SignalConnection
import org.gnome.gtk.ListBox as GtkListBox

private class GtkListBoxComposeNode(gObject: GtkListBox) : VirtualComposeNodeContainer<GtkListBox>(gObject) {
    var onSelectRow: SignalConnection<GtkListBox.RowSelectedCallback>? = null
    var onSelectedRowsChange: SignalConnection<GtkListBox.SelectedRowsChangedCallback>? = null
    var onSelectAll: SignalConnection<GtkListBox.SelectAllCallback>? = null
    var onUnselectAll: SignalConnection<GtkListBox.UnselectAllCallback>? = null
}

private class GtkListBoxContentComposeNode(gObject: GtkListBox) : GtkContainerComposeNode<GtkListBox>(gObject) {
    override fun addNode(index: Int, child: GtkComposeWidget<Widget>) {
        widget.insert(child.widget, index)
        super.addNode(index, child)
    }

    override fun removeNode(index: Int) {
        widget.remove(children[index])
        super.removeNode(index)
    }

    override fun clearNodes() {
        widget.removeAll()
        super.clearNodes()
    }
}

/**
 * TODO:
 *  - filter func
 *  - header func
 *  - sort func
 */
@Composable
private fun BaseListBox(
    creator: () -> GtkListBoxComposeNode,
    updater: Updater<GtkListBoxComposeNode>.() -> Unit,
    modifier: Modifier = Modifier,
    activateOnSingleClick: Boolean = true,
    adjustment: Adjustment? = null,
    selectionMode: SelectionMode = SelectionMode.SINGLE,
    showSeparators: Boolean = false,
    tabBehavior: ListTabBehavior = ListTabBehavior.ALL,
    onSelectRow: (row: ListBoxRow?) -> Unit = {},
    onSelectedRowsChange: () -> Unit = {},
    onSelectAll: () -> Unit = {},
    onUnselectAll: () -> Unit = {},
    placeholder: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    ComposeNode<GtkListBoxComposeNode, GtkApplier>(
        factory = creator,
        update = {
            set(modifier) { applyModifier(it) }
            set(activateOnSingleClick) { this.widget.activateOnSingleClick = it }
            set(adjustment) { this.widget.adjustment = it }
            set(selectionMode) { this.widget.selectionMode = it }
            set(showSeparators) { this.widget.showSeparators = it }
            set(tabBehavior) { this.widget.tabBehavior = it }
            set(onSelectRow) {
                this.onSelectRow?.disconnect()
                this.onSelectRow = this.widget.onRowSelected { onSelectRow(it) }
            }
            set(onSelectedRowsChange) {
                this.onSelectedRowsChange?.disconnect()
                this.onSelectedRowsChange = this.widget.onSelectedRowsChanged { onSelectedRowsChange() }
            }
            set(onSelectAll) {
                this.onSelectAll?.disconnect()
                this.onSelectAll = this.widget.onSelectAll { onSelectAll() }
            }
            set(onUnselectAll) {
                this.onUnselectAll?.disconnect()
                this.onUnselectAll = this.widget.onUnselectAll { onUnselectAll() }
            }
            updater()
        },
        content = {
            ListBoxPlaceHolder(placeholder)
            ListBoxContent(content)
        },
    )
}

/**
 * Creates a [org.gnome.gtk.ListBox].
 *
 * @param modifier Compose [Modifier] for layout and styling.
 * @param activateOnSingleClick Whether rows activate on single click.
 * @param adjustment The adjustment used for vertical scrolling.
 * @param selectionMode The selection mode.
 * @param showSeparators Whether the rows have separators between them.
 * @param tabBehavior The behavior of `Tab` and `Shift + Tab` keys.
 * @param onSelectRow Callback triggered when a row is selected.
 * @param onSelectedRowsChange Callback triggered when the selection changes.
 * @param onSelectAll Callback triggered when the shortcut for selecting everything is activated.
 * @param onUnselectAll Callback triggered when the shortcut for unselecting everything is activated.
 * @param placeholder Component shown when the list is empty.
 * @param content The list of [ListBoxRow].
 */
@Composable
fun ListBox(
    modifier: Modifier = Modifier,
    activateOnSingleClick: Boolean = true,
    adjustment: Adjustment? = null,
    selectionMode: SelectionMode = SelectionMode.SINGLE,
    showSeparators: Boolean = false,
    tabBehavior: ListTabBehavior = ListTabBehavior.ALL,
    onSelectRow: (row: ListBoxRow?) -> Unit = {},
    onSelectedRowsChange: () -> Unit = {},
    onSelectAll: () -> Unit = {},
    onUnselectAll: () -> Unit = {},
    placeholder: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    BaseListBox(
        creator = { GtkListBoxComposeNode(GtkListBox()) },
        updater = {},
        modifier = modifier,
        activateOnSingleClick = activateOnSingleClick,
        adjustment = adjustment,
        selectionMode = selectionMode,
        showSeparators = showSeparators,
        tabBehavior = tabBehavior,
        onSelectRow = onSelectRow,
        onSelectedRowsChange = onSelectedRowsChange,
        onSelectAll = onSelectAll,
        onUnselectAll = onUnselectAll,
        placeholder = placeholder,
        content = content,
    )
}

/**
 * Creates a controlled [org.gnome.gtk.ListBox] that can only have a one selected row.
 *
 * @param selectedRow The index of the selected row.
 * @param onSelectRow Callback triggered when a row is selected.
 * @param modifier Compose [Modifier] for layout and styling.
 * @param activateOnSingleClick Whether rows activate on single click.
 * @param adjustment The adjustment used for vertical scrolling.
 * @param showSeparators Whether the rows have separators between them.
 * @param tabBehavior The behavior of `Tab` and `Shift + Tab` keys.
 * @param onSelectAll Callback triggered when the shortcut for selecting everything is activated.
 * @param onUnselectAll Callback triggered when the shortcut for unselecting everything is activated.
 * @param placeholder Component shown when the list is empty.
 * @param content The list of [ListBoxRow].
 */
@Composable
fun ControlledListBox(
    selectedRow: Int?,
    onSelectRow: (position: Int?) -> Unit,
    modifier: Modifier = Modifier,
    activateOnSingleClick: Boolean = true,
    adjustment: Adjustment? = null,
    showSeparators: Boolean = false,
    tabBehavior: ListTabBehavior = ListTabBehavior.ALL,
    onSelectAll: () -> Unit = {},
    onUnselectAll: () -> Unit = {},
    placeholder: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val gObject = remember { GtkListBox() }
    var pendingChange by remember { mutableIntStateOf(0) }

    // Unselects the automatically selected row
    LaunchedEffect(Unit) {
        gObject.selectionMode = SelectionMode.NONE
        gObject.selectionMode = SelectionMode.SINGLE
    }

    BaseListBox(
        creator = { GtkListBoxComposeNode(gObject) },
        updater = {
            set(selectedRow to pendingChange) {
                this.onSelectRow?.block()
                if (selectedRow != null) {
                    this.widget.selectRow(this.widget.getRowAtIndex(selectedRow))
                } else {
                    this.widget.unselectAll()
                }
                this.onSelectRow?.unblock()
            }
            set(onSelectRow) {
                this.onSelectRow?.disconnect()
                this.onSelectRow = this.widget.onRowSelected {
                    this.onSelectRow?.block()
                    pendingChange++
                    onSelectRow(it?.index)
                    this.onSelectRow?.unblock()
                }
            }
        },
        modifier = modifier,
        activateOnSingleClick = activateOnSingleClick,
        adjustment = adjustment,
        selectionMode = SelectionMode.SINGLE,
        showSeparators = showSeparators,
        tabBehavior = tabBehavior,
        onSelectAll = onSelectAll,
        onUnselectAll = onUnselectAll,
        placeholder = placeholder,
        content = content,
    )
}

/**
 * Creates a controlled [org.gnome.gtk.ListBox] that can have multiple selected rows.
 *
 * @param selectedRows The indexes of the selected rows.
 * @param onSelectRows Callback triggered when rows are selected.
 * @param modifier Compose [Modifier] for layout and styling.
 * @param activateOnSingleClick Whether rows activate on single click.
 * @param adjustment The adjustment used for vertical scrolling.
 * @param showSeparators Whether the rows have separators between them.
 * @param tabBehavior The behavior of `Tab` and `Shift + Tab` keys.
 * @param onSelectAll Callback triggered when the shortcut for selecting everything is activated.
 * @param onUnselectAll Callback triggered when the shortcut for unselecting everything is activated.
 * @param placeholder Component shown when the list is empty.
 * @param content The list of [ListBoxRow].
 */
@Composable
fun ControlledListBox(
    selectedRows: Set<Int>,
    onSelectRows: (positions: Set<Int>) -> Unit,
    modifier: Modifier = Modifier,
    activateOnSingleClick: Boolean = true,
    adjustment: Adjustment? = null,
    showSeparators: Boolean = false,
    tabBehavior: ListTabBehavior = ListTabBehavior.ALL,
    onSelectAll: () -> Unit = {},
    onUnselectAll: () -> Unit = {},
    placeholder: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    var pendingChange by remember { mutableIntStateOf(0) }

    BaseListBox(
        creator = { GtkListBoxComposeNode(GtkListBox()) },
        updater = {
            set(selectedRows to selectedRows.size to pendingChange) {
                this.onSelectedRowsChange?.block()
                this.widget.unselectAll()
                for (row in selectedRows) {
                    this.widget.selectRow(this.widget.getRowAtIndex(row))
                }
                this.onSelectedRowsChange?.unblock()
            }
            set(onSelectRows) {
                this.onSelectedRowsChange?.disconnect()
                this.onSelectedRowsChange = this.widget.onSelectedRowsChanged {
                    this.onSelectedRowsChange?.block()
                    pendingChange++
                    val indexes = mutableSetOf<Int>()
                    this.widget.selectedForeach { _, row ->
                        indexes.add(row.index)
                    }
                    onSelectRows(indexes)
                    this.onSelectedRowsChange?.unblock()
                }
            }
        },
        modifier = modifier,
        activateOnSingleClick = activateOnSingleClick,
        adjustment = adjustment,
        selectionMode = SelectionMode.MULTIPLE,
        showSeparators = showSeparators,
        tabBehavior = tabBehavior,
        onSelectAll = onSelectAll,
        onUnselectAll = onUnselectAll,
        placeholder = placeholder,
        content = content,
    )
}

/**
 * Creates a controlled [org.gnome.gtk.ListBox] that cannot have a selected row.
 *
 * @param modifier Compose [Modifier] for layout and styling.
 * @param activateOnSingleClick Whether rows activate on single click.
 * @param adjustment The adjustment used for vertical scrolling.
 * @param showSeparators Whether the rows have separators between them.
 * @param tabBehavior The behavior of `Tab` and `Shift + Tab` keys.
 * @param onSelectAll Callback triggered when the shortcut for selecting everything is activated.
 * @param onUnselectAll Callback triggered when the shortcut for unselecting everything is activated.
 * @param placeholder Component shown when the list is empty.
 * @param content The list of [ListBoxRow].
 */
@Composable
fun ControlledListBox(
    modifier: Modifier = Modifier,
    activateOnSingleClick: Boolean = true,
    adjustment: Adjustment? = null,
    showSeparators: Boolean = false,
    tabBehavior: ListTabBehavior = ListTabBehavior.ALL,
    onSelectAll: () -> Unit = {},
    onUnselectAll: () -> Unit = {},
    placeholder: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    BaseListBox(
        creator = { GtkListBoxComposeNode(GtkListBox()) },
        updater = {},
        modifier = modifier,
        activateOnSingleClick = activateOnSingleClick,
        adjustment = adjustment,
        selectionMode = SelectionMode.NONE,
        showSeparators = showSeparators,
        tabBehavior = tabBehavior,
        onSelectAll = onSelectAll,
        onUnselectAll = onUnselectAll,
        placeholder = placeholder,
        content = content,
    )
}

@Composable
private fun ListBoxPlaceHolder(
    content: @Composable () -> Unit,
) {
    ComposeNode<GtkComposeNode, GtkApplier>(
        factory = {
            VirtualComposeNode<GtkListBox> { listBox ->
                SingleChildComposeNode(listBox) {
                    setPlaceholder(it)
                }
            }
        },
        update = {},
        content = content,
    )
}

@Composable
private fun ListBoxContent(
    content: @Composable () -> Unit,
) {
    ComposeNode<GtkComposeNode, GtkApplier>(
        factory = {
            VirtualComposeNode<GtkListBox> { listBox ->
                GtkListBoxContentComposeNode(listBox)
            }
        },
        update = {},
        content = content,
    )
}
