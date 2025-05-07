package dev.abd3lraouf.learn.decompose.features.todo.presentation.list

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoItem
import dev.abd3lraouf.learn.decompose.features.todo.presentation.create.TodoCreateComponent
import dev.abd3lraouf.learn.decompose.features.todo.presentation.details.TodoDetailsComponent
import dev.abd3lraouf.learn.decompose.features.todo.presentation.edit.TodoEditComponent

interface TodoListComponent {
    val childStack: Value<ChildStack<*, Child>>
    val model: Value<Model>
    
    fun onAddTodoClicked()
    fun onTodoClicked(id: String)
    fun onTodoCompleted(id: String)
    fun onTodoDeleted(id: String)
    fun onBackPressed()
    
    data class Model(
        val todos: List<TodoItem> = emptyList(),
        val isLoading: Boolean = false
    )
    
    sealed class Child {
        data class List(val component: ListComponent) : Child()
        data class Details(val component: TodoDetailsComponent) : Child()
        data class Create(val component: TodoCreateComponent) : Child()
        data class Edit(val component: TodoEditComponent) : Child()
    }
} 