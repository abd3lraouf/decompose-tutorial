package dev.abd3lraouf.learn.decompose.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woowla.compose.icon.collections.heroicons.Heroicons
import com.woowla.compose.icon.collections.heroicons.heroicons.Outline
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.ClipboardDocumentList
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.ExclamationTriangle
import dev.abd3lraouf.learn.decompose.ui.theme.DecomposeTutorialTheme

@Composable
fun EmptyStateView(
    title: String,
    message: String,
    icon: ImageVector = Heroicons.Outline.ClipboardDocumentList,
    actionLabel: String? = null,
    onActionClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val animatedVisibilityState = remember { MutableTransitionState(false).apply { targetState = true } }
    
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        AnimatedVisibility(
            visibleState = animatedVisibilityState,
            enter = fadeIn(animationSpec = tween(600)) +
                    slideInVertically(
                        animationSpec = tween(600),
                        initialOffsetY = { it / 3 }
                    )
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.padding(24.dp)
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.7f),
                    modifier = Modifier.size(72.dp)
                )
                
                Spacer(modifier = Modifier.height(16.dp))
                
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onSurface
                )
                
                Spacer(modifier = Modifier.height(8.dp))
                
                Text(
                    text = message,
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                
                if (actionLabel != null && onActionClick != null) {
                    Spacer(modifier = Modifier.height(24.dp))
                    
                    Button(
                        onClick = onActionClick
                    ) {
                        Text(text = actionLabel)
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EmptyStatePreview() {
    DecomposeTutorialTheme {
        EmptyStateView(
            title = "No Todos Yet",
            message = "Create your first todo to get started organizing your tasks efficiently.",
            actionLabel = "Create Todo",
            onActionClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ErrorStatePreview() {
    DecomposeTutorialTheme {
        EmptyStateView(
            title = "Something Went Wrong",
            message = "We couldn't load your todos. Please try again later.",
            icon = Heroicons.Outline.ExclamationTriangle,
            actionLabel = "Try Again",
            onActionClick = { }
        )
    }
} 