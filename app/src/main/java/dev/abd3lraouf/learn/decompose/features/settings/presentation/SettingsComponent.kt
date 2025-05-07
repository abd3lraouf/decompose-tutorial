package dev.abd3lraouf.learn.decompose.features.settings.presentation

import com.arkivanov.decompose.value.Value
import dev.abd3lraouf.learn.decompose.features.settings.domain.model.AppSettings

interface SettingsComponent {
    val model: Value<AppSettings>
    
    fun onThemeChanged(isDarkMode: Boolean)
}