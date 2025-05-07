package dev.abd3lraouf.learn.decompose.features.todo.presentation.list

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import dev.abd3lraouf.learn.decompose.features.todo.presentation.components.AddTaskFab
import dev.abd3lraouf.learn.decompose.features.todo.presentation.create.TodoCreateContent
import dev.abd3lraouf.learn.decompose.features.todo.presentation.details.TodoDetailsContent
import dev.abd3lraouf.learn.decompose.features.todo.presentation.edit.TodoEditContent
import dev.abd3lraouf.learn.decompose.features.todo.presentation.list.TodoListComponent.Child

@Composable
fun TodoListContent(
    component: TodoListComponent,
    modifier: Modifier = Modifier,
    listModifier: Modifier = Modifier,
    todoDetailsModifier: Modifier = Modifier
) {
    val childStack by component.childStack.subscribeAsState()
    val activeChild = childStack.active.instance
    when (activeChild) {
        is Child.List -> activeChild.component
        is Child.Details -> null
        is Child.Create -> null
        is Child.Edit -> null
    }

    Box(modifier = modifier.fillMaxSize()) {
        when (val child = childStack.active.instance) {
            is Child.List -> TodoItemsListScreen(
                component = child.component
            )

            is Child.Details -> TodoDetailsContent(
                component = child.component, modifier = todoDetailsModifier
            )

            is Child.Create -> TodoCreateContent(
                component = child.component
            )

            is Child.Edit -> TodoEditContent(
                component = child.component
            )
        }

        if (activeChild is Child.List) {
            AddTaskFab(
                onClick = component::onAddTodoClicked,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(end = 16.dp, bottom = 88.dp)
            )
        }
    }
} 