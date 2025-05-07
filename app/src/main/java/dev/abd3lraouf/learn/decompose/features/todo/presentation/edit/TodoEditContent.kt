package dev.abd3lraouf.learn.decompose.features.todo.presentation.edit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import dev.abd3lraouf.learn.decompose.features.todo.presentation.edit.components.EditTaskScreen

@Composable
fun TodoEditContent(
    component: TodoEditComponent,
    modifier: Modifier = Modifier
) {
    val model by component.model.subscribeAsState()
    
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (model.isLoading) {
            CircularProgressIndicator()
        } else if (model.title.isEmpty() && !model.isLoading) {
            Text("Todo not found")
        } else {
            EditTaskScreen(
                title = model.title,
                description = model.description,
                priority = model.priority,
                status = model.status,
                deadline = model.deadline,
                tags = model.tags,
                onTitleChanged = component::onTitleChanged,
                onDescriptionChanged = component::onDescriptionChanged,
                onPriorityChanged = component::onPriorityChanged,
                onStatusChanged = component::onStatusChanged,
                onDueDateChanged = component::onDueDateChanged,
                onTagsChanged = component::onTagsChanged,
                onSaveClick = component::onSaveClicked,
                onBackClick = component::onBackClicked
            )
        }
    }
} 