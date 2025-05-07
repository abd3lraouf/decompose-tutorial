package dev.abd3lraouf.learn.decompose.features.todo.presentation.create

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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
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
import dev.abd3lraouf.learn.decompose.ui.theme.TodoColors
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun CreateTaskScreen(
    onSaveClick: (String, String, TodoStatus, Long, TodoPriority, Set<TodoTag>) -> Unit = { _, _, _, _, _, _ -> },
    modifier: Modifier = Modifier
) {
    var title by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var selectedStatus by remember { mutableStateOf(TodoStatus.TODO) }
    var deadline by remember { mutableStateOf(System.currentTimeMillis()) }
    var priority by remember { mutableStateOf(TodoPriority.Low) }
    var showDatePicker by remember { mutableStateOf(false) }

    // New state for tags
    var selectedTags by remember { mutableStateOf<Set<TodoTag>>(emptySet()) }
    var showTagsDialog by remember { mutableStateOf(false) }

    val dateFormatter = SimpleDateFormat("MM/dd/yyyy", Locale.getDefault())
    val formattedDate = dateFormatter.format(Date(deadline))

    if (showDatePicker) {
        TodoDatePickerDialog(
            onDateSelected = { selectedDate ->
                deadline = selectedDate
            },
            onDismissRequest = { showDatePicker = false }
        )
    }

    // Tags selection dialog
    if (showTagsDialog) {
        TagSelectionDialog(
            selectedTags = selectedTags,
            onTagsSelected = { tags ->
                selectedTags = tags
                showTagsDialog = false
            },
            onDismiss = { showTagsDialog = false }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Title section
        Text(text = "Title", style = MaterialTheme.typography.bodyLarge)
        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            placeholder = { Text("Enter task title") },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(8.dp)
        )

        // Description section
        Text(text = "Description", style = MaterialTheme.typography.bodyLarge)
        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            placeholder = { Text("Enter Description here....") },
            modifier = Modifier
                .fillMaxWidth()
                .height(140.dp),
            shape = RoundedCornerShape(8.dp)
        )

        // Tags section (new)
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
                text = "${selectedTags.size} selected",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        // Display selected tags
        if (selectedTags.isNotEmpty()) {
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                selectedTags.forEach { tag ->
                    Surface(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp)),
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
                        .clickable { priority = priorityOption }
                ) {
                    RadioButton(
                        selected = priority == priorityOption,
                        onClick = { priority = priorityOption },
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
            onClick = {
                if (title.isNotBlank()) {
                    onSaveClick(
                        title,
                        description,
                        selectedStatus,
                        deadline,
                        priority,
                        selectedTags
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(28.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = TodoColors.Purple
            )
        ) {
            Text("Save Task!")
        }
    }
}

@Composable
fun TagSelectionDialog(
    selectedTags: Set<TodoTag>,
    onTagsSelected: (Set<TodoTag>) -> Unit,
    onDismiss: () -> Unit
) {
    val tags = remember { TodoTag.values().toList() }
    val tempSelectedTags = remember { mutableStateListOf<TodoTag>().apply { addAll(selectedTags) } }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Select Tags") },
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                tags.forEach { tag ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                if (tempSelectedTags.contains(tag)) {
                                    tempSelectedTags.remove(tag)
                                } else {
                                    tempSelectedTags.add(tag)
                                }
                            }
                    ) {
                        Checkbox(
                            checked = tempSelectedTags.contains(tag),
                            onCheckedChange = {
                                if (tempSelectedTags.contains(tag)) {
                                    tempSelectedTags.remove(tag)
                                } else {
                                    tempSelectedTags.add(tag)
                                }
                            },
                            colors = CheckboxDefaults.colors(
                                checkedColor = TodoColors.Purple
                            )
                        )
                        Text(text = tag.name)
                    }
                }
            }
        },
        confirmButton = {
            Button(
                onClick = { onTagsSelected(tempSelectedTags.toSet()) },
                colors = ButtonDefaults.buttonColors(
                    containerColor = TodoColors.Purple
                )
            ) {
                Text("Confirm")
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) {
                Text("Cancel")
            }
        }
    )
}

@Preview
@Composable
fun CreateTaskScreenPreview() {
    CreateTaskScreen()
} 