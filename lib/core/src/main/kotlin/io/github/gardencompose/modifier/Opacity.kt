package io.github.gardencompose.modifier

fun Modifier.opacity(opacity: Double = 1.0): Modifier = combine(
    apply = { it.opacity = opacity },
    undo = { it.opacity = 1.0 },
)
