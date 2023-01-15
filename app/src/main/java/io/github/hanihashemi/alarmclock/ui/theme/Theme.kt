package io.github.hanihashemi.alarmclock.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.ViewCompat

private val LightColorScheme = lightColorScheme(
    primary = PrimaryColor,
    surface = SurfaceColor,
    onBackground = OnBackgroundColor,
    secondaryContainer = SecondaryContainerColor,
    onSurfaceVariant = OnSurfaceVariantColor,
)

@Composable
fun AlarmClockTheme(
    content: @Composable () -> Unit
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            (view.context as Activity).window.statusBarColor = LightColorScheme.primary.toArgb()
            (view.context as Activity).window.navigationBarColor =
                AndroidNavigationBarColor.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = false
        }
    }

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography,
        content = content
    )
}
