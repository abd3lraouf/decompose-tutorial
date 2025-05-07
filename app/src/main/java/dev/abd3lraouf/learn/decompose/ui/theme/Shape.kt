package dev.abd3lraouf.learn.decompose.ui.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerBasedShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.dp

class JetcasterShapes(
    val small: CornerBasedShape = RoundedCornerShape(4.dp),
    val medium: CornerBasedShape = RoundedCornerShape(8.dp),
    val large: CornerBasedShape = RoundedCornerShape(16.dp),
    val extraLarge: CornerBasedShape = RoundedCornerShape(24.dp),
    val card: CornerBasedShape = RoundedCornerShape(16.dp),
    val bottomSheet: CornerBasedShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
    val button: CornerBasedShape = RoundedCornerShape(50),
    val fab: CornerBasedShape = CircleShape,
    val chip: CornerBasedShape = RoundedCornerShape(8.dp)
)

val LocalJetcasterShapes = staticCompositionLocalOf { JetcasterShapes() }

val MaterialTheme.jetcasterShapes: JetcasterShapes
    @Composable
    @ReadOnlyComposable
    get() = LocalJetcasterShapes.current 