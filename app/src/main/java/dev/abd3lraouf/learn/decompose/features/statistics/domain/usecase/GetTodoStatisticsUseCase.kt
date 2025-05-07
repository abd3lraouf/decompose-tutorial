package dev.abd3lraouf.learn.decompose.features.statistics.domain.usecase

import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoPriority
import dev.abd3lraouf.learn.decompose.features.todo.domain.repository.TodoRepository
import dev.abd3lraouf.learn.decompose.features.statistics.domain.model.TodoStatistics
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTodoStatisticsUseCase(private val repository: TodoRepository) {
    operator fun invoke(): Flow<TodoStatistics> {
        return repository.getTodos().map { todos ->
            TodoStatistics(
                totalCount = todos.size,
                completedCount = todos.count { it.isCompleted },
                pendingCount = todos.count { !it.isCompleted },
                highPriorityCount = todos.count { it.priority == TodoPriority.High },
                mediumPriorityCount = todos.count { it.priority == TodoPriority.Medium },
                lowPriorityCount = todos.count { it.priority == TodoPriority.Low }
            )
        }
    }
}
