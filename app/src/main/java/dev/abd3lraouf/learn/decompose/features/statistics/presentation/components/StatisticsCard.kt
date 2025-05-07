package dev.abd3lraouf.learn.decompose.features.statistics.presentation.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.woowla.compose.icon.collections.heroicons.Heroicons
import com.woowla.compose.icon.collections.heroicons.heroicons.Outline
import com.woowla.compose.icon.collections.heroicons.heroicons.Solid
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.CheckCircle
import com.woowla.compose.icon.collections.heroicons.heroicons.solid.ChartBar
import com.woowla.compose.icon.collections.heroicons.heroicons.solid.Clock
import dev.abd3lraouf.learn.decompose.ui.theme.DecomposeTutorialTheme
import kotlin.math.roundToInt

/**
 * Enhanced statistics card with optional icon, progress indicator, and animations
 */
@Composable
fun StatisticsCard(
    title: String,
    value: Int,
    color: Color,
    modifier: Modifier = Modifier,
    maxValue: Int = 0,
    icon: ImageVector? = null,
    showProgress: Boolean = false,
    centerAlign: Boolean = false
) {
    val progress = remember(value, maxValue) {
        if (maxValue == 0) 0f else value.toFloat() / maxValue
    }
    
    val animatedProgress = remember { Animatable(0f) }
    
    LaunchedEffect(progress) {
        animatedProgress.animateTo(
            targetValue = progress,
            animationSpec = tween(durationMillis = 1000)
        )
    }
    
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(containerColor = color.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = if (centerAlign) Alignment.CenterHorizontally else Alignment.Start
        ) {
            if (icon != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = null,
                        tint = color,
                        modifier = Modifier.size(28.dp)
                    )
                    
                    Spacer(modifier = Modifier.weight(1f))
                    
                    // Percentage
                    if (maxValue > 0) {
                        Text(
                            text = "${(progress * 100).roundToInt()}%",
                            style = MaterialTheme.typography.labelLarge,
                            color = color
                        )
                    }
                }
                
                Spacer(modifier = Modifier.height(8.dp))
            }
            
            Text(
                text = title,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
            )
            
            Spacer(modifier = Modifier.height(4.dp))
            
            AnimatedContent(
                targetState = value,
                transitionSpec = { fadeIn() togetherWith fadeOut() },
                label = "Statistics value"
            ) { targetValue ->
                Text(
                    text = targetValue.toString(),
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = color
                )
            }
            
            if (showProgress && maxValue > 0) {
                Spacer(modifier = Modifier.height(8.dp))
                
                LinearProgressIndicator(
                    progress = { animatedProgress.value },
                    color = color,
                    trackColor = color.copy(alpha = 0.2f),
                    strokeCap = StrokeCap.Round,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(6.dp)
                        .clip(RoundedCornerShape(4.dp))
                )
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun StatisticsCardPreview() {
    DecomposeTutorialTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    StatisticsCard(
                        title = "Total",
                        value = 12,
                        maxValue = 12,
                        icon = Heroicons.Solid.ChartBar,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f)
                    )

                    StatisticsCard(
                        title = "Completed",
                        value = 7,
                        maxValue = 12,
                        icon = Heroicons.Outline.CheckCircle,
                        color = Color(0xFF4CAF50),
                        modifier = Modifier.weight(1f),
                        showProgress = true
                    )
                }

                StatisticsCard(
                    title = "Pending",
                    value = 5,
                    maxValue = 12,
                    icon = Heroicons.Solid.Clock,
                    color = Color(0xFFF57C00),
                    showProgress = true
                )
            }
        }
    }
} 