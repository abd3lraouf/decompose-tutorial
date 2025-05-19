package dev.abd3lraouf.learn.decompose.features.todo.presentation.edit.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woowla.compose.icon.collections.heroicons.Heroicons
import com.woowla.compose.icon.collections.heroicons.heroicons.Outline
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.Tag
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoPriority
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoStatus
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoTag
import dev.abd3lraouf.learn.decompose.ui.components.TodoDatePickerDialog
import dev.abd3lraouf.learn.decompose.ui.theme.DecomposeTutorialTheme
import dev.abd3lraouf.learn.decompose.ui.theme.TodoColors
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EditTaskScreen(
    title: String,
    description: String,
    priority: TodoPriority,
    status: TodoStatus,
    deadline: Long?,
    tags: Set<TodoTag>,
    onTitleChanged: (String) -> Unit,
    onDescriptionChanged: (String) -> Unit,
    onPriorityChanged: (TodoPriority) -> Unit,
    onStatusChanged: (TodoStatus) -> Unit,
    onDueDateChanged: (Long?) -> Unit,
    onTagsChanged: (Set<TodoTag>) -> Unit,
    onSaveClick: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showTagsDialog by remember { mutableStateOf(false) }
    var showStatusMenu by remember { mutableStateOf(false) }

    val dateFormatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    val formattedDate = deadline?.let { dateFormatter.format(Date(it)) } ?: "No deadline set"

    if (showDatePicker) {
        TodoDatePickerDialog(
            onDateSelected = { selectedDate ->
                onDueDateChanged(selectedDate)
            },
            onDismissRequest = { showDatePicker = false }
        )
    }

    // Tags selection dialog
    if (showTagsDialog) {
        TagSelectionDialog(
            selectedTags = tags,
            onTagsSelected = { newTags ->
                onTagsChanged(newTags)
                showTagsDialog = false
            },
            onDismiss = { showTagsDialog = false }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title section
        Text(text = "Title", style = MaterialTheme.typography.bodyLarge)
        OutlinedTextField(
            value = title,
            onValueChange = onTitleChanged,
            placeholder = { Text("Enter task title") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        )

        // Description section
        Text(text = "Description", style = MaterialTheme.typography.bodyLarge)
        OutlinedTextField(
            value = description,
            onValueChange = onDescriptionChanged,
            placeholder = { Text("Enter Description here....") },
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            shape = RoundedCornerShape(8.dp)
        )

        // Status section
        Text(text = "Status", style = MaterialTheme.typography.bodyLarge)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            FilledTonalButton(
                onClick = { showStatusMenu = true },
                modifier = Modifier.weight(1f)
            ) {
                Text(status.name)
            }

            DropdownMenu(
                expanded = showStatusMenu,
                onDismissRequest = { showStatusMenu = false }
            ) {
                TodoStatus.values().forEach { statusOption ->
                    DropdownMenuItem(
                        text = { Text(statusOption.name) },
                        onClick = {
                            onStatusChanged(statusOption)
                            showStatusMenu = false
                        }
                    )
                }
            }
        }

        // Tags section
        Text(text = "Tags", style = MaterialTheme.typography.bodyLarge)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            FilledTonalButton(
                onClick = { showTagsDialog = true },
                modifier = Modifier.weight(1f)
            ) {
                Icon(
                    imageVector = Heroicons.Outline.Tag,
                    contentDescription = "Select Tags",
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Select Tags")
            }

            Spacer(modifier = Modifier.width(8.dp))

            // Show selected tags count
            Text(
                text = "${tags.size} selected",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Display selected tags
        if (tags.isNotEmpty()) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tags.forEach { tag ->
                    Surface(
                        modifier = Modifier.clip(RoundedCornerShape(16.dp)),
                        color = MaterialTheme.colorScheme.secondaryContainer
                    ) {
                        Text(
                            text = tag.name,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp)
                        )
                    }
                }
            }
        }

        // Deadline section
        Text(text = "Deadline", style = MaterialTheme.typography.bodyLarge)
        OutlinedTextField(
            value = formattedDate,
            onValueChange = { },
            readOnly = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Date",
                    tint = TodoColors.Purple
                )
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDatePicker = true },
            shape = RoundedCornerShape(8.dp)
        )

        // Priority section
        Text(text = "Set Priority", style = MaterialTheme.typography.bodyLarge)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            TodoPriority.values().forEach { priorityOption ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .weight(1f)
                        .clickable { onPriorityChanged(priorityOption) }
                ) {
                    RadioButton(
                        selected = priority == priorityOption,
                        onClick = { onPriorityChanged(priorityOption) },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = TodoColors.Purple
                        )
                    )
                    Text(text = priorityOption.name)
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Save button
        Button(
            onClick = onSaveClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = TodoColors.Purple
            )
        ) {
            Text("Save Changes")
        }
    }
}

@Composable
fun TagSelectionDialog(
    selectedTags: Set<TodoTag>,
    onTagsSelected: (Set<TodoTag>) -> Unit,
    onDismiss: () -> Unit
) {
    val tagOptions = TodoTag.values()
    val selectedTagsState = remember { mutableStateOf(selectedTags) }

    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Tags") },
        text = {
            Column {
                TagList(
                    allTags = tagOptions.toList(),
                    selectedTags = selectedTagsState.value,
                    onTagSelected = { tag, selected ->
                        selectedTagsState.value = if (selected) {
                            selectedTagsState.value + tag
                        } else {
                            selectedTagsState.value - tag
                        }
                    }
                )
            }
        },
        confirmButton = {
            TextButton(onClick = { onTagsSelected(selectedTagsState.value) }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}

@Composable
fun TagList(
    allTags: List<TodoTag>,
    selectedTags: Set<TodoTag>,
    onTagSelected: (TodoTag, Boolean) -> Unit
) {
    Column {
        allTags.forEach { tag ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable {
                        onTagSelected(tag, !selectedTags.contains(tag))
                    }
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                androidx.compose.material3.Checkbox(
                    checked = selectedTags.contains(tag),
                    onCheckedChange = { isChecked ->
                        onTagSelected(tag, isChecked)
                    }
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = tag.name)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun EditTaskScreenPreview() {
    DecomposeTutorialTheme {
        EditTaskScreen(
            title = "Edit Task",
            description = "This is a sample task description for preview",
            priority = TodoPriority.Medium,
            status = TodoStatus.IN_PROGRESS,
            deadline = System.currentTimeMillis(),
            tags = setOf(TodoTag.WORK, TodoTag.PROJECT),
            onTitleChanged = {},
            onDescriptionChanged = {},
            onPriorityChanged = {},
            onStatusChanged = {},
            onDueDateChanged = {},
            onTagsChanged = {},
            onSaveClick = {},
            onBackClick = {}
        )
    }
} 