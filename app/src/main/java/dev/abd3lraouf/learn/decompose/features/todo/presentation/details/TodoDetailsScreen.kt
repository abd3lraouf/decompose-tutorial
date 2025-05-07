package dev.abd3lraouf.learn.decompose.features.todo.presentation.details

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.woowla.compose.icon.collections.heroicons.Heroicons
import com.woowla.compose.icon.collections.heroicons.heroicons.Outline
import com.woowla.compose.icon.collections.heroicons.heroicons.Solid
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.ArrowLongRight
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.Calendar
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.Clock
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.ExclamationCircle
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.MinusCircle
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.PaperClip
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.Pencil
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.Play
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.Tag
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.Trash
import com.woowla.compose.icon.collections.heroicons.heroicons.solid.CheckCircle
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoItem
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoPriority
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoStatus
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoTag
import dev.abd3lraouf.learn.decompose.ui.components.PriorityIndicator
import dev.abd3lraouf.learn.decompose.ui.preview.PreviewTodoData
import dev.abd3lraouf.learn.decompose.ui.theme.DecomposeTutorialTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TodoDetailsScreen(
    todo: TodoItem,
    onDeleteClick: () -> Unit,
    onToggleCompleted: () -> Unit,
    onEditClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header card with title, status and completion toggle
        HeaderCard(
            todo = todo,
            onToggleCompleted = onToggleCompleted
        )
        
        // Todo status progress
        StatusProgressCard(todo = todo)
        
        // Description card
        if (todo.description.isNotEmpty()) {
            DetailsCard(
                title = "Description",
                icon = Heroicons.Outline.PaperClip,
                iconTint = MaterialTheme.colorScheme.primary
            ) {
                Text(
                    text = todo.description,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
        
        // Row with Priority and Deadline
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Priority card
            DetailsCard(
                title = "Priority",
                icon = getPriorityIcon(todo.priority),
                iconTint = getPriorityColor(todo.priority),
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    PriorityIndicator(priority = todo.priority, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = todo.priority.name,
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            }
            
            // Deadline card
            DetailsCard(
                title = "Deadline",
                icon = Heroicons.Outline.Calendar,
                iconTint = MaterialTheme.colorScheme.tertiary,
                modifier = Modifier.weight(1f)
            ) {
                todo.deadline?.let { deadline ->
                    val dateFormat = SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault())
                    Text(
                        text = dateFormat.format(Date(deadline)),
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(top = 8.dp)
                    )
                } ?: Text(
                    text = "No deadline set",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(top = 8.dp).alpha(0.7f)
                )
            }
        }
        
        // Tags card
        DetailsCard(
            title = "Tags",
            icon = Heroicons.Outline.Tag,
            iconTint = MaterialTheme.colorScheme.secondary
        ) {
            if (todo.tags.isNotEmpty()) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    todo.tags.forEach { tag ->
                        TodoTagChip(tag = tag)
                    }
                }
            } else {
                Text(
                    text = "No tags",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .alpha(0.7f)
                )
            }
        }
        
        Spacer(modifier = Modifier.weight(1f))
        
        // Action buttons
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Edit button
            OutlinedButton(
                onClick = onEditClick,
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Heroicons.Outline.Pencil,
                    contentDescription = "Edit",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Edit")
            }
            
            // Delete button
            Button(
                onClick = onDeleteClick,
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer
                )
            ) {
                Icon(
                    imageVector = Heroicons.Outline.Trash,
                    contentDescription = "Delete",
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Delete")
            }
        }
    }
}

@Composable
private fun HeaderCard(
    todo: TodoItem,
    onToggleCompleted: () -> Unit
) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                // Completion indicator
                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .background(
                            when {
                                todo.isCompleted -> MaterialTheme.colorScheme.primaryContainer
                                todo.status == TodoStatus.IN_PROGRESS -> Color(0xFFE6F4EA)
                                else -> MaterialTheme.colorScheme.surfaceVariant
                            }
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    when {
                        todo.isCompleted -> {
                            IconButton(onClick = onToggleCompleted) {
                                Icon(
                                    imageVector = Heroicons.Solid.CheckCircle,
                                    contentDescription = "Completed",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(26.dp)
                                )
                            }
                        }
                        todo.status == TodoStatus.IN_PROGRESS -> {
                            IconButton(onClick = onToggleCompleted) {
                                Icon(
                                    imageVector = Heroicons.Outline.Clock,
                                    contentDescription = "In Progress",
                                    tint = Color(0xFF34A853),
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                        else -> {
                            IconButton(onClick = onToggleCompleted) {
                                Icon(
                                    imageVector = Heroicons.Outline.Play,
                                    contentDescription = "Start Task",
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(24.dp)
                                )
                            }
                        }
                    }
                }
                
                Spacer(modifier = Modifier.width(16.dp))
                
                // Title
                Text(
                    text = todo.title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
            }
            
            // Status text
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Status: ",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = when (todo.status) {
                        TodoStatus.TODO -> "To Do"
                        TodoStatus.IN_PROGRESS -> "In Progress"
                        TodoStatus.DONE -> "Completed"
                    },
                    style = MaterialTheme.typography.titleMedium,
                    color = when (todo.status) {
                        TodoStatus.TODO -> MaterialTheme.colorScheme.outline
                        TodoStatus.IN_PROGRESS -> Color(0xFF34A853)
                        TodoStatus.DONE -> MaterialTheme.colorScheme.primary
                    }
                )
            }
        }
    }
}

