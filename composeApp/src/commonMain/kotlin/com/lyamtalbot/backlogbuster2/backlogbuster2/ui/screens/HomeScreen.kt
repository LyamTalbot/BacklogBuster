package com.lyamtalbot.backlogbuster2.backlogbuster2.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Sort
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material.icons.outlined.VisibilityOff
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.lyamtalbot.backlogbuster2.backlogbuster2.database.colourMap
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.Filters
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.SortType
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.getScreenModel
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.screenmodels.HomescreenModel


class HomeScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        val homescreenModel = getScreenModel<HomescreenModel>()

        val uiState = homescreenModel.uiState.collectAsState()

        val navigator = LocalNavigator.currentOrThrow

        val menuExposed = remember { mutableStateOf(false) }
        val filtersExposed = remember { mutableStateOf(false) }
        val sortsExposed = remember { mutableStateOf(false) }

        val selectedItems = remember { mutableStateOf(setOf<Int>()) }
        val selectMultiple = remember { mutableStateOf(false) }

        Scaffold(modifier = Modifier.fillMaxSize()) { scaffoldPadding ->
            Column(
                verticalArrangement = Arrangement.spacedBy(space = 12.dp),
                modifier = Modifier
                    .safeContentPadding()
            ) {
                //Top Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "Backlog Buster",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = 30.sp,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    //Cancel multiple games selection
                    AnimatedVisibility(visible = selectMultiple.value) {
                        IconButton(onClick = { selectMultiple.value = false }) {
                            Icon(
                                imageVector = Icons.Outlined.CheckCircle,
                                contentDescription = "Close multiple selection",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                    }
                    AnimatedVisibility(
                        visible = !selectMultiple.value,
                    ) {
                        Box(
                            contentAlignment = Alignment.TopEnd
                        ) {
                            IconButton(onClick = {
                                menuExposed.value = true
                            }) {
                                Icon(
                                    Icons.Rounded.Menu,
                                    contentDescription = "menu",
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                )
                            }
                            //Dropdown menu items
                            //1. Select Games
                            //2. Sort by -> Opens another dropdown -> [Title, Rating, Date Added, Date Completed, Time to Beat] | [Asc/Dsc]
                            //3. Show/Hide Completed
                            DropdownMenu(
                                expanded = menuExposed.value,
                                onDismissRequest = { menuExposed.value = !menuExposed.value },
                                modifier = Modifier
                                    .padding(start = 8.dp, end = 8.dp)
                                    .clip(RoundedCornerShape(4.dp)),
                            ) {
                                Row(
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                        .fillMaxWidth()
                                        .clickable {
                                            selectMultiple.value = true
                                            menuExposed.value = false
                                        },
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start)
                                ) {
                                    Icon(Icons.Rounded.CheckCircle, contentDescription = "menu")
                                    Text(
                                        text = "Select Games",
                                        color = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                }

                                ExposedDropdownMenuBox(
                                    expanded = sortsExposed.value,
                                    onExpandedChange = {},
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                        .fillMaxWidth()
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .clickable {
                                                sortsExposed.value = !sortsExposed.value
                                            }
                                            .menuAnchor(
                                                ExposedDropdownMenuAnchorType.PrimaryEditable,
                                                true
                                            ),
                                        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                                        verticalAlignment = Alignment.CenterVertically,
                                    ) {
                                        Icon(
                                            imageVector = Icons.AutoMirrored.Filled.Sort,
                                            contentDescription = "Sort",
                                        )
                                        Column {
                                            Text(
                                                text = "Sort by",
                                                color = MaterialTheme.colorScheme.onPrimaryContainer,
                                            )
                                            Text(
                                                text = uiState.value.sortBy.sortString,
                                                color = MaterialTheme.colorScheme.onSecondaryContainer,
                                            )
                                        }
                                        Spacer(modifier = Modifier.weight(1f))
                                        Icon(
                                            imageVector = if (!sortsExposed.value) Icons.AutoMirrored.Filled.KeyboardArrowRight else Icons.Filled.KeyboardArrowDown,
                                            contentDescription = null,
                                        )
                                    }
                                    ExposedDropdownMenu(
                                        expanded = sortsExposed.value,
                                        onDismissRequest = { },
                                    ) {
                                        SortType.entries.forEach { entry ->
                                            DropdownMenuItem(
                                                text = {
                                                    Row (
                                                        verticalAlignment = Alignment.CenterVertically,
                                                    ){
                                                        Text(
                                                            text = entry.sortString,
                                                            color = MaterialTheme.colorScheme.onPrimaryContainer
                                                        )
                                                        Spacer(modifier = Modifier.weight(1f))
                                                        Icon(
                                                            imageVector = Icons.Filled.Check,
                                                            contentDescription = null,
                                                            modifier = Modifier.graphicsLayer(
                                                                alpha = if (uiState.value.sortBy == entry) 1f else 0f
                                                            )
                                                        )
                                                    }
                                                },
                                                onClick = {
                                                    (homescreenModel::changeSort)(entry)
                                                    sortsExposed.value = false
                                                }
                                            )
                                        }
                                    }
                                }
                                ExposedDropdownMenuBox(
                                    expanded = filtersExposed.value,
                                    onExpandedChange = {},
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true)
                                            .fillMaxWidth()
                                            .clickable {
                                                filtersExposed.value = !filtersExposed.value
                                            },
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.FilterList,
                                            contentDescription = "filter",
                                        )
                                        Text(
                                            text = "Filters",
                                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        )
                                        Spacer(modifier = Modifier.weight(1f))
                                        Icon(
                                            imageVector = if (!filtersExposed.value) Icons.AutoMirrored.Filled.KeyboardArrowRight else Icons.Filled.KeyboardArrowDown,
                                            contentDescription = null,
                                        )
                                    }
                                    ExposedDropdownMenu(
                                        expanded = filtersExposed.value,
                                        onDismissRequest = { },
                                    ) {
                                        Filters.entries.forEach { entry ->
                                            if (entry != Filters.Completed) {
                                                DropdownMenuItem(
                                                    text =
                                                        {
                                                            Row{
                                                                Text(entry.name)
                                                                Spacer(modifier = Modifier.weight(1f))
                                                                Icon(
                                                                    imageVector = Icons.Filled.Check,
                                                                    contentDescription = null,
                                                                    modifier = Modifier.graphicsLayer(alpha = if (uiState.value.filters.contains(entry)) 1f else 0f)
                                                                )
                                                            }
                                                        },
                                                    onClick = {
                                                        homescreenModel.toggleFilter(entry)
                                                    }
                                                )
                                            }
                                        }
                                    }
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                        .fillMaxWidth()
                                        .clickable {
                                            homescreenModel.toggleReverse()
                                        },
                                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start)
                                ) {
                                    Icon(
                                        imageVector = Icons.Filled.SwapVert,
                                        contentDescription = null,
                                    )
                                    Text(
                                        text = "Order",
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    )
                                    Spacer(modifier = Modifier.weight(1f))
                                    Icon(
                                        imageVector = if (uiState.value.reverseList) Icons.Filled.ArrowUpward else Icons.Filled.ArrowDownward,
                                        contentDescription = null,
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .padding(vertical = 8.dp)
                                        .fillMaxWidth()
                                        .clickable {
                                            homescreenModel.toggleShowCompleted()
                                        },
                                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start)
                                ){
                                    Icon(
                                        imageVector = if (uiState.value.showCompleted) Icons.Outlined.Visibility else Icons.Outlined.VisibilityOff,
                                        contentDescription = null,
                                    )
                                    Text(
                                        text = if (uiState.value.showCompleted) "Hide Completed" else "Show Completed",
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    )
                                    Spacer(modifier = Modifier.size(24.dp))
                                }
                            }
                        }
                    }
                    //end of drop down menu
                }
                //GamesList
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    for (filter in uiState.value.filters) {
                        FilterChip(
                            selected = uiState.value.filters.contains(filter),
                            onClick = { homescreenModel.toggleFilter(filter) },
                            label = {Text(filter.name) },
                        )
                    }
                }
                LazyColumn(
                    Modifier.weight(1f),
                ) {
                    items(
                        items = uiState.value.games,
                        key = { game ->
                            game.id
                        }
                    ) { game ->
                        val toggle = remember(game.id) { { homescreenModel.toggleCompletedID(game.id) } }
                        val modifier = Modifier
                            .animateItem()
                            .padding(start = 4.dp, end = 4.dp, top = 8.dp, bottom = 8.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(
                                color = MaterialTheme.colorScheme.surfaceContainerLow
                            )
                            .fillMaxWidth()
                            .clickable {
                                navigator.push(DetailsScreen(game.id))
                            }
                        Row(modifier = modifier) {
                            AnimatedVisibility(visible = selectMultiple.value) {
                                IconButton(
                                    onClick = {
                                        if (selectedItems.value.contains(game.id)) {
                                            selectedItems.value = selectedItems.value.minus(game.id)
                                        } else {
                                            selectedItems.value = selectedItems.value.plus(game.id)
                                        }
                                    }
                                ) {
                                    Icon(
                                        imageVector = if (selectedItems.value.contains(game.id)) Icons.Filled.Circle else Icons.Outlined.Circle,
                                        contentDescription = null,
                                    )
                                }
                            }
                            AnimatedVisibility(visible = !selectMultiple.value) {
                                IconButton(
                                    onClick = toggle,
                                ) {
                                    Icon(
                                        imageVector = if (game.completed) Icons.Filled.Circle else Icons.Outlined.Circle,
                                        contentDescription = null,
                                        tint = colourMap[game.rating] ?: Color.White
                                    )
                                }
                            }
                            Spacer(Modifier.width(8.dp))
                            Text(
                                text = game.title,
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier
                                    .alpha(if (game.completed) 0.35f else 1f)
                                    .align(Alignment.CenterVertically)
                            )
                        }
                    }
                }
                //
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Spacer(modifier = Modifier.weight(.33f))
                    FloatingActionButton(
                        onClick = {
                            if (!selectMultiple.value) navigator.push(AddScreen())
                            else homescreenModel.deleteGames(selectedItems.value)
                            selectMultiple.value = false
                            menuExposed.value = false
                        }
                    ) {
                        Icon(
                            imageVector = if (!selectMultiple.value) Icons.Default.Add
                            else Icons.Outlined.Delete,
                            contentDescription = "Close"
                        )
                    }
                }
            }
        }
    }
}


