package dev.abd3lraouf.learn.decompose.features.todo.domain.usecase

import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoStatus
import dev.abd3lraouf.learn.decompose.features.todo.domain.repository.TodoRepository

/**
 * This use case toggles a todo item's status following the workflow:
 * TODO -> IN_PROGRESS -> DONE -> TODO
 * It also updates the completion status based on whether the status is DONE
 */
class ToggleTodoUseCase(
    private val repository: TodoRepository
) {
    suspend operator fun invoke(todoId: String) {
        val todo = repository.getTodoById(todoId) ?: return
        
        // Apply the state transition logic
        val newStatus = when (todo.status) {
            // Play button: Move from TODO to IN_PROGRESS
            TodoStatus.TODO -> TodoStatus.IN_PROGRESS
            
            // Checkbox checked: Move from IN_PROGRESS to DONE
            TodoStatus.IN_PROGRESS -> TodoStatus.DONE
            
            // Checkbox unchecked: Move from DONE back to TODO
            TodoStatus.DONE -> TodoStatus.TODO
        }
        
        // Update the todo with new status
        repository.updateTodo(
            todo.copy(
                isCompleted = newStatus == TodoStatus.DONE,
                status = newStatus
            )
        )
    }
} 