package dev.abd3lraouf.learn.decompose.features.todo.presentation.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoStatus
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoItem
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoPriority
import dev.abd3lraouf.learn.decompose.features.todo.domain.usecase.GetTodosUseCase
import dev.abd3lraouf.learn.decompose.features.todo.domain.usecase.ToggleTodoUseCase
import dev.abd3lraouf.learn.decompose.features.todo.presentation.list.ListComponent.SortOption
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class DefaultListComponent(
    componentContext: ComponentContext,
    private val getTodosUseCase: GetTodosUseCase,
    private val toggleTodoUseCase: ToggleTodoUseCase,
    private val onItemSelected: (id: String) -> Unit,
    private val onTodoToggled: (id: String) -> Unit,
    private val onTodoStatusChanged: (id: String, status: TodoStatus) -> Unit
) : ListComponent, ComponentContext by componentContext {
    
    private val componentScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private var currentTodos: List<TodoItem> = emptyList()
    
    override val model: MutableValue<ListComponent.Model> = MutableValue(ListComponent.Model(isLoading = true))
    
    init {
        getTodosUseCase().onEach { todos ->
            currentTodos = todos
            updateModelWithSortedItems()
        }.launchIn(componentScope)
    }
    
    private fun updateModelWithSortedItems() {
        val sortedItems = applySorting(currentTodos, model.value.sortOption)
        model.value = model.value.copy(
            items = sortedItems,
            isLoading = false
        )
    }
    
    private fun applySorting(items: List<TodoItem>, sortOption: SortOption): List<TodoItem> {
        return when (sortOption) {
            SortOption.NONE -> items
            SortOption.PRIORITY_ASC -> items.sortedBy { 
                when (it.priority) {
                    TodoPriority.High -> 3
                    TodoPriority.Medium -> 2
                    TodoPriority.Low -> 1
                }
            }
            SortOption.PRIORITY_DESC -> items.sortedByDescending { 
                when (it.priority) {
                    TodoPriority.High -> 3
                    TodoPriority.Medium -> 2
                    TodoPriority.Low -> 1
                }
            }
            SortOption.DATE_ASC -> items.sortedBy { it.deadline ?: Long.MAX_VALUE }
            SortOption.DATE_DESC -> items.sortedByDescending { it.deadline ?: Long.MIN_VALUE }
        }
    }
    
    override fun onItemSelected(id: String) {
        onItemSelected.invoke(id)
    }
    
    override fun onCompletedToggled(id: String) {
        componentScope.launch {
            toggleTodoUseCase(id)
        }
    }
    
    override fun onStatusChanged(id: String, status: TodoStatus) {
        onTodoStatusChanged.invoke(id, status)
    }
    
    override fun onSortOptionSelected(sortOption: SortOption) {
        model.value = model.value.copy(sortOption = sortOption)
        updateModelWithSortedItems()
    }
} 