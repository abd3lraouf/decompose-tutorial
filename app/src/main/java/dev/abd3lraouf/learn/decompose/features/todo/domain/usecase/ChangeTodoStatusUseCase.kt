package dev.abd3lraouf.learn.decompose.features.todo.domain.usecase

import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoStatus
import dev.abd3lraouf.learn.decompose.features.todo.domain.repository.TodoRepository

class ChangeTodoStatusUseCase(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(todoId: String, status: TodoStatus) {
        val todo = repository.getTodoById(todoId) ?: return
        
        // Skip if already in the status
        if (todo.status == status) {
            return
        }
        
        // Update the todo with new status
        val updatedTodo = todo.copy(
            status = status,
            isCompleted = status == TodoStatus.DONE
        )
        repository.updateTodo(updatedTodo)
    }
} 