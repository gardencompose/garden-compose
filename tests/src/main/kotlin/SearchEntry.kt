import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.adw.components.HeaderBar
import io.github.gardencompose.adw.components.ShortcutLabel
import io.github.gardencompose.gtk.components.ControlledListView
import io.github.gardencompose.gtk.components.HorizontalBox
import io.github.gardencompose.gtk.components.Label
import io.github.gardencompose.gtk.components.SearchEntry
import io.github.gardencompose.gtk.components.VerticalBox
import io.github.gardencompose.modifier.Modifier
import io.github.gardencompose.modifier.cssClasses
import io.github.gardencompose.modifier.margin
import org.gnome.gobject.GObject

fun main(args: Array<String>) {
    adwApplication("my.example.hello-app", args) {
        ApplicationWindow(title = "Search Entry", onClose = ::exitApplication) {
            VerticalBox {
                HeaderBar(modifier = Modifier.cssClasses("flat"))

                val fruits = remember {
                    mutableStateListOf(
                        Fruit("\uD83C\uDF4E Apple"),
                        Fruit("\uD83C\uDF50 Pear"),
                        Fruit("\uD83C\uDF4C Banana"),
                        Fruit("\uD83E\uDD6D Mango"),
                        Fruit("\uD83C\uDF51 Peach"),
                        Fruit("\uD83E\uDD5D Kiwi"),
                        Fruit("\uD83C\uDF4A Tangerine"),
                        Fruit("\uD83C\uDF4B Lemon"),
                        Fruit("\uD83C\uDF47 Grapes"),
                        Fruit("\uD83C\uDF52 Cherries"),
                        Fruit("\uD83C\uDF4D Pineapple"),
                    )
                }

                var text by remember { mutableStateOf("") }
                var searchedText by remember { mutableStateOf(text) }

                val searchedFruits = fruits.filter { it.name.contains(searchedText, true) }
                var selectedFruitIndex by remember { mutableStateOf<Int?>(null) }

                VerticalBox(modifier = Modifier.margin(8), spacing = 8) {
                    SearchEntry(
                        text = text,
                        onTextChange = { text = it },
                        placeholderText = "Search fruits...",
                        onSearchChange = {
                            selectedFruitIndex = null
                            searchedText = text
                        },
                        onNextMatch = {
                            if (selectedFruitIndex == null && searchedFruits.isNotEmpty()) {
                                selectedFruitIndex = 0
                            } else {
                                selectedFruitIndex = (selectedFruitIndex!! + 1).coerceIn(searchedFruits.indices)
                            }
                        },
                        onPreviousMatch = {
                            if (selectedFruitIndex == null && searchedFruits.isNotEmpty()) {
                                selectedFruitIndex = 0
                            } else {
                                selectedFruitIndex = (selectedFruitIndex!! - 1).coerceIn(searchedFruits.indices)
                            }
                        },
                    )

                    HorizontalBox(spacing = 8) {
                        Label(text = "Next Match")
                        ShortcutLabel(accelerator = "<Control>G", disabledText = "Not set")
                    }
                    HorizontalBox(spacing = 8) {
                        Label(text = "Previous Match")
                        ShortcutLabel(accelerator = "<Control><Shift>G", disabledText = "Not set")
                    }

                    ControlledListView(items = searchedFruits, selectedItem = selectedFruitIndex) {
                        Label(text = it.name)
                    }
                }
            }
        }
    }
}

private data class Fruit(val name: String) : GObject()
