import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.mutableStateSetOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.setValue
import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.adw.components.HeaderBar
import io.github.gardencompose.gtk.components.Box
import io.github.gardencompose.gtk.components.Button
import io.github.gardencompose.gtk.components.ColumnView
import io.github.gardencompose.gtk.components.ControlledColumnView
import io.github.gardencompose.gtk.components.Label
import io.github.gardencompose.gtk.components.ScrolledWindow
import io.github.gardencompose.gtk.components.ToggleButton
import io.github.gardencompose.gtk.components.VerticalBox
import io.github.gardencompose.gtk.components.createListItemFactory
import io.github.gardencompose.gtk.components.rememberSingleSelectionModel
import io.github.gardencompose.modifier.Modifier
import io.github.gardencompose.modifier.cssClasses
import io.github.gardencompose.modifier.expand
import io.github.gardencompose.modifier.margin
import org.gnome.gobject.GObject
import org.gnome.gtk.ColumnViewColumn

fun main(args: Array<String>) {
    adwApplication("my.example.hello-app", args) {
        ApplicationWindow(
            title = "Column View",
            onClose = ::exitApplication,
            defaultWidth = 1200,
            defaultHeight = 600,
        ) {
            VerticalBox {
                HeaderBar(modifier = Modifier.cssClasses("flat"))

                val compositionContext = rememberCompositionContext()

                var firstColumnVisible by remember { mutableStateOf(true) }

                val columns = remember {
                    mutableStateListOf(
                        ColumnViewColumn.builder()
                            .setTitle("Title")
                            .setExpand(true)
                            .setVisible(firstColumnVisible)
                            .setFactory(
                                createListItemFactory<ColumnItem>(compositionContext) { item ->
                                    Label(item.title)
                                },
                            )
                            .build(),
                        ColumnViewColumn.builder()
                            .setTitle("Author")
                            .setExpand(true)
                            .setFactory(
                                createListItemFactory<ColumnItem>(compositionContext) { item ->
                                    Label(item.author)
                                },
                            )
                            .build(),
                        ColumnViewColumn.builder()
                            .setTitle("Year")
                            .setExpand(true)
                            .setFactory(
                                createListItemFactory<ColumnItem>(compositionContext) { item ->
                                    Label(item.year.toString())
                                },
                            )
                            .build(),
                    )
                }

                val items = remember {
                    mutableStateListOf(
                        ColumnItem("Winds from Afar", "Kenji Miyazawa", 1972),
                        ColumnItem("Like Water for Chocolate", "Laura Esquivel", 1989),
                        ColumnItem("Works and Nights", "Alejandra Pizarnik", 1965),
                        ColumnItem("Understading Analysis", "Stephen Abbott", 2002),
                        ColumnItem("The Timeless Way of Building", "Cristopher Alexander", 1979),
                        ColumnItem("Bitter", "Akwaeke Emezi", 2022),
                        ColumnItem("Saying Yes", "Griselda Gambaro", 1981),
                        ColumnItem("Itinerary of a Dramatist", "Rodolfo Usigli", 1940),
                    )
                }

                Box {
                    VerticalBox(spacing = 8) {
                        Panel(title = "Uncontrolled") {
                            val model = rememberSingleSelectionModel(items)
                            ColumnView(
                                columns = columns,
                                model = model,
                                showRowSeparators = true,
                                showColumnSeparators = true,
                                onActivate = { println("Activated row: $it") },
                            )
                        }
                        VerticalBox(modifier = Modifier.margin(8), spacing = 8) {
                            Button(
                                label = "Add Column",
                                onClick = {
                                    columns.add(
                                        ColumnViewColumn(
                                            columns[columns.size % 3].title,
                                            columns[columns.size % 3].factory,
                                        ),
                                    )
                                },
                            )
                            Button(
                                label = "Remove Column",
                                onClick = {
                                    if (columns.size > 3) {
                                        columns.remove(columns.last())
                                    }
                                },
                            )
                            ToggleButton(
                                active = !firstColumnVisible,
                                label = "Hide First Column",
                                onToggle = {
                                    firstColumnVisible = !firstColumnVisible
                                    columns[0].visible = firstColumnVisible
                                },
                            )
                        }
                    }

                    Panel(
                        title = "Controlled Multi Selection",
                    ) {
                        // Copies the original columns.
                        // Columns can't be assigned to more than one column view.
                        val controlledColumns = remember { copyColumns(columns) }
                        val selectedItems = remember { mutableStateSetOf<Int>() }
                        ControlledColumnView(
                            columns = controlledColumns,
                            items = items,
                            selectedItems = selectedItems,
                            onSelectionChange = {
                                selectedItems.clear()
                                selectedItems.addAll(it)
                            },
                        )
                    }

                    Panel(
                        title = "Controlled Single Selection",
                    ) {
                        val controlledColumns = remember { copyColumns(columns) }
                        var selectedItem by remember { mutableStateOf<Int?>(null) }
                        ControlledColumnView(
                            columns = controlledColumns,
                            items = items,
                            selectedItem = selectedItem,
                            onSelectionChange = { selectedItem = it },
                        )
                    }

                    Panel(
                        title = "Controlled No Selection",
                    ) {
                        val controlledColumns = remember { copyColumns(columns) }
                        ControlledColumnView(
                            columns = controlledColumns,
                            items = items,
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun Panel(title: String, child: @Composable () -> Unit) {
    VerticalBox(modifier = Modifier.expand(true)) {
        HeaderBar(title = { Label(text = title) }, showEndTitleButtons = false)
        ScrolledWindow(
            hasFrame = true,
            modifier = Modifier.expand(true),
            propagateNaturalWidth = true,
            propagateNaturalHeight = true,
        ) {
            child()
        }
    }
}

private fun copyColumns(columns: List<ColumnViewColumn>): List<ColumnViewColumn> {
    return columns.map {
        ColumnViewColumn.builder()
            .setTitle(it.title)
            .setExpand(true)
            .setFactory(it.factory)
            .build()
    }
}

private data class ColumnItem(val title: String, val author: String, val year: Int) : GObject()
