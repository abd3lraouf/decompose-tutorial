package dev.abd3lraouf.learn.decompose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value

/**
 * Helper function to monitor a child stack when a certain condition is met
 */
@Composable
fun <T : Any> monitorChildStack(
    condition: () -> Boolean,
    childStackProvider: () -> Value<ChildStack<*, T>>?
): Pair<State<ChildStack<*, T>?>, T?> {
    // Get child stack when condition is met, otherwise null
    val childStack = if (condition()) {
        childStackProvider()?.subscribeAsState()
    } else null
    
    // Get active child instance or null
    val activeChild = childStack?.value?.active?.instance
    
    return Pair(childStack, activeChild) as Pair<State<ChildStack<*, T>?>, T?>
} 