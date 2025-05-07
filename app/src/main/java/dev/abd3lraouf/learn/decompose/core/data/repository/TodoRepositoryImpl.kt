package dev.abd3lraouf.learn.decompose.core.data.repository

import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoStatus
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoItem
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoPriority
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoTag
import dev.abd3lraouf.learn.decompose.features.todo.domain.repository.TodoRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Calendar
import java.util.concurrent.TimeUnit

class TodoRepositoryImpl : TodoRepository {
    // Initial sample data
    private val _todos = MutableStateFlow<List<TodoItem>>(
        listOf(
            TodoItem(
                title = "UI/UX Design Task",
                description = "Create wireframes for the new mobile app",
                priority = TodoPriority.High,
                status = TodoStatus.TODO,
                tags = setOf(TodoTag.WORK, TodoTag.PROJECT),
                deadline = getDateInMillis(1)
            ),
            TodoItem(
                title = "Submit project report",
                description = "Complete and submit final project report",
                priority = TodoPriority.High,
                status = TodoStatus.IN_PROGRESS,
                tags = setOf(TodoTag.WORK, TodoTag.EDUCATION),
                deadline = getDateInMillis(1)
            ),
            TodoItem(
                title = "Read 10 pages of a book",
                description = "Continue reading the current book",
                priority = TodoPriority.Low,
                status = TodoStatus.TODO,
                tags = setOf(TodoTag.PERSONAL, TodoTag.EDUCATION),
                deadline = getDateInMillis(1)
            ),
            TodoItem(
                title = "Buy groceries for the week",
                description = "Get essentials from the supermarket",
                priority = TodoPriority.Medium,
                status = TodoStatus.IN_PROGRESS,
                tags = setOf(TodoTag.SHOPPING, TodoTag.HOME),
                deadline = getDateInMillis(1)
            ),
            TodoItem(
                title = "Update portfolio website",
                description = "Add recent projects to portfolio",
                priority = TodoPriority.Medium,
                status = TodoStatus.TODO,
                tags = setOf(TodoTag.WORK, TodoTag.PROJECT),
                deadline = getDateInMillis(2)
            ),
            TodoItem(
                title = "Prepare presentation slides",
                description = "Create slides for next week's meeting",
                priority = TodoPriority.High,
                status = TodoStatus.DONE,
                tags = setOf(TodoTag.WORK),
                isCompleted = true,
                deadline = getDateInMillis(-1)
            ),
            TodoItem(
                title = "Pay monthly bills",
                description = "Pay utility and internet bills",
                priority = TodoPriority.High,
                status = TodoStatus.DONE,
                tags = setOf(TodoTag.FINANCE, TodoTag.HOME),
                isCompleted = true,
                deadline = getDateInMillis(-2)
            ),
            TodoItem(
                title = "Schedule dentist appointment",
                description = "Book 6-month checkup",
                priority = TodoPriority.Medium,
                status = TodoStatus.DONE,
                tags = setOf(TodoTag.HEALTH, TodoTag.PERSONAL),
                isCompleted = true,
                deadline = getDateInMillis(-3)
            )
        )
    )
    
    override fun getTodos(): Flow<List<TodoItem>> = _todos.asStateFlow()
    
    override suspend fun addTodo(todoItem: TodoItem) {
        _todos.update { currentList ->
            currentList + todoItem
        }
    }
    
    override suspend fun updateTodo(todoItem: TodoItem) {
        _todos.update { currentList ->
            currentList.map {
                if (it.id == todoItem.id) todoItem else it
            }
        }
    }
    
    override suspend fun deleteTodo(id: String) {
        _todos.update { currentList ->
            currentList.filter { it.id != id }
        }
    }
    
    suspend fun toggleComplete(id: String) {
        _todos.update { currentList ->
            currentList.map {
                if (it.id == id) it.copy(isCompleted = !it.isCompleted) else it
            }
        }
    }
    
    override suspend fun getTodoById(id: String): TodoItem? {
        return _todos.value.find { it.id == id }
    }
    
    // Helper method to generate date timestamps
    private fun getDateInMillis(daysFromNow: Int): Long {
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.DAY_OF_YEAR, daysFromNow)
        return calendar.timeInMillis
    }
    
    companion object {
        private var instance: TodoRepositoryImpl? = null
        
        fun getInstance(): TodoRepositoryImpl {
            return instance ?: TodoRepositoryImpl().also { instance = it }
        }
    }
} 