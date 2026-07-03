package io.github.gardencompose.modifier

import org.gnome.gdk.Cursor

fun Modifier.cursor(cursor: Cursor?): Modifier = combine(
    apply = { it.cursor = cursor },
    undo = { it.cursor = null },
)
