package dev.abd3lraouf.learn.decompose.features.statistics.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import dev.abd3lraouf.learn.decompose.features.statistics.domain.model.TodoStatistics
import dev.abd3lraouf.learn.decompose.features.statistics.domain.usecase.GetTodoStatisticsUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class DefaultStatsComponent(
    componentContext: ComponentContext,
    getTodoStatisticsUseCase: GetTodoStatisticsUseCase
) : StatsComponent, ComponentContext by componentContext {
    
    private val componentScope = CoroutineScope(Dispatchers.Main + SupervisorJob())
    
    override val model: MutableValue<TodoStatistics> = MutableValue(TodoStatistics())
    
    init {
        getTodoStatisticsUseCase().onEach { stats ->
            model.value = stats
        }.launchIn(componentScope)
    }
    
    class Factory(private val getTodoStatisticsUseCase: GetTodoStatisticsUseCase) {
        fun create(componentContext: ComponentContext): DefaultStatsComponent {
            return DefaultStatsComponent(
                componentContext = componentContext,
                getTodoStatisticsUseCase = getTodoStatisticsUseCase
            )
        }
    }
} 