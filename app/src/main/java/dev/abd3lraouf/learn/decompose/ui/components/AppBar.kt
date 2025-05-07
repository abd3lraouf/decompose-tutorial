package dev.abd3lraouf.learn.decompose.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable

/**
 * Data class to hold top app bar configuration
 */
data class TopAppBarConfig(
    val title: String,
    val showBackButton: Boolean = false,
    val onBackClick: () -> Unit = {},
    val actions: @Composable () -> Unit = {}
)

/**
 * Reusable TopAppBar component that adapts to configuration
 */
@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun AppTopBar(config: TopAppBarConfig) {
    if (config.showBackButton) {
        TopAppBar(
            title = { Text(text = config.title) },
            navigationIcon = {
                IconButton(onClick = config.onBackClick) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            actions = { config.actions() }
        )
    } else {
        CenterAlignedTopAppBar(
            title = { Text(text = config.title) },
            actions = { config.actions() }
        )
    }
} 