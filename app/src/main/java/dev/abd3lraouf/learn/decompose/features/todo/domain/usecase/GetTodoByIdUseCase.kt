package dev.abd3lraouf.learn.decompose.features.todo.domain.usecase

import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoItem
import dev.abd3lraouf.learn.decompose.features.todo.domain.repository.TodoRepository

class GetTodoByIdUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(id: String): TodoItem? {
        return repository.getTodoById(id)
    }
} 