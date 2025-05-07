package dev.abd3lraouf.learn.decompose.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF1B6D29),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFA9F4A2),
    onPrimaryContainer = Color(0xFF002105),
    secondary = Color(0xFF526350),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFD5E8D0),
    onSecondaryContainer = Color(0xFF101F10),
    tertiary = Color(0xFF39656B),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFBDEBF2),
    onTertiaryContainer = Color(0xFF001F23),
    error = Color(0xFFBA1A1A),
    background = Color(0xFFFBFDF6),
    surface = Color(0xFFFBFDF6),
    surfaceVariant = Color(0xFFDEE4D7),
    onSurfaceVariant = Color(0xFF42483F),
    surfaceTint = Color(0xFF1B6D29),
    outlineVariant = Color(0xFFC2C8BB)
)

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF8DDA88),
    onPrimary = Color(0xFF00390C),
    primaryContainer = Color(0xFF055318),
    onPrimaryContainer = Color(0xFFA9F4A2),
    secondary = Color(0xFFB9CCB4),
    onSecondary = Color(0xFF253423),
    secondaryContainer = Color(0xFF3B4B39),
    onSecondaryContainer = Color(0xFFD5E8D0),
    tertiary = Color(0xFFA1CED5),
    onTertiary = Color(0xFF00363C),
    tertiaryContainer = Color(0xFF1F4D53),
    onTertiaryContainer = Color(0xFFBDEBF2),
    error = Color(0xFFFFB4AB),
    background = Color(0xFF191C18),
    surface = Color(0xFF191C18),
    surfaceVariant = Color(0xFF42483F),
    onSurfaceVariant = Color(0xFFC2C8BB),
    surfaceTint = Color(0xFF8DDA88),
    outlineVariant = Color(0xFF42483F)
)

@Composable
fun DecomposeTutorialTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = Color.Transparent.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    // Custom shapes based on Jetcaster
    val shapes = JetcasterShapes()
    
    // Apply elevations to surfaces
    val elevations = Elevations(
        card = 3.dp,
        dialog = 6.dp
    )

    CompositionLocalProvider(
        LocalJetcasterShapes provides shapes
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            content = content
        )
    }
}

// Material3 Elevations class for consistent elevations
class Elevations(
    val card: androidx.compose.ui.unit.Dp,
    val dialog: androidx.compose.ui.unit.Dp
)

val MaterialTheme.Elevations: Elevations
    @Composable
    get() = Elevations(
        card = 2.dp,
        dialog = 3.dp
    )