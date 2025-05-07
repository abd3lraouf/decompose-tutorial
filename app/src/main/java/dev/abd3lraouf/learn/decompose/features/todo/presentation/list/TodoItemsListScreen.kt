package dev.abd3lraouf.learn.decompose.features.todo.presentation.list

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import dev.abd3lraouf.learn.decompose.features.todo.domain.model.TodoStatus
import com.woowla.compose.icon.collections.heroicons.Heroicons
import com.woowla.compose.icon.collections.heroicons.heroicons.Outline
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.ExclamationCircle
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.InformationCircle
import dev.abd3lraouf.learn.decompose.ui.components.TodoCard
import kotlinx.coroutines.launch
import kotlin.math.abs
import androidx.compose.ui.tooling.preview.Preview
import dev.abd3lraouf.learn.decompose.ui.preview.PreviewListComponent
import dev.abd3lraouf.learn.decompose.ui.theme.DecomposeTutorialTheme
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.ArrowDown
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.ArrowUp
import com.woowla.compose.icon.collections.heroicons.heroicons.outline.Funnel
import dev.abd3lraouf.learn.decompose.features.todo.presentation.list.ListComponent.SortOption
import androidx.compose.material3.Surface

@Composable
fun TodoItemsListScreen(
    component: ListComponent,
    modifier: Modifier = Modifier
) {
    val model by component.model.subscribeAsState()
    val coroutineScope = rememberCoroutineScope()
    
    val tabs = listOf(
        TabInfo("TODO", model.items.count { it.status == TodoStatus.TODO }),
        TabInfo("IN PROGRESS", model.items.count { it.status == TodoStatus.IN_PROGRESS }),
        TabInfo("DONE", model.items.count { it.status == TodoStatus.DONE })
    )
    
    val pagerState = rememberPagerState(
        initialPage = 0,
        pageCount = { tabs.size }
    )
    
    // Track tab sizes for animation
    val tabWidths = remember { mutableStateMapOf<Int, Int>() }
    
    Column(modifier = modifier.fillMaxSize()) {
        TabRow(
            selectedTabIndex = pagerState.currentPage,
            containerColor = Color.Transparent,
            contentColor = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth(),
            indicator = { tabPositions ->
                AnimatedTabIndicator(
                    tabPositions = tabPositions,
                    pagerState = pagerState
                )
            },
            divider = {}
        ) {
            tabs.forEachIndexed { index, tabInfo ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    modifier = Modifier.onSizeChanged { tabWidths[index] = it.width },
                    text = {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(vertical = 8.dp),
                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = tabInfo.title,
                                    style = MaterialTheme.typography.titleSmall
                                )

                                Text(
                                    text = tabInfo.count.toString(),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }
                )
            }
        }
        
        // Sorting menu (moved from MainActivity.kt)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Sort by:",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(8.dp))
            SortingMenu(
                currentSort = model.sortOption,
                onSortSelected = { component.onSortOptionSelected(it) }
            )
        }
        
        Spacer(modifier = Modifier.height(8.dp))
        
        // Horizontal pager for the content
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            val status = when (page) {
                0 -> TodoStatus.TODO
                1 -> TodoStatus.IN_PROGRESS
                else -> TodoStatus.DONE
            }
            
            val filteredTodos = model.items.filter { it.status == status }
            
            // For IN_PROGRESS tab, check if there are more than 2 items
            val showTooManyInProgressHint = status == TodoStatus.IN_PROGRESS && filteredTodos.size > 2
            
            Column(modifier = Modifier.fillMaxSize()) {
                // Show hint if there are more than 2 tasks in progress
                AnimatedVisibility(
                    visible = showTooManyInProgressHint,
                    enter = fadeIn() + expandVertically(),
                    exit = fadeOut() + shrinkVertically()
                ) {
                    InProgressHintCard()
                }
                
                Box(modifier = Modifier.weight(1f)) {
                    if (filteredTodos.isEmpty()) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "No tasks in this category yet.",
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        val listState = rememberLazyListState()
                        
                        LazyColumn(
                            state = listState,
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp)
                        ) {
                            items(filteredTodos) { todo ->
                                TodoCard(
                                    todo = todo,
                                    onTodoClicked = { component.onItemSelected(todo.id) },
                                    onCompletedChanged = { component.onCompletedToggled(todo.id) }
                                )
                            }
                        }
                    }
                }
                
                // Status guidance card at the bottom
                CategoryGuidanceCard(status)
            }
        }
    }
}

@Composable
fun CategoryGuidanceCard(status: TodoStatus) {
    val (message, icon) = when (status) {
        TodoStatus.TODO -> "These are tasks you need to start working on." to Heroicons.Outline.InformationCircle
        TodoStatus.IN_PROGRESS -> "These are tasks you're currently working on." to Heroicons.Outline.InformationCircle
        TodoStatus.DONE -> "Congratulations on completing these tasks!" to Heroicons.Outline.InformationCircle
    }
    
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "Information",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(end = 12.dp)
            )
            
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun InProgressHintCard() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.errorContainer.copy(alpha = 0.7f)
        ),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Heroicons.Outline.ExclamationCircle,
                contentDescription = "Warning",
                tint = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(end = 12.dp)
            )
            
            Text(
                text = "Productivity tip: It's recommended to limit tasks in progress to 2 at a time for better focus and productivity.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onErrorContainer
            )
        }
    }
}

