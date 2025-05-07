package dev.abd3lraouf.learn.decompose.features.settings.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import dev.abd3lraouf.learn.decompose.features.settings.data.SettingsManager
import dev.abd3lraouf.learn.decompose.features.settings.domain.model.AppSettings

class DefaultSettingsComponent(
    componentContext: ComponentContext,
    private val settingsManager: SettingsManager
) : SettingsComponent, ComponentContext by componentContext {
    
    override val model: MutableValue<AppSettings> = MutableValue(settingsManager.settings.value)
    
    override fun onThemeChanged(isDarkMode: Boolean) {
        settingsManager.updateDarkMode(isDarkMode)
        model.value = model.value.copy(isDarkMode = isDarkMode)
    }

    class Factory(private val settingsManager: SettingsManager) {
        fun create(componentContext: ComponentContext): DefaultSettingsComponent {
            return DefaultSettingsComponent(componentContext, settingsManager)
        }
    }
} 