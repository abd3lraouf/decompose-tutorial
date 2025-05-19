package dev.abd3lraouf.learn.decompose.features.todo.presentation.create.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import dev.abd3lraouf.learn.decompose.features.todo.presentation.create.TodoCreateComponent

@Composable
fun TodoCreateContent(
    component: TodoCreateComponent,
    modifier: Modifier = Modifier
) {
    val model by component.model.subscribeAsState()

    CreateTaskScreen(
        onSaveClick = { title, description, status, deadline, priority, tags ->
            component.onTitleChanged(title)
            component.onDescriptionChanged(description)
            component.onStatusChanged(status)
            component.onDueDateChanged(deadline)
            component.onPriorityChanged(priority)
            component.onTagsChanged(tags)
            component.onSaveClicked()
        },
        modifier = modifier
    )
} 