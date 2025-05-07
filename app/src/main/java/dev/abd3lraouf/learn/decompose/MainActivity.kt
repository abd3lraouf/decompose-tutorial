package dev.abd3lraouf.learn.decompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.defaultComponentContext
import com.arkivanov.decompose.extensions.compose.experimental.stack.ChildStack
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.woowla.compose.icon.collections.heroicons.Heroicons
import com.woowla.compose.icon.collections.heroicons.heroicons.Outline
import com.woowla.compose.icon.collections.heroicons.heroicons.Solid
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.Funnel
import com.woowla.compose.icon.collections.heroicons.heroicons.solid.ChartBar
import com.woowla.compose.icon.collections.heroicons.heroicons.solid.Cog
import dev.abd3lraouf.learn.decompose.features.settings.data.SettingsManager
import dev.abd3lraouf.learn.decompose.features.settings.presentation.SettingsContent
import dev.abd3lraouf.learn.decompose.features.settings.presentation.components.createSettingsTopAppBarConfig
import dev.abd3lraouf.learn.decompose.features.statistics.presentation.StatsContent
import dev.abd3lraouf.learn.decompose.features.statistics.presentation.components.createStatsTopAppBarConfig
import dev.abd3lraouf.learn.decompose.features.todo.presentation.components.AddTaskFab
import dev.abd3lraouf.learn.decompose.features.todo.presentation.components.createTodoTopAppBarConfig
import dev.abd3lraouf.learn.decompose.features.todo.presentation.list.TodoListComponent
import dev.abd3lraouf.learn.decompose.features.todo.presentation.list.TodoListContent
import dev.abd3lraouf.learn.decompose.navigation.DefaultRootComponent
import dev.abd3lraouf.learn.decompose.navigation.RootComponent
import dev.abd3lraouf.learn.decompose.navigation.TabNavigationComponent
import dev.abd3lraouf.learn.decompose.navigation.TabNavigationComponent.Child.SettingsChild
import dev.abd3lraouf.learn.decompose.navigation.TabNavigationComponent.Child.StatsChild
import dev.abd3lraouf.learn.decompose.navigation.TabNavigationComponent.Child.TodoListChild
import dev.abd3lraouf.learn.decompose.navigation.components.AppBottomNavigationBar
import dev.abd3lraouf.learn.decompose.navigation.monitorChildStack
import dev.abd3lraouf.learn.decompose.ui.components.AppTopBar
import dev.abd3lraouf.learn.decompose.ui.components.TopAppBarConfig
import dev.abd3lraouf.learn.decompose.ui.preview.PreviewRootComponent
import dev.abd3lraouf.learn.decompose.ui.preview.PreviewTabNavigationComponent
import dev.abd3lraouf.learn.decompose.ui.theme.DecomposeTutorialTheme
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.parameter.parametersOf

class MainActivity : ComponentActivity(), KoinComponent {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Use Koin to get the root component
        val rootComponentContext = defaultComponentContext()
        val root by inject<DefaultRootComponent> { parametersOf(rootComponentContext) }
        val settingsManager by inject<SettingsManager>()

        setContent {
            // Initialize with system theme on first launch
            settingsManager.initializeWithSystemTheme()
            
            // Use the settings manager to get the dark mode state
            val isDarkMode by settingsManager.darkModeState
            
            DecomposeTutorialTheme(darkTheme = isDarkMode) {
                Surface(
                    color = MaterialTheme.colorScheme.background,
                    modifier = Modifier.fillMaxSize()
                ) {
                    RootContent(
                        component = root,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun RootContent(
    component: RootComponent,
    modifier: Modifier = Modifier
) {
    ChildStack(
        stack = component.stack,
        modifier = modifier,
    ) {
        when (val child = it.instance) {
            is RootComponent.Child.TabsChild -> TabNavigationContent(child.component)
        }
    }
}

@OptIn(ExperimentalDecomposeApi::class)
@Composable
fun TabNavigationContent(
    component: TabNavigationComponent,
    modifier: Modifier = Modifier
) {
    val childStack by component.childStack.subscribeAsState()
    val rootChild = childStack.active.instance
    val model by component.model.subscribeAsState()

    // Monitor the TodoList child stack with a generic approach
    val (_, todoChild) = monitorChildStack(
        condition = { rootChild is TodoListChild },
        childStackProvider = { (rootChild as? TodoListChild)?.component?.childStack }
    )

    // Derive UI states based on navigation
    val showAddButton by remember(rootChild, todoChild) {
        derivedStateOf {
            rootChild is TodoListChild &&
                    todoChild is TodoListComponent.Child.List
        }
    }

    // Create a dynamic app bar configuration based on navigation state
    val topAppBarConfig = createTopAppBarConfig(rootChild, todoChild)

    Scaffold(
        modifier = modifier,
        topBar = {
            AppTopBar(topAppBarConfig)
        },
        floatingActionButton = {
            if (showAddButton) {
                AddTaskFab(
                    onClick = {
                        val todoListChild = rootChild as TodoListChild
                        todoListChild.component.onAddTodoClicked()
                    }
                )
            }
        },
        bottomBar = {
            AppBottomNavigationBar(
                activeChild = rootChild,
                inProgressCount = model.inProgressCount,
                onTabSelected = component::onTabSelected
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            ChildStack(
                stack = childStack,
                modifier = Modifier.fillMaxSize()
            ) {
                when (val child = it.instance) {
                    is TodoListChild -> TodoListContent(
                        component = child.component
                    )

                    is StatsChild -> StatsContent(
                        component = child.component
                    )

                    is SettingsChild -> SettingsContent(
                        component = child.component
                    )
                }
            }
        }
    }
}

/**
 * Create TopAppBar configuration based on navigation state
 */
@Composable
private fun createTopAppBarConfig(
    rootChild: TabNavigationComponent.Child,
    todoChild: Any?
): TopAppBarConfig {
    return when (rootChild) {
        is TodoListChild -> createTodoTopAppBarConfig(rootChild, todoChild)
        is StatsChild -> createStatsTopAppBarConfig()
        is SettingsChild -> createSettingsTopAppBarConfig()
    }
}

@Preview(showBackground = true)
@Composable
fun TabNavigationContentPreview() {
    DecomposeTutorialTheme {
        TabNavigationContent(component = PreviewTabNavigationComponent())
    }
}

@Preview(showBackground = true)
@Composable
fun RootContentPreview() {
    DecomposeTutorialTheme {
        RootContent(component = PreviewRootComponent())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun BottomNavigationPreview() {
    DecomposeTutorialTheme {
        Surface {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = {
                        BadgedBox(
                            badge = {
                                Badge { Text("3") }
                            }
                        ) {
                            Icon(
                                imageVector = Heroicons.Outline.Funnel,
                                contentDescription = "Tasks"
                            )
                        }
                    },
                    label = { Text("Tasks") },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = MaterialTheme.colorScheme.primary,
                        selectedTextColor = MaterialTheme.colorScheme.primary,
                        indicatorColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = {
                        Icon(
                            imageVector = Heroicons.Solid.ChartBar,
                            contentDescription = "Statistics"
                        )
                    },
                    label = { Text("Stats") },
                    colors = NavigationBarItemDefaults.colors()
                )

                NavigationBarItem(
                    selected = false,
                    onClick = { },
                    icon = {
                        Icon(
                            imageVector = Heroicons.Solid.Cog,
                            contentDescription = "Settings"
                        )
                    },
                    label = { Text("Settings") },
                    colors = NavigationBarItemDefaults.colors()
                )
            }
        }
    }
}