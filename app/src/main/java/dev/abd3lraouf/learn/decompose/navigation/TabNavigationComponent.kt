package dev.abd3lraouf.learn.decompose.navigation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import dev.abd3lraouf.learn.decompose.features.settings.presentation.SettingsComponent
import dev.abd3lraouf.learn.decompose.features.statistics.presentation.StatsComponent
import dev.abd3lraouf.learn.decompose.features.todo.presentation.list.TodoListComponent

interface TabNavigationComponent {
    val childStack: Value<ChildStack<*, Child>>
    val model: Value<Model>
    
    fun onTabSelected(tab: Tab)
    
    enum class Tab {
        TODO_LIST, 
        STATS,
        SETTINGS
    }
    
    sealed class Child {
        data class TodoListChild(val component: TodoListComponent) : Child()
        data class StatsChild(val component: StatsComponent) : Child()
        data class SettingsChild(val component: SettingsComponent) : Child()
    }
    
    data class Model(
        val inProgressCount: Int = 0
    )
} 