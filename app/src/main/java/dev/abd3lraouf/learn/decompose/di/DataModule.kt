package dev.abd3lraouf.learn.decompose.di

import dev.abd3lraouf.learn.decompose.features.settings.data.SettingsManager
import dev.abd3lraouf.learn.decompose.features.todo.data.repository.InMemoryTodoRepository
import dev.abd3lraouf.learn.decompose.features.todo.domain.repository.TodoRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val dataModule = module {
    // Repositories
    single<TodoRepository> { InMemoryTodoRepository() }
    
    // Settings
    single { SettingsManager(androidContext()) }
} 