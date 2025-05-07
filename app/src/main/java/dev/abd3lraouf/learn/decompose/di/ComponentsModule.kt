package dev.abd3lraouf.learn.decompose.di

import com.arkivanov.decompose.ComponentContext
import dev.abd3lraouf.learn.decompose.navigation.DefaultRootComponent
import dev.abd3lraouf.learn.decompose.navigation.RootComponent
import org.koin.dsl.module

val componentsModule = module {
    factory { params ->
        DefaultRootComponent(componentContext = params.get<ComponentContext>())
    }
} 