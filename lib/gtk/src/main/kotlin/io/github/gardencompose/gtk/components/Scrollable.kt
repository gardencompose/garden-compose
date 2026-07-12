package io.github.gardencompose.gtk.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.Updater
import io.github.gardencompose.GtkApplier
import io.github.gardencompose.GtkComposeWidget
import org.gnome.gtk.Adjustment
import org.gnome.gtk.ScrollablePolicy
import org.gnome.gtk.Widget
import org.gnome.gtk.Scrollable as GtkScrollable

@Composable
internal fun <S : GtkComposeWidget<W>, W> Scrollable(
    creator: () -> S,
    updater: Updater<S>.() -> Unit,
    horizontalAdjustment: Adjustment? = null,
    horizontalScrollPolicy: ScrollablePolicy = ScrollablePolicy.MINIMUM,
    verticalAdjustment: Adjustment? = null,
    verticalScrollPolicy: ScrollablePolicy = ScrollablePolicy.MINIMUM,
) where W : Widget, W : GtkScrollable {
    ComposeNode<S, GtkApplier>(
        factory = creator,
        update = {
            set(horizontalAdjustment) { this.widget.hadjustment = it }
            set(horizontalScrollPolicy) { this.widget.hscrollPolicy = it }
            set(verticalAdjustment) { this.widget.vadjustment = it }
            set(verticalScrollPolicy) { this.widget.vscrollPolicy = it }
            updater()
        },
    )
}
