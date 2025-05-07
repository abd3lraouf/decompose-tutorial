package dev.abd3lraouf.learn.decompose.navigation.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.abd3lraouf.learn.decompose.ui.theme.jetcasterShapes

@Composable
fun NavigationButton(
    isSelected: Boolean,
    icon: @Composable () -> Unit,
    label: @Composable () -> Unit,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val interactionSource = remember { MutableInteractionSource() }
    val scale = animateFloatAsState(
        targetValue = if (isSelected) 1.1f else 1.0f,
        animationSpec = tween(durationMillis = 300),
        label = "Button scale animation"
    )

    val textStyle = if (isSelected) {
        MaterialTheme.typography.labelMedium.copy(
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.primary
        )
    } else {
        MaterialTheme.typography.labelSmall.copy(
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
        )
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable(
                interactionSource = interactionSource,
                indication = null,
                onClick = onClick
            )
            .padding(vertical = 8.dp, horizontal = 16.dp)
    ) {
        // Icon with background
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .size(48.dp)
                .graphicsLayer {
                    scaleX = scale.value
                    scaleY = scale.value
                }
        ) {
            // Background indicator when selected
            if (isSelected) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.6f))
                )
            }

            // Icon
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(40.dp)
                    .padding(8.dp)
            ) {
                icon()
            }
        }

        Spacer(modifier = Modifier.height(4.dp))

        // Label
        label()

        // Selection indicator
        if (isSelected) {
            Spacer(modifier = Modifier.height(4.dp))
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(3.dp)
                    .clip(MaterialTheme.jetcasterShapes.small)
                    .background(MaterialTheme.colorScheme.primary)
            )
        } else {
            Spacer(modifier = Modifier.height(7.dp))  // Balance height when no indicator
        }
    }
} 