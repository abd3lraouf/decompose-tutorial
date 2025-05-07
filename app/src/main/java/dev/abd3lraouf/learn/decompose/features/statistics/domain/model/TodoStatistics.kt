package dev.abd3lraouf.learn.decompose.features.statistics.domain.model

data class TodoStatistics(
    val totalCount: Int = 0,
    val completedCount: Int = 0,
    val pendingCount: Int = 0,
    val highPriorityCount: Int = 0,
    val mediumPriorityCount: Int = 0,
    val lowPriorityCount: Int = 0
) 