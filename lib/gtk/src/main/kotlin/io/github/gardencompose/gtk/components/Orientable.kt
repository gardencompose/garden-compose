package io.github.gardencompose.gtk.components

import androidx.compose.runtime.Updater
import io.github.gardencompose.GtkComposeWidget
import org.gnome.gtk.Orientation
import org.gnome.gtk.Widget
import org.gnome.gtk.Orientable as GtkOrientable

fun <E : GtkComposeWidget<W>, W> Updater<E>.orientable(orientation: Orientation) where W : Widget, W : GtkOrientable {
    set(orientation) { this.widget.orientation = it }
}
