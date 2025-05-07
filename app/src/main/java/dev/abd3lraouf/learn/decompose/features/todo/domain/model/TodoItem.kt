package dev.abd3lraouf.learn.decompose.features.todo.domain.model

import kotlinx.serialization.Serializable
import java.util.UUID

// This will now be called TodoStatus to better reflect its purpose
enum class TodoStatus {
    TODO, IN_PROGRESS, DONE;
    
    fun isCompleted(): Boolean = this == DONE
}

enum class TodoPriority {
    Low, Medium, High
}

// Define some common tags
@Serializable
enum class TodoTag {
    WORK, PERSONAL, SHOPPING, HEALTH, FINANCE, EDUCATION, TRAVEL, HOME, PROJECT, FAMILY, SOCIAL;
    
    companion object {
        val DEFAULT_TAGS = setOf(WORK, PERSONAL, SHOPPING, HEALTH)
    }
}

@Serializable
data class TodoItem(
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String = "",
    val status: TodoStatus = TodoStatus.TODO,
    val deadline: Long? = null, // Timestamp
    val priority: TodoPriority = TodoPriority.Medium,
    val isCompleted: Boolean = status == TodoStatus.DONE,
    val tags: Set<TodoTag> = emptySet()
) 