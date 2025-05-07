package dev.abd3lraouf.learn.decompose.ui.preview

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import dev.abd3lraouf.learn.decompose.features.settings.domain.model.AppSettings
import dev.abd3lraouf.learn.decompose.features.settings.presentation.SettingsComponent
import dev.abd3lraouf.learn.decompose.features.statistics.domain.model.TodoStatistics
import dev.abd3lraouf.learn.decompose.features.statistics.presentation.StatsComponent
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoStatus
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoItem
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoPriority
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoTag
import dev.abd3lraouf.learn.decompose.features.todo.presentation.create.TodoCreateComponent
import dev.abd3lraouf.learn.decompose.features.todo.presentation.details.TodoDetailsComponent
import dev.abd3lraouf.learn.decompose.features.todo.presentation.edit.TodoEditComponent
import dev.abd3lraouf.learn.decompose.features.todo.presentation.list.ListComponent
import dev.abd3lraouf.learn.decompose.features.todo.presentation.list.TodoListComponent
import dev.abd3lraouf.learn.decompose.navigation.RootComponent
import dev.abd3lraouf.learn.decompose.navigation.TabNavigationComponent
import kotlinx.serialization.Serializable
import java.util.Calendar

/**
 * Preview data for TodoItems
 */
object PreviewTodoData {
    val todoItems = listOf(
        TodoItem(
            id = "1",
            title = "Complete Decompose Tutorial",
            description = "Finish implementing all components",
            status = TodoStatus.TODO,
            priority = TodoPriority.High,
            tags = setOf(TodoTag.WORK, TodoTag.EDUCATION),
            deadline = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, 1) }.timeInMillis
        ),
        TodoItem(
            id = "2",
            title = "Study Kotlin Coroutines",
            description = "Read the official documentation",
            status = TodoStatus.IN_PROGRESS,
            priority = TodoPriority.Medium,
            tags = setOf(TodoTag.EDUCATION),
            deadline = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, 3) }.timeInMillis
        ),
        TodoItem(
            id = "3",
            title = "Go Shopping",
            description = "Buy groceries for the week",
            status = TodoStatus.DONE,
            priority = TodoPriority.Low,
            isCompleted = true,
            tags = setOf(TodoTag.SHOPPING, TodoTag.HOME),
            deadline = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }.timeInMillis
        ),
        TodoItem(
            id = "4",
            title = "Exercise",
            description = "Go for a run or do yoga",
            status = TodoStatus.TODO,
            priority = TodoPriority.Medium,
            tags = setOf(TodoTag.HEALTH, TodoTag.PERSONAL),
            deadline = null
        )
    )
    
    val statistics = TodoStatistics(
        totalCount = todoItems.size,
        completedCount = todoItems.count { it.isCompleted },
        pendingCount = todoItems.count { !it.isCompleted },
        highPriorityCount = todoItems.count { it.priority == TodoPriority.High },
        mediumPriorityCount = todoItems.count { it.priority == TodoPriority.Medium },
        lowPriorityCount = todoItems.count { it.priority == TodoPriority.Low }
    )
}

/**
 * Preview implementations for Components
 */

// Preview TodoListComponent
class PreviewTodoListComponent : TodoListComponent {
    override val childStack: Value<ChildStack<*, TodoListComponent.Child>> = MutableValue(
        ChildStack(
            configuration = "list",
            instance = TodoListComponent.Child.List(PreviewListComponent())
        )
    )
    
    override val model: Value<TodoListComponent.Model> = MutableValue(
        TodoListComponent.Model(
            todos = PreviewTodoData.todoItems
        )
    )
    
    override fun onAddTodoClicked() {}
    override fun onTodoClicked(id: String) {}
    override fun onTodoCompleted(id: String) {}
    override fun onTodoDeleted(id: String) {}
    override fun onBackPressed() {}
}

// Preview ListComponent
class PreviewListComponent : ListComponent {
    override val model: Value<ListComponent.Model> = MutableValue(
        ListComponent.Model(
            items = PreviewTodoData.todoItems
        )
    )
    
