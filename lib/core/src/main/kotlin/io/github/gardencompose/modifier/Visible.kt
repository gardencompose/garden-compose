package io.github.gardencompose.modifier

fun Modifier.visible(visible: Boolean = true): Modifier = combine(
    apply = { it.visible = visible },
    undo = { it.visible = true },
)
