package dev.abd3lraouf.learn.decompose.features.todo.presentation.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.woowla.compose.icon.collections.heroicons.Heroicons
import com.woowla.compose.icon.collections.heroicons.heroicons.Solid
import com.woowla.compose.icon.collections.heroicons.heroicons.solid.Plus
import dev.abd3lraouf.learn.decompose.features.todo.presentation.list.TodoListComponent
import dev.abd3lraouf.learn.decompose.navigation.TabNavigationComponent
import dev.abd3lraouf.learn.decompose.ui.components.TopAppBarConfig

/**
 * Reusable Add Task FAB component
 */
@Composable
fun AddTaskFab(onClick: () -> Unit,
               modifier: Modifier = Modifier
               ) {
    FloatingActionButton(
        modifier = modifier,
        onClick = onClick,
        containerColor = MaterialTheme.colorScheme.primaryContainer,
        contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
        shape = CircleShape,
        elevation = FloatingActionButtonDefaults.elevation()
    ) {
        Icon(
            imageVector = Heroicons.Solid.Plus,
            contentDescription = "Add Task",
            modifier = Modifier.size(26.dp)
        )
    }
}

/**
 * Creates TopAppBar configuration for Todo feature screens
 */
@Composable
fun createTodoTopAppBarConfig(
    rootChild: TabNavigationComponent.Child.TodoListChild,
    todoChild: Any?
): TopAppBarConfig {
    return when (todoChild) {
        is TodoListComponent.Child.List -> 
            TopAppBarConfig(title = "Todo List")
        is TodoListComponent.Child.Create -> 
            TopAppBarConfig(
                title = "Create Task",
                showBackButton = true,
                onBackClick = rootChild.component::onBackPressed
            )
        is TodoListComponent.Child.Details -> 
            TopAppBarConfig(
                title = "Task Details",
                showBackButton = true,
                onBackClick = rootChild.component::onBackPressed
            )
        is TodoListComponent.Child.Edit ->
            TopAppBarConfig(
                title = "Edit Task",
                showBackButton = true,
                onBackClick = rootChild.component::onBackPressed
            )
        else -> TopAppBarConfig(title = "Todo List")
    }
} 