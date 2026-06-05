import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import io.github.gardencompose.adw.adwApplication
import io.github.gardencompose.adw.components.ApplicationWindow
import io.github.gardencompose.adw.components.ExpanderRow
import io.github.gardencompose.adw.components.HeaderBar
import io.github.gardencompose.adw.components.PreferencesGroup
import io.github.gardencompose.adw.components.SwitchRow
import io.github.gardencompose.gtk.components.VerticalBox
import io.github.gardencompose.modifier.Modifier
import io.github.gardencompose.modifier.margin

fun main(args: Array<String>) {
    adwApplication("my.example.hello-app", args) {
        ApplicationWindow(title = "Expander Row", onClose = ::exitApplication) {
            VerticalBox {
                HeaderBar()

                var expanded by remember { mutableStateOf(false) }
                var expansionEnabled by remember { mutableStateOf(true) }

                var useEncryptionSetting by remember { mutableStateOf(false) }

                PreferencesGroup(
                    title = "Settings",
                    modifier = Modifier.margin(8),
                    description = "A bunch of settings for your app",
                ) {
                    ExpanderRow(
                        expanded = expanded,
                        title = "Enable Backups",
                        enableExpansion = expansionEnabled,
                        showEnableSwitch = true,
                        onEnableExpansion = { expansionEnabled = !expansionEnabled },
                        onExpand = { expanded = !expanded },
                    ) {
                        SwitchRow(
                            active = useEncryptionSetting,
                            title = "Use Encryption",
                            onActivate = { useEncryptionSetting = !useEncryptionSetting },
                        )
                    }
                }
            }
        }
    }
}
