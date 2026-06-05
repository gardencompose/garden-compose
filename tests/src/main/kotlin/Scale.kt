import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.adw.components.HeaderBar
import io.github.gardencompose.adw.components.StatusPage
import io.github.gardencompose.gtk.ImageSource
import io.github.gardencompose.gtk.components.HorizontalBox
import io.github.gardencompose.gtk.components.IconButton
import io.github.gardencompose.gtk.components.Mark
import io.github.gardencompose.gtk.components.Scale
import io.github.gardencompose.gtk.components.VerticalBox
import io.github.gardencompose.modifier.Modifier
import io.github.gardencompose.modifier.alignment
import io.github.gardencompose.modifier.cssClasses
import io.github.gardencompose.useGioResource
import org.gnome.gtk.Align
import org.gnome.gtk.PositionType

fun main(args: Array<String>) {
    useGioResource("resources.gresource") {
        adwApplication("my.example.hello-app", args) {
            ApplicationWindow(title = "Scale", onClose = ::exitApplication) {
                VerticalBox {
                    HeaderBar(modifier = Modifier.cssClasses("flat"))

                    var scaleValue by remember { mutableDoubleStateOf(25.5) }

                    val marks = remember {
                        mutableStateListOf(
                            Mark(15.0, PositionType.BOTTOM, "15"),
                            Mark(40.0, PositionType.BOTTOM, "40"),
                            Mark(100.0, PositionType.BOTTOM, "100"),
                        )
                    }

                    StatusPage(
                        title = "Scale",
                        description = "Slider control to select a value from a range",
                    ) {
                        VerticalBox(
                            spacing = 16,
                        ) {
                            Scale(
                                value = scaleValue,
                                onChange = { scrollType, newValue ->
                                    println(newValue)
                                    scaleValue = newValue
                                },
                                lower = -10.0,
                                upper = 110.0,
                                digits = 2,
                                drawValue = true,
                                fillLevel = 90.0,
                                stepIncrements = 10.0,
                                pageIncrements = 25.0,
                                showFillLevel = true,
                                marks = marks.toList(),
                            )

                            HorizontalBox(
                                spacing = 8,
                                modifier = Modifier.alignment(Align.CENTER),
                            ) {
                                IconButton(ImageSource.Icon("left-small-symbolic"), {
                                    val newValue = marks[1].value - 5
                                    marks[1] = Mark(newValue, marks[1].position, newValue.toInt().toString())
                                })
                                IconButton(ImageSource.Icon("right-small-symbolic"), {
                                    val newValue = marks[1].value + 5
                                    marks[1] = Mark(newValue, marks[1].position, newValue.toInt().toString())
                                })
                            }
                        }
                    }
                }
            }
        }
    }
}
