package dev.abd3lraouf.learn.decompose.di

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import kotlinx.coroutines.Dispatchers
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {
    // Provide coroutine dispatchers
    single(named("IO")) { Dispatchers.IO }
    single(named("Main")) { Dispatchers.Main }
    single(named("Default")) { Dispatchers.Default }
    
    // ComponentContext factory - for creating new component contexts
    factory<ComponentContext> { params ->
        params.get<ComponentContext>()
    }
    
    // Root ComponentContext
    factory {
        DefaultComponentContext(LifecycleRegistry())
    }
} 