    override fun onItemSelected(id: String) {}
    override fun onCompletedToggled(id: String) {}
    override fun onStatusChanged(id: String, status: TodoStatus) {}
    override fun onSortOptionSelected(sortOption: ListComponent.SortOption) {}
}

// Preview TodoDetailsComponent
class PreviewTodoDetailsComponent : TodoDetailsComponent {
    override val model: Value<TodoDetailsComponent.Model> = MutableValue(
        TodoDetailsComponent.Model(
            todo = PreviewTodoData.todoItems[0],
            isLoading = false
        )
    )
    
    override fun onBackClicked() {}
    override fun onDeleteClicked() {}
    override fun onToggleCompleted() {}
    override fun onEditClicked() {}
}

// Preview TodoCreateComponent
class PreviewTodoCreateComponent : TodoCreateComponent {
    override val model: Value<TodoCreateComponent.Model> = MutableValue(
        TodoCreateComponent.Model(
            title = "New Todo",
            description = "Description",
            priority = TodoPriority.Medium,
            status = TodoStatus.TODO,
            tags = setOf(TodoTag.WORK, TodoTag.PROJECT),
            deadline = Calendar.getInstance().timeInMillis,
            isSaveEnabled = true
        )
    )
    
    override fun onTitleChanged(title: String) {}
    override fun onDescriptionChanged(description: String) {}
    override fun onPriorityChanged(priority: TodoPriority) {}
    override fun onStatusChanged(status: TodoStatus) {}
    override fun onTagsChanged(tags: Set<TodoTag>) {}
    override fun onDueDateChanged(timestamp: Long?) {}
    override fun onSaveClicked() {}
    override fun onBackClicked() {}
}

// Preview TodoEditComponent
class PreviewTodoEditComponent : TodoEditComponent {
    override val model: Value<TodoEditComponent.Model> = MutableValue(
        TodoEditComponent.Model(
            id = "1",
            title = "Edit This Task",
            description = "Sample description for preview",
            priority = TodoPriority.Medium,
            status = TodoStatus.IN_PROGRESS,
            deadline = Calendar.getInstance().timeInMillis,
            tags = setOf(TodoTag.WORK, TodoTag.PROJECT),
            isLoading = false,
            isSaveEnabled = true
        )
    )
    
    override fun onTitleChanged(title: String) {}
    override fun onDescriptionChanged(description: String) {}
    override fun onPriorityChanged(priority: TodoPriority) {}
    override fun onStatusChanged(status: TodoStatus) {}
    override fun onTagsChanged(tags: Set<TodoTag>) {}
    override fun onDueDateChanged(timestamp: Long?) {}
    override fun onSaveClicked() {}
    override fun onBackClicked() {}
}

// Preview StatsComponent
class PreviewStatsComponent : StatsComponent {
    override val model: Value<TodoStatistics> = MutableValue(PreviewTodoData.statistics)
}

// Preview SettingsComponent
class PreviewSettingsComponent : SettingsComponent {
    override val model: Value<AppSettings> = MutableValue(
        AppSettings(isDarkMode = true)
    )
    
    override fun onThemeChanged(isDarkMode: Boolean) {}
}

// Preview TabNavigationComponent
class PreviewTabNavigationComponent : TabNavigationComponent {
    override val childStack: Value<ChildStack<*, TabNavigationComponent.Child>> = MutableValue(
        ChildStack(
            configuration = "todo_list",
            instance = TabNavigationComponent.Child.TodoListChild(PreviewTodoListComponent())
        )
    )
    
    override val model: Value<TabNavigationComponent.Model> = MutableValue(
        TabNavigationComponent.Model(inProgressCount = 2)
    )
    
    override fun onTabSelected(tab: TabNavigationComponent.Tab) {}
}

// Preview RootComponent
class PreviewRootComponent : RootComponent {
    override val stack: Value<ChildStack<*, RootComponent.Child>> = MutableValue(
        ChildStack(
            configuration = "tabs",
            instance = RootComponent.Child.TabsChild(PreviewTabNavigationComponent())
        )
    )
    
    override fun onBackClicked(toIndex: Int) {}
} 