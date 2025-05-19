package dev.abd3lraouf.learn.decompose.features.settings.presentation.ui

import androidx.compose.runtime.Composable
import dev.abd3lraouf.learn.decompose.ui.components.TopAppBarConfig

/**
 * Creates TopAppBar configuration for Settings feature screens
 */
@Composable
fun createSettingsTopAppBarConfig(): TopAppBarConfig {
    return TopAppBarConfig(title = "Settings")
} 