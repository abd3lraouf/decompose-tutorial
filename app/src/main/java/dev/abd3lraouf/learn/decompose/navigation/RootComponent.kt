package dev.abd3lraouf.learn.decompose.navigation

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    fun onBackClicked(toIndex: Int = -1)

    sealed class Child {
        data class TabsChild(val component: TabNavigationComponent) : Child()
    }
} 