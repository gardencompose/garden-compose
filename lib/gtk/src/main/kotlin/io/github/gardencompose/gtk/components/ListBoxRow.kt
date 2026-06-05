package io.github.gardencompose.gtk.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import io.github.gardencompose.GtkApplier
import io.github.gardencompose.GtkComposeWidget
import io.github.gardencompose.SingleChildComposeNode
import io.github.gardencompose.modifier.Modifier
import org.gnome.gtk.ListBoxRow

private class GtkListBoxRow(gObject: ListBoxRow) : SingleChildComposeNode<ListBoxRow>(gObject, { child = it })

/**
 * @param activatable The property determines whether the [ListBox]::row-activated signal will be emitted for this row.
 * @param selectable The property determines whether this row can be selected.
 */
@Composable
fun ListBoxRow(
    modifier: Modifier = Modifier,
    activatable: Boolean = true,
    selectable: Boolean = true,
    child: @Composable () -> Unit,
) {
    ComposeNode<GtkComposeWidget<ListBoxRow>, GtkApplier>(
        factory = { GtkListBoxRow(ListBoxRow.builder().build()) },
        update = {
            set(modifier) { applyModifier(it) }
            set(activatable) { this.widget.activatable = activatable }
            set(selectable) { this.widget.selectable = selectable }
        },
        content = child,
    )
}
