package dev.abd3lraouf.learn.decompose.features.statistics.presentation

import com.arkivanov.decompose.value.Value
import dev.abd3lraouf.learn.decompose.features.statistics.domain.model.TodoStatistics

interface StatsComponent {
    val model: Value<TodoStatistics>
} 