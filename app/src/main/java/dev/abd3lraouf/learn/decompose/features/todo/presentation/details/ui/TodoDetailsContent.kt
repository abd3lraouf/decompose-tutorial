package dev.abd3lraouf.learn.decompose.features.todo.presentation.details.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import dev.abd3lraouf.learn.decompose.features.todo.presentation.details.TodoDetailsComponent
import dev.abd3lraouf.learn.decompose.ui.preview.PreviewTodoDetailsComponent
import dev.abd3lraouf.learn.decompose.ui.theme.DecomposeTutorialTheme

@Composable
fun TodoDetailsContent(
    component: TodoDetailsComponent,
    modifier: Modifier = Modifier
) {
    val model by component.model.subscribeAsState()
    
    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        if (model.isLoading) {
            CircularProgressIndicator()
        } else if (model.todo == null) {
            Text("Todo not found")
        } else {
            TodoDetailsScreen(
                todo = model.todo!!,
                onDeleteClick = component::onDeleteClicked,
                onToggleCompleted = component::onToggleCompleted,
                onEditClick = component::onEditClicked,
                modifier = Modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoDetailsContentPreview() {
    DecomposeTutorialTheme {
        TodoDetailsContent(component = PreviewTodoDetailsComponent())
    }
} 