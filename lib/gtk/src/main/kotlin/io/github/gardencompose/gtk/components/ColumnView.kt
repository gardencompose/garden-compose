package io.github.gardencompose.gtk.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.gardencompose.GtkApplier
import io.github.gardencompose.modifier.Modifier
import io.github.oshai.kotlinlogging.KotlinLogging
import org.gnome.gobject.GObject
import org.gnome.gtk.ColumnViewColumn
import org.gnome.gtk.ListTabBehavior
import org.gnome.gtk.SelectionModel
import org.gnome.gtk.ColumnView as GtkColumnView

private val logger = KotlinLogging.logger {}

/**
 * TODO:
 *  - Row factory
 *  - Header factory
 */
@Composable
private fun <T : GObject> BaseControlledColumnView(
    columns: List<ColumnViewColumn>,
    model: SelectionModel<T>,
    modifier: Modifier = Modifier,
    selectedItems: Set<Int>? = null,
    enableRubberband: Boolean = false,
    reorderable: Boolean = true,
    showColumnSeparators: Boolean = false,
    showRowSeparators: Boolean = false,
    singleClickActivate: Boolean = false,
    tabBehavior: ListTabBehavior = ListTabBehavior.ALL,
    onActivate: (position: Int) -> Unit = {},
    onSelectionChange: (positions: Set<Int>) -> Unit = {},
) {
    var pendingChange by remember { mutableIntStateOf(0) }

    ComposeNode<BaseListComposeNode<GtkColumnView, GtkColumnView.ActivateCallback>, GtkApplier>(
        factory = { BaseListComposeNode(GtkColumnView()) },
        update = {
            set(columns to columns.size) { (columns, _) ->
                // Remove old columns
                while (this.widget.columns.nItems > 0) {
                    this.widget.removeColumn(
                        this.widget.columns.getItem(0) as ColumnViewColumn,
                    )
                }

                // Add new columns
                for ((i, c) in columns.withIndex()) {
                    this.widget.insertColumn(i, c)
                }
            }
            set(model) { this.widget.model = it }
            set(modifier) { applyModifier(it) }
            set(enableRubberband) { this.widget.enableRubberband = it }
            set(reorderable) { this.widget.reorderable = it }
            set(showColumnSeparators) { this.widget.showColumnSeparators = it }
            set(showRowSeparators) { this.widget.showRowSeparators = it }
            set(singleClickActivate) { this.widget.singleClickActivate = it }
            set(tabBehavior) { this.widget.tabBehavior = it }
            set(onActivate) {
                this.onActivate?.disconnect()
                this.onActivate = this.widget.onActivate {
                    onActivate(it)
                }
            }
            set(onSelectionChange) {
                this.onSelectionChanges?.disconnect()
                this.onSelectionChanges = model.onSelectionChanged { _, _ ->
                    pendingChange++
                    val positions = mutableSetOf<Int>()
                    for (position in 0 until model.size) {
                        if (model.isSelected(position)) {
                            positions.add(position)
                        }
                    }
                    onSelectionChange(positions)
                }
            }
            set(selectedItems to selectedItems?.size to pendingChange) {
                if (selectedItems != null) {
                    this.onSelectionChanges?.block()
                    model.unselectAll()
                    if (selectedItems.isNotEmpty()) {
                        for (position in selectedItems) {
                            if (position < model.size) {
                                model.selectItem(position, false)
                            } else {
                                logger.warn { "Position $position is out of range" }
                            }
                        }
                    }
                    this.onSelectionChanges?.unblock()
                }
            }
        },
    )
}

@Composable
fun <T : GObject> ColumnView(
    columns: List<ColumnViewColumn>,
    model: SelectionModel<T>,
    modifier: Modifier = Modifier,
    enableRubberband: Boolean = false,
    reorderable: Boolean = true,
    showColumnSeparators: Boolean = false,
    showRowSeparators: Boolean = false,
    singleClickActivate: Boolean = false,
    tabBehavior: ListTabBehavior = ListTabBehavior.ALL,
    onActivate: (position: Int) -> Unit = {},
) {
    BaseControlledColumnView(
        columns = columns,
        model = model,
        modifier = modifier,
        enableRubberband = enableRubberband,
        reorderable = reorderable,
        showColumnSeparators = showColumnSeparators,
        showRowSeparators = showRowSeparators,
        singleClickActivate = singleClickActivate,
        tabBehavior = tabBehavior,
        onActivate = onActivate,
    )
}

