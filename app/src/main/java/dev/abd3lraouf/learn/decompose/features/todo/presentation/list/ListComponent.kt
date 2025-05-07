package dev.abd3lraouf.learn.decompose.features.todo.presentation.list

import com.arkivanov.decompose.value.Value
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoItem
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoStatus

interface ListComponent {
    val model: Value<Model>
    
    fun onItemSelected(id: String)
    fun onCompletedToggled(id: String)
    fun onStatusChanged(id: String, status: TodoStatus)
    fun onSortOptionSelected(sortOption: SortOption)
    
    data class Model(
        val items: List<TodoItem> = emptyList(),
        val isLoading: Boolean = false,
        val sortOption: SortOption = SortOption.NONE
    )
    
    enum class SortOption {
        NONE,
        PRIORITY_ASC,
        PRIORITY_DESC,
        DATE_ASC,
        DATE_DESC
    }
} 