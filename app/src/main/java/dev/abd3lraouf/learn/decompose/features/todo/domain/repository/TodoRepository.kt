package dev.abd3lraouf.learn.decompose.features.todo.domain.repository

import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoItem
import kotlinx.coroutines.flow.Flow

interface TodoRepository {
    fun getTodos(): Flow<List<TodoItem>>
    suspend fun addTodo(todoItem: TodoItem)
    suspend fun updateTodo(todoItem: TodoItem)
    suspend fun deleteTodo(id: String)
    suspend fun getTodoById(id: String): TodoItem?
} 