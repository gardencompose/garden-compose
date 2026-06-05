package io.github.gardencompose.gtk.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import io.github.gardencompose.GtkApplier
import io.github.gardencompose.GtkComposeNode
import io.github.gardencompose.GtkComposeWidget
import io.github.gardencompose.GtkContainerComposeNode
import io.github.gardencompose.SingleChildComposeNode
import io.github.gardencompose.VirtualComposeNode
import io.github.gardencompose.VirtualComposeNodeContainer
import io.github.gardencompose.modifier.Modifier
import org.gnome.gtk.Overlay

/**
 * Creates a [org.gnome.gtk.Overlay] that places widgets on top of a single main child.
 *
 * @param mainChild The child underneath the overlay.
 * @param modifier Compose [Modifier] for layout and styling.
 * @param overlays The composable content to display on top of [mainChild].
 */
@Composable
fun Overlay(
    mainChild: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    overlays: @Composable () -> Unit,
) {
    ComposeNode<GtkComposeWidget<Overlay>, GtkApplier>(
        factory = {
            VirtualComposeNodeContainer(Overlay.builder().build())
        },
        update = {
            set(modifier) { applyModifier(it) }
        },
        content = {
            MainChild {
                mainChild()
            }
            OverlayChildren {
                overlays()
            }
        },
    )
}

@Composable
private fun MainChild(
    content: @Composable () -> Unit,
) {
    ComposeNode<GtkComposeNode, GtkApplier>(
        factory = {
            VirtualComposeNode<Overlay> { overlay ->
                SingleChildComposeNode(
                    overlay,
                    set = { child = it },
                )
            }
        },
        update = { },
        content = content,
    )
}

@Composable
private fun OverlayChildren(
    content: @Composable () -> Unit,
) {
    ComposeNode<GtkComposeNode, GtkApplier>(
        factory = {
            VirtualComposeNode<Overlay> { overlay ->
                GtkContainerComposeNode.appendOnly(
                    overlay,
                    add = { addOverlay(it) },
                    remove = { removeOverlay(it) },
                )
            }
        },
        update = { },
        content = content,
    )
}
