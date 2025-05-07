package dev.abd3lraouf.learn.decompose.features.todo.presentation.details

import com.arkivanov.decompose.value.Value
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoItem

interface TodoDetailsComponent {
    val model: Value<Model>
    
    fun onBackClicked()
    fun onDeleteClicked()
    fun onToggleCompleted()
    fun onEditClicked()
    
    data class Model(
        val todo: TodoItem? = null,
        val isLoading: Boolean = false
    )
} 