package dev.abd3lraouf.learn.decompose.features.todo.presentation.create

import com.arkivanov.decompose.value.Value
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoStatus
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoPriority
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoTag

interface TodoCreateComponent {
    val model: Value<Model>
    
    fun onTitleChanged(title: String)
    fun onDescriptionChanged(description: String)
    fun onPriorityChanged(priority: TodoPriority)
    fun onStatusChanged(status: TodoStatus)
    fun onDueDateChanged(timestamp: Long?)
    fun onTagsChanged(tags: Set<TodoTag>)
    fun onSaveClicked()
    fun onBackClicked()
    
    data class Model(
        val title: String = "",
        val description: String = "",
        val priority: TodoPriority = TodoPriority.Medium,
        val status: TodoStatus = TodoStatus.TODO,
        val deadline: Long? = null,
        val tags: Set<TodoTag> = emptySet(),
        val isSaveEnabled: Boolean = false
    )
} 