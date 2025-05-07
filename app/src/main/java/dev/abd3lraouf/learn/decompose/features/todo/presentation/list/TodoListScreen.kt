package dev.abd3lraouf.learn.decompose.features.todo.presentation.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import dev.abd3lraouf.learn.decompose.features.todo.presentation.create.TodoCreateContent
import dev.abd3lraouf.learn.decompose.features.todo.presentation.details.TodoDetailsContent
import dev.abd3lraouf.learn.decompose.features.todo.presentation.edit.TodoEditContent
import dev.abd3lraouf.learn.decompose.ui.preview.PreviewTodoListComponent
import dev.abd3lraouf.learn.decompose.ui.theme.DecomposeTutorialTheme

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun TodoListContent(
    component: TodoListComponent,
    modifier: Modifier = Modifier
) {
    val childStack by component.childStack.subscribeAsState()

    ChildStack(
        stack = childStack,
        modifier = Modifier.fillMaxSize()
    ) { child ->
        when (val instance = child.instance) {
            is TodoListComponent.Child.List -> TodoItemsListScreen(
                component = instance.component
            )

            is TodoListComponent.Child.Details -> TodoDetailsContent(
                component = instance.component
            )

            is TodoListComponent.Child.Create -> TodoCreateContent(
                component = instance.component
            )

            is TodoListComponent.Child.Edit -> TodoEditContent(
                component = instance.component
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoListContentPreview() {
    DecomposeTutorialTheme {
        TodoListContent(component = PreviewTodoListComponent())
    }
} 