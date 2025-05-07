package dev.abd3lraouf.learn.decompose.di

import dev.abd3lraouf.learn.decompose.features.settings.presentation.DefaultSettingsComponent
import dev.abd3lraouf.learn.decompose.features.statistics.presentation.DefaultStatsComponent
import dev.abd3lraouf.learn.decompose.features.todo.presentation.create.DefaultTodoCreateComponent
import dev.abd3lraouf.learn.decompose.features.todo.presentation.details.DefaultTodoDetailsComponent
import dev.abd3lraouf.learn.decompose.features.todo.presentation.edit.DefaultTodoEditComponent
import dev.abd3lraouf.learn.decompose.features.todo.presentation.list.DefaultListComponent
import dev.abd3lraouf.learn.decompose.features.todo.presentation.list.DefaultTodoListComponent
import dev.abd3lraouf.learn.decompose.navigation.DefaultTabNavigationComponent
import org.koin.dsl.module

val presentationModule = module {
    // Factories for Components
    factory { 
        DefaultTabNavigationComponent.Factory(
            getTodosUseCase = get(),
            todoListComponentFactory = get(),
            statsComponentFactory = get(),
            settingsComponentFactory = get()
        )
    }
    
    factory { 
        DefaultTodoListComponent.Factory(
            getTodosUseCase = get(),
            toggleTodoUseCase = get(),
            deleteTodoUseCase = get(),
            changeTodoStatusUseCase = get(),
            getTodoByIdUseCase = get(),
            addTodoUseCase = get(),
            updateTodoTagsUseCase = get(),
            updateTodoUseCase = get()
        )
    }
    
    factory { 
        DefaultTodoDetailsComponent.Factory(
            getTodoByIdUseCase = get(),
            deleteTodoUseCase = get(),
            toggleTodoUseCase = get()
        )
    }
    
    factory { 
        DefaultTodoCreateComponent.Factory(
            addTodoUseCase = get()
        )
    }
    
    factory { DefaultTodoEditComponent.Factory(
        getTodoByIdUseCase = get(),
        updateTodoUseCase = get()
    ) }
    
    factory { DefaultStatsComponent.Factory(getTodoStatisticsUseCase = get()) }
    
    factory { DefaultSettingsComponent.Factory(settingsManager = get()) }
} 