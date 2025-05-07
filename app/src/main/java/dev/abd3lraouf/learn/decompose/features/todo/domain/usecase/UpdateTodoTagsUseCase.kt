package dev.abd3lraouf.learn.decompose.features.todo.domain.usecase

import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoTag
import dev.abd3lraouf.learn.decompose.features.todo.domain.repository.TodoRepository

class UpdateTodoTagsUseCase(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(todoId: String, tags: Set<TodoTag>) {
        val todo = repository.getTodoById(todoId) ?: return
        
        // Skip if tags are the same
        if (todo.tags == tags) {
            return
        }
        
        // Update the todo with new tags
        val updatedTodo = todo.copy(tags = tags)
        repository.updateTodo(updatedTodo)
    }
} 