@Composable
private fun StatusProgressCard(todo: TodoItem) {
    val statusProgress = when (todo.status) {
        TodoStatus.TODO -> 0.0f
        TodoStatus.IN_PROGRESS -> 0.5f
        TodoStatus.DONE -> 1.0f
    }
    
    val animatedProgress by animateFloatAsState(
        targetValue = statusProgress,
        label = "StatusProgress"
    )
    
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            LinearProgressIndicator(
                progress = { animatedProgress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
                color = MaterialTheme.colorScheme.primary,
                strokeCap = StrokeCap.Round
            )
            
            Spacer(modifier = Modifier.height(12.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                StatusStep(
                    label = "To Do",
                    isActive = true,
                    isCompleted = todo.status != TodoStatus.TODO
                )
                
                Icon(
                    imageVector = Heroicons.Outline.ArrowLongRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                )
                
                StatusStep(
                    label = "In Progress",
                    isActive = todo.status == TodoStatus.IN_PROGRESS || todo.status == TodoStatus.DONE,
                    isCompleted = todo.status == TodoStatus.DONE
                )
                
                Icon(
                    imageVector = Heroicons.Outline.ArrowLongRight,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                )
                
                StatusStep(
                    label = "Completed",
                    isActive = todo.status == TodoStatus.DONE,
                    isCompleted = todo.status == TodoStatus.DONE
                )
            }
        }
    }
}

@Composable
private fun StatusStep(
    label: String,
    isActive: Boolean,
    isCompleted: Boolean
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .clip(CircleShape)
                .background(
                    when {
                        isCompleted -> MaterialTheme.colorScheme.primary
                        isActive -> MaterialTheme.colorScheme.primaryContainer
                        else -> MaterialTheme.colorScheme.surfaceVariant
                    }
                ),
            contentAlignment = Alignment.Center
        ) {
            if (isCompleted) {
                Icon(
                    imageVector = Heroicons.Solid.CheckCircle,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall,
            fontWeight = if (isActive) FontWeight.SemiBold else FontWeight.Normal,
            color = if (isActive) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun DetailsCard(
    title: String,
    icon: Any,
    iconTint: Color = MaterialTheme.colorScheme.primary,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = icon as androidx.compose.ui.graphics.vector.ImageVector,
                    contentDescription = null,
                    tint = iconTint,
                    modifier = Modifier.size(20.dp)
                )
                
                Spacer(modifier = Modifier.width(8.dp))
                
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            
            content()
        }
    }
}

@Composable
private fun TodoTagChip(tag: TodoTag) {
    SuggestionChip(
        onClick = { /* No action on click */ },
        label = { Text(tag.name) },
        colors = SuggestionChipDefaults.suggestionChipColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.7f),
            labelColor = MaterialTheme.colorScheme.onSecondaryContainer
        ),
        icon = {
            Icon(
                imageVector = Heroicons.Outline.Tag,
                contentDescription = null,
                modifier = Modifier.size(16.dp)
            )
        }
    )
}

@Composable
private fun getPriorityIcon(priority: TodoPriority) = when (priority) {
    TodoPriority.High -> Heroicons.Outline.ExclamationCircle
    TodoPriority.Medium -> Heroicons.Outline.Clock
    TodoPriority.Low -> Heroicons.Outline.MinusCircle
}

@Composable
private fun getPriorityColor(priority: TodoPriority) = when (priority) {
    TodoPriority.High -> Color(0xFFF44336) // Red
    TodoPriority.Medium -> Color(0xFFFF9800) // Orange
    TodoPriority.Low -> Color(0xFF4CAF50) // Green
}

@Preview(showBackground = true)
@Composable
fun TodoDetailsScreenPreview() {
    DecomposeTutorialTheme {
        TodoDetailsScreen(
            todo = PreviewTodoData.todoItems[0],
            onDeleteClick = {},
            onToggleCompleted = {},
            onEditClick = {}
        )
    }
} 