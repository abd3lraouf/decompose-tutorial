package dev.abd3lraouf.learn.decompose.di

import dev.abd3lraouf.learn.decompose.features.statistics.domain.usecase.GetTodoStatisticsUseCase
import dev.abd3lraouf.learn.decompose.features.todo.domain.usecase.AddTodoUseCase
import dev.abd3lraouf.learn.decompose.features.todo.domain.usecase.ChangeTodoStatusUseCase
import dev.abd3lraouf.learn.decompose.features.todo.domain.usecase.DeleteTodoUseCase
import dev.abd3lraouf.learn.decompose.features.todo.domain.usecase.GetTodoByIdUseCase
import dev.abd3lraouf.learn.decompose.features.todo.domain.usecase.GetTodosUseCase
import dev.abd3lraouf.learn.decompose.features.todo.domain.usecase.ToggleTodoUseCase
import dev.abd3lraouf.learn.decompose.features.todo.domain.usecase.UpdateTodoTagsUseCase
import dev.abd3lraouf.learn.decompose.features.todo.domain.usecase.UpdateTodoUseCase
import org.koin.dsl.module

val domainModule = module {
    // Todo Use Cases
    factory { GetTodosUseCase(get()) }
    factory { GetTodoByIdUseCase(get()) }
    factory { AddTodoUseCase(get()) }
    factory { DeleteTodoUseCase(get()) }
    factory { ChangeTodoStatusUseCase(get()) }
    factory { ToggleTodoUseCase(get()) }
    factory { UpdateTodoTagsUseCase(get()) }
    factory { UpdateTodoUseCase(get()) }
    
    // Statistics Use Cases
    factory { GetTodoStatisticsUseCase(get()) }
} 