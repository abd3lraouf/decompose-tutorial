package dev.abd3lraouf.learn.decompose.features.todo.domain.usecase

import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoItem
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoPriority
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoStatus
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoTag
import dev.abd3lraouf.learn.decompose.features.todo.domain.repository.TodoRepository

/**
 * Updates an existing todo item with new values
 */
class UpdateTodoUseCase(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(
        todoId: String,
        title: String,
        description: String,
        status: TodoStatus,
        deadline: Long?,
        priority: TodoPriority,
        tags: Set<TodoTag>
    ) {
        val todo = repository.getTodoById(todoId) ?: return
        
        val updatedTodo = todo.copy(
            title = title,
            description = description,
            status = status,
            deadline = deadline,
            priority = priority,
            tags = tags,
            isCompleted = status == TodoStatus.DONE
        )
        
        repository.updateTodo(updatedTodo)
    }
    
    // Overloaded function to directly update a todo item
    suspend operator fun invoke(todoItem: TodoItem) {
        repository.updateTodo(todoItem)
    }
} 