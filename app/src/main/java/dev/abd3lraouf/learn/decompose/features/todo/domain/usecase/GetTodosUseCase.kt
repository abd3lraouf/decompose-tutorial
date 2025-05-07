package dev.abd3lraouf.learn.decompose.features.todo.domain.usecase

import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoItem
import dev.abd3lraouf.learn.decompose.features.todo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow

class GetTodosUseCase(private val repository: TodoRepository) {
    operator fun invoke(): Flow<List<TodoItem>> = repository.getTodos()
} 