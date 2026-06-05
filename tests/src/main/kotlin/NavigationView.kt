import androidx.compose.runtime.Composable
import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.adw.components.HeaderBar
import io.github.gardencompose.adw.components.NavigationPage
import io.github.gardencompose.adw.components.NavigationView
import io.github.gardencompose.adw.components.NavigationViewState
import io.github.gardencompose.adw.components.rememberNavigationViewState
import io.github.gardencompose.gtk.components.Button
import io.github.gardencompose.gtk.components.Label
import io.github.gardencompose.gtk.components.VerticalBox
import io.github.gardencompose.modifier.Modifier
import io.github.gardencompose.modifier.cssClasses
import io.github.gardencompose.modifier.margin

fun main(args: Array<String>) {
    adwApplication("my.example.hello-app", args) {
        ApplicationWindow(title = "Navigation View", onClose = ::exitApplication) {
            VerticalBox {
                HeaderBar(modifier = Modifier.cssClasses("flat"))

                val navigationViewState = rememberNavigationViewState()

                VerticalBox(
                    modifier = Modifier.cssClasses("card"),
                ) {
                    NavigationView(
                        state = navigationViewState,
                        verticallyHomogenous = true,
                        modifier = Modifier.margin(16),
                    ) {
                        NavigationPage(title = "Page 1", tag = "page1") {
                            VerticalBox {
                                PageOne(navigationViewState)
                            }
                        }
                        NavigationPage(title = "Page 2", tag = "page2") {
                            PageTwo(navigationViewState)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun PageOne(navigationViewState: NavigationViewState) {
    VerticalBox(
        spacing = 8,
    ) {
        Label(text = "Welcome to page 1!")
        Button(label = "Go to page 2", onClick = {
            navigationViewState.pushByTag("page2")
        })
    }
}

@Composable
fun PageTwo(navigationViewState: NavigationViewState) {
    Button(label = "Go back", onClick = {
        navigationViewState.pop()
    })
}
