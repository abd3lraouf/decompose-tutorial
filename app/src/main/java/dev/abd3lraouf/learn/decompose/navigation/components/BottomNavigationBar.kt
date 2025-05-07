package dev.abd3lraouf.learn.decompose.navigation.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.woowla.compose.icon.collections.heroicons.Heroicons
import com.woowla.compose.icon.collections.heroicons.heroicons.Solid
import com.woowla.compose.icon.collections.heroicons.heroicons.solid.ChartBar
import com.woowla.compose.icon.collections.heroicons.heroicons.solid.Cog
import dev.abd3lraouf.learn.decompose.navigation.TabNavigationComponent

/**
 * Bottom navigation bar for the main app navigation
 */
@Composable
fun AppBottomNavigationBar(
    activeChild: TabNavigationComponent.Child,
    inProgressCount: Int,
    onTabSelected: (TabNavigationComponent.Tab) -> Unit
) {
    NavigationBar {
        // Tasks tab
        NavigationBarItem(
            selected = activeChild is TabNavigationComponent.Child.TodoListChild,
            onClick = { onTabSelected(TabNavigationComponent.Tab.TODO_LIST) },
            icon = {
                Text(
                    text = inProgressCount.toString(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )
            },
            label = { Text("Tasks") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.secondaryContainer
            )
        )

        // Stats tab
        NavigationBarItem(
            selected = activeChild is TabNavigationComponent.Child.StatsChild,
            onClick = { onTabSelected(TabNavigationComponent.Tab.STATS) },
            icon = {
                androidx.compose.material3.Icon(
                    imageVector = Heroicons.Solid.ChartBar,
                    contentDescription = "Statistics"
                )
            },
            label = { Text("Stats") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.secondaryContainer
            )
        )

        // Settings tab
        NavigationBarItem(
            selected = activeChild is TabNavigationComponent.Child.SettingsChild,
            onClick = { onTabSelected(TabNavigationComponent.Tab.SETTINGS) },
            icon = {
                androidx.compose.material3.Icon(
                    imageVector = Heroicons.Solid.Cog,
                    contentDescription = "Settings"
                )
            },
            label = { Text("Settings") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = MaterialTheme.colorScheme.primary,
                selectedTextColor = MaterialTheme.colorScheme.primary,
                indicatorColor = MaterialTheme.colorScheme.secondaryContainer
            )
        )
    }
} 