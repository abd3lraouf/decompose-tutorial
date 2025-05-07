package dev.abd3lraouf.learn.decompose.features.settings.presentation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.woowla.compose.icon.collections.heroicons.AllIcons
import com.woowla.compose.icon.collections.heroicons.Heroicons
import com.woowla.compose.icon.collections.heroicons.heroicons.Outline
import com.woowla.compose.icon.collections.heroicons.heroicons.Solid
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.ChevronDown
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.InformationCircle
import com.woowla.compose.icon.collections.heroicons.heroicons.solid.Cog
import com.woowla.compose.icon.collections.heroicons.heroicons.solid.Document
import com.woowla.compose.icon.collections.heroicons.heroicons.solid.Heart
import com.woowla.compose.icon.collections.heroicons.heroicons.solid.ShieldCheck
import com.woowla.compose.icon.collections.ionicons.Ionicons
import com.woowla.compose.icon.collections.ionicons.ionicons.Filled
import com.woowla.compose.icon.collections.ionicons.ionicons.filled.Moon
import com.woowla.compose.icon.collections.ionicons.ionicons.filled.Sunny
import dev.abd3lraouf.learn.decompose.ui.preview.PreviewSettingsComponent
import dev.abd3lraouf.learn.decompose.ui.theme.DecomposeTutorialTheme

@Composable
fun SettingsContent(
    component: SettingsComponent,
    modifier: Modifier = Modifier
) {
    val settings by component.model.subscribeAsState()
    val scrollState = rememberScrollState()
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primaryContainer),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Heroicons.Solid.Cog,
                    contentDescription = "Settings",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Settings",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))

        // Appearance Card
        SettingsCard(
            title = "Appearance",
            iconTint = Color(0xFFFFA000) // Amber
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = if (settings.isDarkMode) Ionicons.Filled.Moon else Ionicons.Filled.Sunny,
                        contentDescription = "Theme",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(12.dp))
                    Text(
                        text = if (settings.isDarkMode) "Dark Mode" else "Light Mode",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
                
                Switch(
                    checked = settings.isDarkMode,
                    onCheckedChange = { component.onThemeChanged(it) },
                    thumbContent = if (settings.isDarkMode) {
                        {
                            Icon(
                                imageVector = Ionicons.Filled.Moon,
                                contentDescription = null,
                                modifier = Modifier.size(SwitchDefaults.IconSize)
                            )
                        }
                    } else {
                        {
                            Icon(
                                imageVector = Ionicons.Filled.Sunny,
                                contentDescription = null,
                                modifier = Modifier.size(SwitchDefaults.IconSize)
                            )
                        }
                    }
                )
            }
        }

        // Help & Support
        SettingsCard(
            title = "Help & Support",
            icon = Heroicons.Outline.InformationCircle,
            iconTint = MaterialTheme.colorScheme.primary
        ) {
            SettingsItem(
                icon = Heroicons.Solid.Document,
                title = "Documentation",
                subtitle = "Learn how to use this app",
                onClick = { /* Open documentation */ }
            )
            
            Divider(modifier = Modifier.padding(vertical = 8.dp))
            
            SettingsItem(
                icon = Heroicons.Solid.Heart,
                title = "Rate the App",
                subtitle = "Let us know what you think",
                onClick = { /* Open rating */ }
            )
        }

        // About Card
        var showAboutDetails by remember { mutableStateOf(false) }
        val rotationState by animateFloatAsState(
            targetValue = if (showAboutDetails) 180f else 0f,
            label = "Rotation Animation"
        )
        
        SettingsCard(
            title = "About",
            icon = Heroicons.Solid.ShieldCheck,
            iconTint = Color(0xFF4CAF50), // Green
            titleTrailingContent = {
                Icon(
                    imageVector = Heroicons.Outline.ChevronDown,
                    contentDescription = "Expand",
                    modifier = Modifier
                        .rotate(rotationState)
                        .clickable { showAboutDetails = !showAboutDetails }
                )
            }
        ) {
            Text(
                text = "Decompose Tutorial App v1.0",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            
            AnimatedVisibility(visible = showAboutDetails) {
                Column(modifier = Modifier.padding(top = 8.dp)) {
                    Text(
                        text = "This app demonstrates the capabilities of the Decompose library for navigation and state management in Jetpack Compose applications.",
                        style = MaterialTheme.typography.bodyMedium
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                    
                    Text(
                        text = "Developed by Abdelraouf Sabri",
                        style = MaterialTheme.typography.bodySmall
                    )
                    
                    Text(
                        text = "© 2025 abd3lraouf.dev",
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }
        }
    }
}

@Composable
fun SettingsCard(
    title: String,
    icon: ImageVector? = null,
    iconTint: Color = MaterialTheme.colorScheme.primary,
    titleTrailingContent: @Composable (() -> Unit)? = null,
    content: @Composable () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    if (icon != null) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = iconTint,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                    }
                    Text(
                        text = title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
                
                if (titleTrailingContent != null) {
                    titleTrailingContent()
                }
            }
            
            content()
        }
    }
}

@Composable
fun SettingsItem(
    icon: Any,
    title: String,
    subtitle: String? = null,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon as ImageVector,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.size(20.dp)
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsContentPreview() {
    DecomposeTutorialTheme {
        SettingsContent(component = PreviewSettingsComponent())
    }
} 