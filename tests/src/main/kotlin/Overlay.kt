import androidx.compose.runtime.getValue
import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.adw.components.HeaderBar
import io.github.gardencompose.adw.components.OverlaySplitView
import io.github.gardencompose.gtk.components.*
import io.github.gardencompose.modifier.Modifier
import io.github.gardencompose.modifier.expandVertically
import io.github.gardencompose.modifier.margin
import org.gnome.adw.BreakpointCondition
import org.gnome.gtk.PolicyType

private val BREAKPOINT_CONDITION = BreakpointCondition.parse("min-width: 800px")

fun main(args: Array<String>) {
    adwApplication("my.example.hello-app", args) {
        ApplicationWindow(
            "Overlay",
            onClose = ::exitApplication,
            defaultWidth = 800,
            defaultHeight = 640,
        ) {
            val isBig by rememberBreakpoint(BREAKPOINT_CONDITION)
            OverlaySplitView(
                sidebar = {
                    VerticalBox {
                        HeaderBar(
                            title = { Label("Sidebar") },
                        )
                        Button(
                            label = "Hide sidebar",
                            modifier = Modifier.margin(8),
                            onClick = { hideSidebar() },
                        )
                    }
                },
                collapsed = !isBig,
            ) {
                VerticalBox {
                    HeaderBar(
                        title = { Label("Content") },
                        startWidgets = {
                            Button(if (showSidebar) "Hide sidebar" else "Show sidebar", onClick = {
                                if (showSidebar) {
                                    hideSidebar()
                                } else {
                                    showSidebar()
                                }
                            })
                        },
                    )
                    ScrolledWindow(
                        horizontalScrollbarPolicy = PolicyType.NEVER,
                        modifier = Modifier.expandVertically(),
                    ) {
                        VerticalBox(Modifier.margin(40), spacing = 40) {
                            repeat(10) { index ->
                                Frame {
                                    Label("Hello World#$index", Modifier.margin(10))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
