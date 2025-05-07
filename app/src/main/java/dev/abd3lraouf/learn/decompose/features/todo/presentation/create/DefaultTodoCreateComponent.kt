package dev.abd3lraouf.learn.decompose.features.todo.presentation.create

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoItem
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoPriority
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoStatus
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoTag
import dev.abd3lraouf.learn.decompose.features.todo.domain.usecase.AddTodoUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class DefaultTodoCreateComponent(
    componentContext: ComponentContext,
    private val addTodoUseCase: AddTodoUseCase,
    private val onBackClicked: () -> Unit,
    private val onSaveClicked: () -> Unit
) : TodoCreateComponent, ComponentContext by componentContext {
    
    private val componentScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    
    override val model: MutableValue<TodoCreateComponent.Model> = MutableValue(TodoCreateComponent.Model())
    
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
    
    override fun onTagsChanged(tags: Set<TodoTag>) {
        model.value = model.value.copy(tags = tags)
    }
    
    override fun onDueDateChanged(timestamp: Long?) {
        model.value = model.value.copy(deadline = timestamp)
    }
    
    override fun onSaveClicked() {
        val currentModel = model.value
        
        if (currentModel.title.isBlank()) return
        
        val newTodo = TodoItem(
            title = currentModel.title,
            description = currentModel.description,
            priority = currentModel.priority,
            status = currentModel.status,
            deadline = currentModel.deadline,
            tags = currentModel.tags
        )
        
        componentScope.launch {
            addTodoUseCase(newTodo)
            onSaveClicked.invoke()
        }
    }
    
    override fun onBackClicked() {
        onBackClicked.invoke()
    }
    
    class Factory(private val addTodoUseCase: AddTodoUseCase) {
        fun create(
            componentContext: ComponentContext,
            onBackClicked: () -> Unit,
            onSaveClicked: () -> Unit
        ): DefaultTodoCreateComponent {
            return DefaultTodoCreateComponent(
                componentContext = componentContext,
                addTodoUseCase = addTodoUseCase,
                onBackClicked = onBackClicked,
                onSaveClicked = onSaveClicked
            )
        }
    }
} 