package dev.abd3lraouf.learn.decompose.features.settings.data

import android.content.Context
import android.content.SharedPreferences
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dev.abd3lraouf.learn.decompose.features.settings.domain.model.AppSettings
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Manages app settings and provides reactive state updates
 */
class SettingsManager(context: Context) {
    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    
    private val _settings = MutableStateFlow(loadSettings())
    val settings: StateFlow<AppSettings> = _settings.asStateFlow()
    
    // Compose state for direct observation in Composables
    private val _darkModeState = mutableStateOf(loadSettings().isDarkMode)
    val darkModeState: State<Boolean> = _darkModeState
    
    fun updateDarkMode(isDarkMode: Boolean) {
        val currentSettings = _settings.value
        val newSettings = currentSettings.copy(isDarkMode = isDarkMode)
        _settings.value = newSettings
        _darkModeState.value = isDarkMode
        saveSettings(newSettings)
    }
    
    /**
     * Initialize the settings with the system's dark mode preference if not already set
     */
    @Composable
    fun initializeWithSystemTheme() {
        if (!prefs.contains(KEY_DARK_MODE)) {
            val systemDarkMode = isSystemInDarkTheme()
            updateDarkMode(systemDarkMode)
        }
    }
    
    private fun loadSettings(): AppSettings {
        return AppSettings(
            isDarkMode = prefs.getBoolean(KEY_DARK_MODE, false)
        )
    }
    
    private fun saveSettings(settings: AppSettings) {
        prefs.edit()
            .putBoolean(KEY_DARK_MODE, settings.isDarkMode)
            .apply()
    }
    
    companion object {
        private const val PREFS_NAME = "decompose_settings"
        private const val KEY_DARK_MODE = "dark_mode"
    }
} 