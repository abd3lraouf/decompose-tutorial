package dev.abd3lraouf.learn.decompose.navigation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pop
import com.arkivanov.decompose.value.Value
import kotlinx.serialization.Serializable
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class DefaultRootComponent(
    componentContext: ComponentContext
) : RootComponent, ComponentContext by componentContext, KoinComponent {
    
    private val navigation = StackNavigation<Config>()
    private val tabNavigationFactory: DefaultTabNavigationComponent.Factory by inject()

    override val stack: Value<ChildStack<*, RootComponent.Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        initialConfiguration = Config.Tabs,
        handleBackButton = true,
        childFactory = ::child,
    )

    private fun child(
        config: Config, componentContext: ComponentContext
    ): RootComponent.Child {
        return when (config) {
            Config.Tabs -> RootComponent.Child.TabsChild(
                tabNavigationFactory.create(componentContext)
            )
        }
    }

    override fun onBackClicked(toIndex: Int) {
        navigation.pop()
    }

    @Serializable
    private sealed interface Config {
        @Serializable
        data object Tabs : Config
    }
} 