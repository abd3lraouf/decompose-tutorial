package dev.abd3lraouf.learn.decompose.features.statistics.presentation.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.woowla.compose.icon.collections.heroicons.Heroicons
import com.woowla.compose.icon.collections.heroicons.heroicons.Outline
import com.woowla.compose.icon.collections.heroicons.heroicons.Solid
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.CheckCircle
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.ExclamationCircle
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.MinusCircle
import com.woowla.compose.icon.collections.heroicons.heroicons.solid.ChartBar
import com.woowla.compose.icon.collections.heroicons.heroicons.solid.Clock
import com.woowla.compose.icon.collections.heroicons.heroicons.solid.Fire
import dev.abd3lraouf.learn.decompose.features.statistics.presentation.StatsComponent
import dev.abd3lraouf.learn.decompose.ui.preview.PreviewStatsComponent
import dev.abd3lraouf.learn.decompose.ui.theme.DecomposeTutorialTheme

@Composable
fun StatsContent(
    component: StatsComponent,
    modifier: Modifier = Modifier
) {
    val stats by component.model.subscribeAsState()
    
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Main statistics card
        StatisticsCard(
            title = "Total Todos",
            value = stats.totalCount,
            maxValue = stats.totalCount,
            icon = Heroicons.Solid.ChartBar,
            color = MaterialTheme.colorScheme.primary,
            showProgress = true
        )
        
        // Completion status
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            StatisticsCard(
                title = "Completed",
                value = stats.completedCount,
                maxValue = stats.totalCount,
                icon = Heroicons.Outline.CheckCircle,
                color = Color(0xFF4CAF50), // Green
                modifier = Modifier.weight(1f),
                showProgress = true
            )
            
            StatisticsCard(
                title = "Pending",
                value = stats.pendingCount,
                maxValue = stats.totalCount,
                icon = Heroicons.Solid.Clock,
                color = Color(0xFFF57C00), // Orange
                modifier = Modifier.weight(1f),
                showProgress = true
            )
        }
        
        // Priority section
        Text(
            text = "By Priority",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            StatisticsCard(
                title = "High",
                value = stats.highPriorityCount,
                maxValue = stats.totalCount,
                icon = Heroicons.Solid.Fire,
                color = Color(0xFFF44336), // Red
                modifier = Modifier.weight(1f),
                centerAlign = true
            )
            
            StatisticsCard(
                title = "Medium",
                value = stats.mediumPriorityCount,
                maxValue = stats.totalCount,
                icon = Heroicons.Outline.ExclamationCircle,
                color = Color(0xFFFFC107), // Yellow/Amber
                modifier = Modifier.weight(1f),
                centerAlign = true
            )
            
            StatisticsCard(
                title = "Low",
                value = stats.lowPriorityCount,
                maxValue = stats.totalCount,
                icon = Heroicons.Outline.MinusCircle,
                color = Color(0xFF4CAF50), // Green
                modifier = Modifier.weight(1f),
                centerAlign = true
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StatsContentPreview() {
    DecomposeTutorialTheme {
        StatsContent(component = PreviewStatsComponent())
    }
} 