@Composable
fun AnimatedTabIndicator(
    tabPositions: List<TabPosition>,
    pagerState: PagerState
) {
    val currentPage = pagerState.currentPage
    val pageOffset = pagerState.currentPageOffsetFraction
    val baseColor = MaterialTheme.colorScheme.primary
    
    // Calculate the animated indicator position
    val indicatorOffset by remember(currentPage, pageOffset, tabPositions) {
        derivedStateOf {
            // When between pages, calculate the interpolated position
            val currentTab = tabPositions[currentPage]
            
            if (pageOffset != 0f) {
                // Direction of swipe - clamped to valid tab indices
                val targetPage = (currentPage + if (pageOffset > 0) 1 else -1)
                    .coerceIn(0, tabPositions.lastIndex)
                
                // Only interpolate if target page is different from current
                if (targetPage != currentPage) {
                    val targetTab = tabPositions[targetPage]
                    val fraction = abs(pageOffset)
                    
                    // Interpolate position and width between current and target
                    val targetOffset = (targetTab.left - currentTab.left) * fraction
                    val widthDelta = (targetTab.width - currentTab.width) * fraction
                    
                    Pair(
                        currentTab.left + targetOffset,
                        currentTab.width + widthDelta
                    )
                } else {
                    // We're at an edge tab and trying to scroll beyond - maintain position
                    Pair(currentTab.left, currentTab.width)
                }
            } else {
                Pair(currentTab.left, currentTab.width)
            }
        }
    }
    
    // Animation for the indicator height based on movement
    val indicatorHeight by remember(pageOffset) {
        derivedStateOf {
            // Make the indicator slightly thicker when moving between pages
            val baseHeight = 2.5f
            val extraHeight = 1.5f * abs(pageOffset)
            (baseHeight + extraHeight).dp
        }
    }
    
    // Animation for indicator color based on movement
    val indicatorColor by remember(pageOffset) {
        derivedStateOf {
            // Make the indicator slightly more saturated when moving
            if (abs(pageOffset) > 0.1f) {
                baseColor.copy(alpha = 0.9f + 0.1f * abs(pageOffset))
            } else {
                baseColor
            }
        }
    }
    
    // Draw the animated indicator
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(align = Alignment.BottomStart)
            .offset(x = indicatorOffset.first)
            .width(indicatorOffset.second)
            .padding(horizontal = 8.dp)
            .height(indicatorHeight)
            .background(
                color = indicatorColor,
                shape = MaterialTheme.shapes.small
            )
            // Add a subtle bounce effect
            .graphicsLayer {
                val scale = 1f + 0.1f * abs(pageOffset)
                scaleX = scale
            }
    )
}

data class TabInfo(val title: String, val count: Int)

@Composable
fun TabItem(
    title: String,
    count: Int,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
            )
            
            Text(
                text = count.toString(),
                style = MaterialTheme.typography.bodyMedium,
                color = if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
            )
        }
        
        Spacer(modifier = Modifier.height(4.dp))
        
        // Indicator line
        Box(
            modifier = Modifier
                .height(2.dp)
                .fillMaxWidth()
                .background(
                    if (selected) MaterialTheme.colorScheme.primary
                    else Color.Transparent
                )
        )
    }
}

@Composable
fun SortingMenu(
    currentSort: SortOption,
    onSortSelected: (SortOption) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val sortIcon = when (currentSort) {
        SortOption.NONE -> Heroicons.Outline.Funnel
        SortOption.PRIORITY_ASC, SortOption.DATE_ASC -> Heroicons.Outline.ArrowUp
        SortOption.PRIORITY_DESC, SortOption.DATE_DESC -> Heroicons.Outline.ArrowDown
    }

    val sortLabel = when (currentSort) {
        SortOption.NONE -> "Not sorted"
        SortOption.PRIORITY_ASC -> "Priority (Low → High)"
        SortOption.PRIORITY_DESC -> "Priority (High → Low)"
        SortOption.DATE_ASC -> "Date (Older first)"
        SortOption.DATE_DESC -> "Date (Recent first)"
    }

    Box(modifier = Modifier.wrapContentSize(Alignment.TopEnd)) {
        Row(
            modifier = Modifier
                .clickable { expanded = true }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = sortLabel,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                imageVector = sortIcon,
                contentDescription = "Sorting options",
                tint = MaterialTheme.colorScheme.primary
            )
        }

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                text = { Text("No sorting") },
                onClick = {
                    onSortSelected(SortOption.NONE)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Priority (Low → High)") },
                onClick = {
                    onSortSelected(SortOption.PRIORITY_ASC)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Priority (High → Low)") },
                onClick = {
                    onSortSelected(SortOption.PRIORITY_DESC)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Date (Older first)") },
                onClick = {
                    onSortSelected(SortOption.DATE_ASC)
                    expanded = false
                }
            )
            DropdownMenuItem(
                text = { Text("Date (Recent first)") },
                onClick = {
                    onSortSelected(SortOption.DATE_DESC)
                    expanded = false
                }
            )
        }
    }
}

// Add these previews at the end of the file
@Preview(showBackground = true)
@Composable
fun TodoItemsListScreenPreview() {
    DecomposeTutorialTheme {
        TodoItemsListScreen(component = PreviewListComponent())
    }
}

@Preview(showBackground = true)
@Composable
fun CategoryGuidanceCardPreview() {
    DecomposeTutorialTheme {
        CategoryGuidanceCard(status = TodoStatus.TODO)
    }
}

@Preview(showBackground = true)
@Composable
fun SortingMenuPreview() {
    DecomposeTutorialTheme {
        Surface {
            SortingMenu(
                currentSort = SortOption.PRIORITY_DESC,
                onSortSelected = {}
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InProgressHintCardPreview() {
    DecomposeTutorialTheme {
        InProgressHintCard()
    }
} 