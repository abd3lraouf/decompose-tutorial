package dev.abd3lraouf.learn.decompose.features.todo.presentation.details

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.Lifecycle
import com.arkivanov.essenty.lifecycle.doOnResume
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoItem
import dev.abd3lraouf.learn.decompose.features.todo.domain.usecase.DeleteTodoUseCase
import dev.abd3lraouf.learn.decompose.features.todo.domain.usecase.GetTodoByIdUseCase
import dev.abd3lraouf.learn.decompose.features.todo.domain.usecase.ToggleTodoUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class DefaultTodoDetailsComponent(
    private val todoId: String,
    private val onBack: () -> Unit,
    private val onDeleted: (String) -> Unit,
    private val onEdit: (String) -> Unit,
    componentContext: ComponentContext,
    private val getTodoByIdUseCase: GetTodoByIdUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val toggleTodoUseCase: ToggleTodoUseCase
) : TodoDetailsComponent, ComponentContext by componentContext {

    private val componentScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val _model = MutableValue(TodoDetailsComponent.Model(isLoading = true))
    override val model: Value<TodoDetailsComponent.Model> = _model

    init {
        loadTodo()
        
        // Subscribe to lifecycle events to reload data when component becomes active
        lifecycle.doOnResume {
            loadTodo()
        }
    }

    private fun loadTodo() {
        componentScope.launch {
            _model.value = _model.value.copy(isLoading = true)
            try {
                val todo = getTodoByIdUseCase(todoId)
                _model.value = _model.value.copy(todo = todo, isLoading = false)
            } catch (e: Exception) {
                _model.value = _model.value.copy(isLoading = false)
            }
        }
    }

    override fun onBackClicked() {
        onBack()
    }

    override fun onDeleteClicked() {
        componentScope.launch {
            _model.value.todo?.let { todo ->
                deleteTodoUseCase(todo.id)
                onDeleted(todo.id)
            }
        }
    }

    override fun onToggleCompleted() {
        componentScope.launch {
            _model.value.todo?.let { todo ->
                toggleTodoUseCase(todo.id)
                loadTodo() // Reload to get updated state
            }
        }
    }

    override fun onEditClicked() {
        onEdit(todoId)
    }

    class Factory(
        private val getTodoByIdUseCase: GetTodoByIdUseCase,
        private val deleteTodoUseCase: DeleteTodoUseCase,
        private val toggleTodoUseCase: ToggleTodoUseCase
    ) {
        fun create(
            componentContext: ComponentContext,
            todoId: String,
            onBack: () -> Unit,
            onDeleted: (String) -> Unit,
            onEdit: (String) -> Unit
        ): DefaultTodoDetailsComponent {
            return DefaultTodoDetailsComponent(
                todoId = todoId,
                componentContext = componentContext,
                getTodoByIdUseCase = getTodoByIdUseCase,
                deleteTodoUseCase = deleteTodoUseCase,
                toggleTodoUseCase = toggleTodoUseCase,
                onBack = onBack,
                onDeleted = onDeleted,
                onEdit = onEdit
            )
        }
    }
}