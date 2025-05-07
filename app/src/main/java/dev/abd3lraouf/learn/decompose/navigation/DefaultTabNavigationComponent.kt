package dev.abd3lraouf.learn.decompose.navigation

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.active
import com.arkivanov.decompose.router.stack.bringToFront
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.items
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import dev.abd3lraouf.learn.decompose.features.settings.presentation.DefaultSettingsComponent
import dev.abd3lraouf.learn.decompose.features.statistics.presentation.DefaultStatsComponent
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoStatus
import dev.abd3lraouf.learn.decompose.features.todo.domain.usecase.AddTodoUseCase
import dev.abd3lraouf.learn.decompose.features.todo.domain.usecase.ChangeTodoStatusUseCase
import dev.abd3lraouf.learn.decompose.features.todo.domain.usecase.DeleteTodoUseCase
import dev.abd3lraouf.learn.decompose.features.todo.domain.usecase.GetTodoByIdUseCase
import dev.abd3lraouf.learn.decompose.features.todo.domain.usecase.GetTodosUseCase
import dev.abd3lraouf.learn.decompose.features.todo.domain.usecase.ToggleTodoUseCase
import dev.abd3lraouf.learn.decompose.features.todo.presentation.list.DefaultTodoListComponent
import dev.abd3lraouf.learn.decompose.navigation.TabNavigationComponent.Child
import dev.abd3lraouf.learn.decompose.navigation.TabNavigationComponent.Tab
import dev.abd3lraouf.learn.decompose.features.statistics.domain.usecase.GetTodoStatisticsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DefaultTabNavigationComponent private constructor(
    componentContext: ComponentContext,
    private val todoListComponentFactory: DefaultTodoListComponent.Factory,
    private val statsComponentFactory: DefaultStatsComponent.Factory,
    private val settingsComponentFactory: DefaultSettingsComponent.Factory,
    private val getTodosUseCase: GetTodosUseCase
) : TabNavigationComponent, ComponentContext by componentContext, KoinComponent {

    private val navigation = StackNavigation<Config>()
    private val componentScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    
    override val model: MutableValue<TabNavigationComponent.Model> = MutableValue(TabNavigationComponent.Model())

    override val childStack: Value<ChildStack<*, Child>> =
        childStack(
            source = navigation,
            serializer = Config.serializer(),
            initialConfiguration = Config.TodoList,
            childFactory = ::createChild,
            handleBackButton = true
        )
        
    init {
        // Subscribe to todos to track in progress tasks count
        getTodosUseCase().onEach { todos ->
            val inProgressCount = todos.count { it.status == TodoStatus.IN_PROGRESS }
            model.value = model.value.copy(inProgressCount = inProgressCount)
        }.launchIn(componentScope)

    }

    private fun createChild(config: Config, componentContext: ComponentContext): Child =
        when (config) {
            is Config.TodoList -> Child.TodoListChild(
                todoListComponentFactory.create(componentContext)
            )
            is Config.Stats -> Child.StatsChild(
                statsComponentFactory.create(componentContext)
            )
            is Config.Settings -> Child.SettingsChild(
                settingsComponentFactory.create(componentContext)
            )
        }

    override fun onTabSelected(tab: Tab) {
        val config = when (tab) {
            Tab.TODO_LIST -> Config.TodoList
            Tab.STATS -> Config.Stats
            Tab.SETTINGS -> Config.Settings
        }
        navigation.bringToFront(config)
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object TodoList : Config

        @Serializable
        data object Stats : Config

        @Serializable
        data object Settings : Config
    }
    
    class Factory(
        private val getTodosUseCase: GetTodosUseCase,
        private val todoListComponentFactory: DefaultTodoListComponent.Factory,
        private val statsComponentFactory: DefaultStatsComponent.Factory,
        private val settingsComponentFactory: DefaultSettingsComponent.Factory
    ) {
        fun create(componentContext: ComponentContext): DefaultTabNavigationComponent {
            return DefaultTabNavigationComponent(
                componentContext = componentContext,
                todoListComponentFactory = todoListComponentFactory,
                statsComponentFactory = statsComponentFactory,
                settingsComponentFactory = settingsComponentFactory,
                getTodosUseCase = getTodosUseCase
            )
        }
    }
} 