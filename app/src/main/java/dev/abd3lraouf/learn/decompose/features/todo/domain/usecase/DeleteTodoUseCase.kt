package dev.abd3lraouf.learn.decompose.features.todo.domain.usecase

import dev.abd3lraouf.learn.decompose.features.todo.domain.repository.TodoRepository

class DeleteTodoUseCase(private val repository: TodoRepository) {
    suspend operator fun invoke(id: String) = repository.deleteTodo(id)
} 