@Composable
fun <T : GObject> ControlledColumnView(
    columns: List<ColumnViewColumn>,
    items: List<T>,
    selectedItems: Set<Int>,
    modifier: Modifier = Modifier,
    enableRubberband: Boolean = false,
    reorderable: Boolean = true,
    showColumnSeparators: Boolean = false,
    showRowSeparators: Boolean = false,
    singleClickActivate: Boolean = false,
    tabBehavior: ListTabBehavior = ListTabBehavior.ALL,
    onActivate: (position: Int) -> Unit = {},
    onSelectionChange: (positions: Set<Int>) -> Unit = {},
) {
    val model = rememberMultiSelectionModel(items)

    BaseControlledColumnView(
        columns = columns,
        model = model,
        modifier = modifier,
        selectedItems = selectedItems,
        enableRubberband = enableRubberband,
        reorderable = reorderable,
        showColumnSeparators = showColumnSeparators,
        showRowSeparators = showRowSeparators,
        singleClickActivate = singleClickActivate,
        tabBehavior = tabBehavior,
        onActivate = onActivate,
        onSelectionChange = onSelectionChange,
    )
}

@Composable
fun <T : GObject> ControlledColumnView(
    columns: List<ColumnViewColumn>,
    items: List<T>,
    selectedItem: Int?,
    modifier: Modifier = Modifier,
    enableRubberband: Boolean = false,
    reorderable: Boolean = true,
    showColumnSeparators: Boolean = false,
    showRowSeparators: Boolean = false,
    singleClickActivate: Boolean = false,
    tabBehavior: ListTabBehavior = ListTabBehavior.ALL,
    onActivate: (position: Int) -> Unit = {},
    onSelectionChange: (position: Int?) -> Unit = {},
) {
    val model = rememberSingleSelectionModel(items)

    LaunchedEffect(model) {
        model.autoselect = false
        model.canUnselect = true
        if (selectedItem == null) {
            model.unselectAll()
        }
    }

    BaseControlledColumnView(
        columns = columns,
        model = model,
        modifier = modifier,
        selectedItems = if (selectedItem != null) setOf(selectedItem) else emptySet(),
        enableRubberband = enableRubberband,
        reorderable = reorderable,
        showColumnSeparators = showColumnSeparators,
        showRowSeparators = showRowSeparators,
        singleClickActivate = singleClickActivate,
        tabBehavior = tabBehavior,
        onActivate = onActivate,
        onSelectionChange = { positions ->
            if (positions.isNotEmpty()) {
                onSelectionChange(positions.first())
            } else {
                onSelectionChange(null)
            }
        },
    )
}

@Composable
fun <T : GObject> ControlledColumnView(
    columns: List<ColumnViewColumn>,
    items: List<T>,
    modifier: Modifier = Modifier,
    enableRubberband: Boolean = false,
    reorderable: Boolean = true,
    showColumnSeparators: Boolean = false,
    showRowSeparators: Boolean = false,
    singleClickActivate: Boolean = false,
    tabBehavior: ListTabBehavior = ListTabBehavior.ALL,
    onActivate: (position: Int) -> Unit = {},
) {
    val model = rememberNoSelectionModel(items)

    BaseControlledColumnView(
        columns = columns,
        model = model,
        modifier = modifier,
        enableRubberband = enableRubberband,
        reorderable = reorderable,
        showColumnSeparators = showColumnSeparators,
        showRowSeparators = showRowSeparators,
        singleClickActivate = singleClickActivate,
        tabBehavior = tabBehavior,
        onActivate = onActivate,
    )
}
