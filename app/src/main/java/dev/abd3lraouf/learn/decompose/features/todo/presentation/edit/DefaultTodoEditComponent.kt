package dev.abd3lraouf.learn.decompose.features.todo.presentation.edit

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoPriority
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoStatus
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoTag
import dev.abd3lraouf.learn.decompose.features.todo.domain.usecase.GetTodoByIdUseCase
import dev.abd3lraouf.learn.decompose.features.todo.domain.usecase.UpdateTodoUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class DefaultTodoEditComponent(
    componentContext: ComponentContext,
    private val todoId: String,
    private val getTodoByIdUseCase: GetTodoByIdUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase,
    private val onBackClicked: () -> Unit,
    private val onSaveClicked: () -> Unit
) : TodoEditComponent, ComponentContext by componentContext {
    
    private val componentScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    
    override val model: MutableValue<TodoEditComponent.Model> = MutableValue(
        TodoEditComponent.Model(id = todoId, isLoading = true)
    )
    
    init {
        loadTodo()
    }
    
    private fun loadTodo() {
        componentScope.launch {
            val todo = getTodoByIdUseCase(todoId)
            if (todo != null) {
                model.value = TodoEditComponent.Model(
                    id = todo.id,
                    title = todo.title,
                    description = todo.description,
                    priority = todo.priority,
                    status = todo.status,
                    deadline = todo.deadline,
                    tags = todo.tags,
                    isLoading = false,
                    isSaveEnabled = true
                )
            } else {
                model.value = model.value.copy(isLoading = false)
                onBackClicked()
            }
        }
    }
    
    override fun onTitleChanged(title: String) {
        model.value = model.value.copy(
            title = title,
            isSaveEnabled = title.isNotBlank()
        )
    }
    
    override fun onDescriptionChanged(description: String) {
        model.value = model.value.copy(description = description)
    }
    
    override fun onPriorityChanged(priority: TodoPriority) {
        model.value = model.value.copy(priority = priority)
    }
    
    override fun onStatusChanged(status: TodoStatus) {
        model.value = model.value.copy(status = status)
    }
    
    override fun onDueDateChanged(timestamp: Long?) {
        model.value = model.value.copy(deadline = timestamp)
    }
    
    override fun onTagsChanged(tags: Set<TodoTag>) {
        model.value = model.value.copy(tags = tags)
    }
    
    override fun onSaveClicked() {
        val currentModel = model.value
        
        if (!currentModel.isSaveEnabled || currentModel.title.isBlank()) {
            return
        }
        
        componentScope.launch {
            updateTodoUseCase(
                todoId = currentModel.id,
                title = currentModel.title,
                description = currentModel.description,
                status = currentModel.status,
                deadline = currentModel.deadline,
                priority = currentModel.priority,
                tags = currentModel.tags
            )
            onSaveClicked.invoke()
        }
    }
    
    override fun onBackClicked() {
        onBackClicked.invoke()
    }
    
    class Factory(
        private val getTodoByIdUseCase: GetTodoByIdUseCase,
        private val updateTodoUseCase: UpdateTodoUseCase
    ) {
        fun create(
            componentContext: ComponentContext,
            todoId: String,
            onBackClicked: () -> Unit,
            onSaveClicked: () -> Unit
        ): DefaultTodoEditComponent {
            return DefaultTodoEditComponent(
                componentContext = componentContext,
                todoId = todoId,
                getTodoByIdUseCase = getTodoByIdUseCase,
                updateTodoUseCase = updateTodoUseCase,
                onBackClicked = onBackClicked,
                onSaveClicked = onSaveClicked
            )
        }
    }
} 