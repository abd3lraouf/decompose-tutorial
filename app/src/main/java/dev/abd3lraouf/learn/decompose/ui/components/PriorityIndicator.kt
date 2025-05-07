package dev.abd3lraouf.learn.decompose.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoPriority
import dev.abd3lraouf.learn.decompose.ui.theme.DecomposeTutorialTheme

/**
 * A visual indicator for the priority level
 */
@Composable
fun PriorityIndicator(
    priority: TodoPriority,
    modifier: Modifier = Modifier
) {
    val color = when (priority) {
        TodoPriority.High -> Color(0xFFF44336) // Red
        TodoPriority.Medium -> Color(0xFFFF9800) // Orange
        TodoPriority.Low -> Color(0xFF4CAF50) // Green
    }
    
    val strokeWidth = when (priority) {
        TodoPriority.High -> 3.dp
        TodoPriority.Medium -> 2.dp
        TodoPriority.Low -> 1.5.dp
    }
    
    Canvas(
        modifier = modifier.size(12.dp)
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val radius = size.minDimension / 2
        
        // Filled circle with different colors based on priority
        drawCircle(
            color = color,
            radius = radius,
            center = center
        )
        
        // Border for high priority
        if (priority == TodoPriority.High) {
            drawCircle(
                color = color.copy(alpha = 0.5f),
                radius = radius * 1.4f,
                center = center,
                style = Stroke(width = strokeWidth.toPx())
            )
        }
    }
}

@Composable
fun PriorityButton(
    priority: TodoPriority,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val color = when (priority) {
        TodoPriority.High -> Color(0xFFF44336) // Red
        TodoPriority.Medium -> Color(0xFFFF9800) // Orange
        TodoPriority.Low -> Color(0xFF4CAF50) // Green
    }
    
    val bgColor = if (selected) color.copy(alpha = 0.2f) else Color.Transparent
    val borderColor = if (selected) color else Color.Gray.copy(alpha = 0.5f)
    
    Button(
        onClick = onClick,
        modifier = modifier
            .border(
                width = 1.dp,
                color = borderColor,
                shape = RoundedCornerShape(8.dp)
            ),
        colors = ButtonDefaults.buttonColors(
            containerColor = bgColor,
            contentColor = if (selected) color else Color.Gray
        )
    ) {
        Text(
            text = when (priority) {
                TodoPriority.High -> "High"
                TodoPriority.Medium -> "Medium"
                TodoPriority.Low -> "Low"
            }
        )
    }
}

@Preview
@Composable
fun PriorityIndicatorPreview() {
    DecomposeTutorialTheme {
        PriorityIndicator(
            priority = TodoPriority.High,
            modifier = Modifier.size(20.dp)
        )
    }
} 