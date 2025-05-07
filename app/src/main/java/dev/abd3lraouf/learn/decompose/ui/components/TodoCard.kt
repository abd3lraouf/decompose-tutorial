package dev.abd3lraouf.learn.decompose.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woowla.compose.icon.collections.heroicons.Heroicons
import com.woowla.compose.icon.collections.heroicons.heroicons.Outline
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.CalendarDays
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.Fire
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.Play
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoStatus
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoItem
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoPriority
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoTag
import dev.abd3lraouf.learn.decompose.ui.theme.DecomposeTutorialTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoCard(
    todo: TodoItem,
    onTodoClicked: () -> Unit,
    onCompletedChanged: () -> Unit,
    modifier: Modifier = Modifier
) {
    // Configure colors based on priority and category
    val priorityColor = when (todo.priority) {
        TodoPriority.High -> Color(0xFFF44336) // Red
        TodoPriority.Medium -> Color(0xFFFF9800) // Orange
        TodoPriority.Low -> Color(0xFF4CAF50) // Green
    }

    // Calculate opacity based on completion status
    val contentAlpha = if (todo.isCompleted) 0.7f else 1f

    ElevatedCard(
        onClick = onTodoClicked,
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(4.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = .5.dp)
    ) {
        Column(modifier.padding(16.dp)) {
            // Title and checkbox/play button based on status
            Row(
                modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically
            ) {
                // Show Play button for TODO items, and Checkbox for IN_PROGRESS and DONE items
                if (todo.status == TodoStatus.TODO) {
                    // Play button for TODO items
                    IconButton(
                        onClick = { onCompletedChanged() },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            imageVector = Heroicons.Outline.Play,
                            contentDescription = "Move to In Progress",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                } else {
                    // Checkbox for IN_PROGRESS and DONE items
                    Checkbox(
                        checked = todo.isCompleted,
                        onCheckedChange = { onCompletedChanged() },
                        colors = CheckboxDefaults.colors(
                            checkedColor = MaterialTheme.colorScheme.primary
                        ),
                        modifier = Modifier.size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontWeight = FontWeight.SemiBold, fontSize = 16.sp
                    ),
                    textDecoration = if (todo.isCompleted) TextDecoration.LineThrough else null,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.alpha(contentAlpha)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Description preview if available
            if (todo.description.isNotEmpty()) {
                Text(
                    text = todo.description,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.alpha(contentAlpha * 0.9f)
                )
            }

            // Metadata row with icons
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
            ) {
                // Due date with icon
                todo.deadline?.let { timestamp ->
                    val date = Date(timestamp)
                    val formattedDate =
                        SimpleDateFormat("d MMM, yyyy", Locale.getDefault()).format(date)

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .alpha(contentAlpha * 0.9f)
                            .align(Alignment.CenterStart)
                    ) {
                        Icon(
                            imageVector = Heroicons.Outline.CalendarDays,
                            contentDescription = "Due date",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(16.dp)
                        )

                        Spacer(modifier = Modifier.width(4.dp))

                        Text(
                            text = formattedDate,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }

                Row(
                    modifier = Modifier
                        .wrapContentSize()
                        .align(Alignment.CenterEnd),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Priority indicator with icon
                    Icon(
                        imageVector = Heroicons.Outline.Fire,
                        contentDescription = "Priority",
                        tint = priorityColor,
                        modifier = Modifier
                            .size(16.dp)
                            .alpha(contentAlpha)
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = when (todo.priority) {
                            TodoPriority.High -> "High"
                            TodoPriority.Medium -> "Medium"
                            TodoPriority.Low -> "Low"
                        },
                        style = MaterialTheme.typography.bodySmall,
                        color = priorityColor.copy(alpha = 0.9f)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun TodoCardPreview() {
    DecomposeTutorialTheme {
        TodoCard(
            todo = TodoItem(
            title = "Complete UI Design",
            description = "Finish the dashboard UI components and prepare for review",
            priority = TodoPriority.High,
            status = TodoStatus.TODO,
            deadline = System.currentTimeMillis() + 86400000,
            isCompleted = false
        ), onTodoClicked = {}, onCompletedChanged = {})
    }
}

@Preview
@Composable
fun CompletedTodoCardPreview() {
    DecomposeTutorialTheme {
        TodoCard(
            todo = TodoItem(
            title = "Research Material Design 3",
            description = "Review Material Design 3 for mobile applications",
            priority = TodoPriority.Medium,
            status = TodoStatus.DONE,
            deadline = System.currentTimeMillis() - 86400000,
            isCompleted = true
        ), onTodoClicked = {}, onCompletedChanged = {})
    }
}

@Preview
@Composable
fun InProgressTodoCardPreview() {
    DecomposeTutorialTheme {
        TodoCard(
            todo = TodoItem(
            title = "Read 30 Pages",
            description = "Read the assigned textbook chapter",
            priority = TodoPriority.Medium,
            status = TodoStatus.IN_PROGRESS,
            deadline = System.currentTimeMillis() + 86400000,
            isCompleted = false
        ), onTodoClicked = {}, onCompletedChanged = {})
    }
} 