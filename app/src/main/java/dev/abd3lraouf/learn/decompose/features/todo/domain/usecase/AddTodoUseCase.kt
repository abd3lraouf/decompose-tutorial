package dev.abd3lraouf.learn.decompose.features.todo.domain.usecase

import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoStatus
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoItem
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoPriority
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoTag
import dev.abd3lraouf.learn.decompose.features.todo.domain.repository.TodoRepository

class AddTodoUseCase(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(todoItem: TodoItem) {
        repository.addTodo(todoItem)
    }
    
    // Overload for convenience
    suspend operator fun invoke(
        title: String,
        description: String,
        status: TodoStatus,
        deadline: Long,
        priority: TodoPriority,
        tags: Set<TodoTag> = emptySet()
    ) {
        val todoItem = TodoItem(
            title = title,
            description = description,
            status = status,
            deadline = deadline,
            priority = priority,
            tags = tags
        )
        repository.addTodo(todoItem)
    }
} 