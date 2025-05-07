package dev.abd3lraouf.learn.decompose.features.todo.presentation.list

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.router.stack.push
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoStatus
import dev.abd3lraouf.learn.decompose.features.todo.domain.usecase.AddTodoUseCase
import dev.abd3lraouf.learn.decompose.features.todo.domain.usecase.ChangeTodoStatusUseCase
import dev.abd3lraouf.learn.decompose.features.todo.domain.usecase.DeleteTodoUseCase
import dev.abd3lraouf.learn.decompose.features.todo.domain.usecase.GetTodoByIdUseCase
import dev.abd3lraouf.learn.decompose.features.todo.domain.usecase.GetTodosUseCase
import dev.abd3lraouf.learn.decompose.features.todo.domain.usecase.ToggleTodoUseCase
import dev.abd3lraouf.learn.decompose.features.todo.domain.usecase.UpdateTodoTagsUseCase
import dev.abd3lraouf.learn.decompose.features.todo.domain.usecase.UpdateTodoUseCase
import dev.abd3lraouf.learn.decompose.features.todo.presentation.create.DefaultTodoCreateComponent
import dev.abd3lraouf.learn.decompose.features.todo.presentation.details.DefaultTodoDetailsComponent
import dev.abd3lraouf.learn.decompose.features.todo.presentation.edit.DefaultTodoEditComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable

class DefaultTodoListComponent(
    componentContext: ComponentContext,
    private val getTodosUseCase: GetTodosUseCase,
    private val toggleTodoUseCase: ToggleTodoUseCase,
    private val deleteTodoUseCase: DeleteTodoUseCase,
    private val changeTodoStatusUseCase: ChangeTodoStatusUseCase,
    private val getTodoByIdUseCase: GetTodoByIdUseCase,
    private val addTodoUseCase: AddTodoUseCase,
    private val updateTodoTagsUseCase: UpdateTodoTagsUseCase,
    private val updateTodoUseCase: UpdateTodoUseCase
) : TodoListComponent, ComponentContext by componentContext {
    
    private val componentScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    private val navigation = StackNavigation<Config>()
    
    override val childStack: Value<ChildStack<*, TodoListComponent.Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.List,
            childFactory = ::createChild,
            handleBackButton = true
        )
    
    override val model: MutableValue<TodoListComponent.Model> = MutableValue(TodoListComponent.Model())
    
    init {
        getTodosUseCase().onEach { todos ->
            model.value = model.value.copy(todos = todos)
        }.launchIn(componentScope)
    }
    
    private fun createChild(config: Config, componentContext: ComponentContext): TodoListComponent.Child =
        when (config) {
            is Config.List -> TodoListComponent.Child.List(
                DefaultListComponent(
                    componentContext = componentContext,
                    getTodosUseCase = getTodosUseCase,
                    toggleTodoUseCase = toggleTodoUseCase,
                    onItemSelected = { itemId -> onTodoClicked(itemId) },
                    onTodoToggled = { id -> onTodoCompleted(id) },
                    onTodoStatusChanged = { id, status -> onTodoStatusChanged(id, status) }
                )
            )
            is Config.Details -> TodoListComponent.Child.Details(
                DefaultTodoDetailsComponent(
                    componentContext = componentContext,
                    todoId = config.todoId,
                    getTodoByIdUseCase = getTodoByIdUseCase,
                    deleteTodoUseCase = deleteTodoUseCase,
                    toggleTodoUseCase = toggleTodoUseCase,
                    onBack = { navigation.pop() },
                    onDeleted = { id -> 
                        onTodoDeleted(id)
                        navigation.pop()
                    },
                    onEdit = { id -> onTodoEditClicked(id) }
                )
            )
            is Config.Create -> TodoListComponent.Child.Create(
                DefaultTodoCreateComponent(
                    componentContext = componentContext,
                    addTodoUseCase = addTodoUseCase,
                    onBackClicked = { navigation.pop() },
                    onSaveClicked = { navigation.pop() }
                )
            )
            is Config.Edit -> TodoListComponent.Child.Edit(
                DefaultTodoEditComponent(
                    componentContext = componentContext,
                    todoId = config.todoId,
                    getTodoByIdUseCase = getTodoByIdUseCase,
                    updateTodoUseCase = updateTodoUseCase,
                    onBackClicked = { navigation.pop() },
                    onSaveClicked = { 
                        // Force refresh of data
                        componentScope.launch {
                            // Small delay to ensure repository update completes
                            kotlinx.coroutines.delay(100)
                            // Refresh todos to get latest changes
                            model.value = model.value.copy(isLoading = true)
                        }
                        navigation.pop() 
                    }
                )
            )
        }
    
    override fun onAddTodoClicked() {
        navigation.push(Config.Create)
    }
    
    override fun onTodoClicked(id: String) {
        navigation.push(Config.Details(todoId = id))
    }
    
    override fun onTodoCompleted(id: String) {
        componentScope.launch {
            toggleTodoUseCase(id)
        }
    }
    
    override fun onTodoDeleted(id: String) {
        componentScope.launch {
            deleteTodoUseCase(id)
        }
    }
    
    private fun onTodoStatusChanged(id: String, status: TodoStatus) {
        componentScope.launch {
            changeTodoStatusUseCase(id, status)
        }
    }
    
    private fun onTodoEditClicked(id: String) {
        navigation.push(Config.Edit(todoId = id))
    }
    
    override fun onBackPressed() {
        navigation.pop()
    }
    
    @Serializable
    private sealed interface Config {
        @Serializable
        data object List : Config
        
        @Serializable
        data class Details(val todoId: String) : Config
        
        @Serializable
        data object Create : Config
        
        @Serializable
        data class Edit(val todoId: String) : Config
    }
    
    class Factory(
        private val getTodosUseCase: GetTodosUseCase,
        private val toggleTodoUseCase: ToggleTodoUseCase,
        private val deleteTodoUseCase: DeleteTodoUseCase,
        private val changeTodoStatusUseCase: ChangeTodoStatusUseCase,
        private val getTodoByIdUseCase: GetTodoByIdUseCase,
        private val addTodoUseCase: AddTodoUseCase,
        private val updateTodoTagsUseCase: UpdateTodoTagsUseCase,
        private val updateTodoUseCase: UpdateTodoUseCase
    ) {
        fun create(componentContext: ComponentContext): DefaultTodoListComponent {
            return DefaultTodoListComponent(
                componentContext = componentContext,
                getTodosUseCase = getTodosUseCase,
                toggleTodoUseCase = toggleTodoUseCase,
                deleteTodoUseCase = deleteTodoUseCase,
                changeTodoStatusUseCase = changeTodoStatusUseCase,
                getTodoByIdUseCase = getTodoByIdUseCase,
                addTodoUseCase = addTodoUseCase,
                updateTodoTagsUseCase = updateTodoTagsUseCase,
                updateTodoUseCase = updateTodoUseCase
            )
        }
    }
} 