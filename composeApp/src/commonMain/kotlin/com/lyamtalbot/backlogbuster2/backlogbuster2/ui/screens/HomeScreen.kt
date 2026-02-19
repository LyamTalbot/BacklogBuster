package com.lyamtalbot.backlogbuster2.backlogbuster2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.Filters
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.SortType
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.components.GameRow
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.getScreenModel
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.screenmodels.HomescreenModel


class HomeScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {

        val homescreenModel = getScreenModel<HomescreenModel>()

        val uiState = homescreenModel.uiState.collectAsState()

        val navigator = LocalNavigator.currentOrThrow

        val listState = rememberLazyListState()
        val previousListItems = remember {mutableStateOf(uiState.value.games)}

        val menuExposed = remember { mutableStateOf(false) }
        val filtersExposed = remember { mutableStateOf(false) }
        val sortsExposed = remember { mutableStateOf(false) }
        Scaffold(modifier = Modifier.fillMaxSize()) { scaffoldPadding ->
            Column(
                verticalArrangement = Arrangement.spacedBy(space = 12.dp),
                modifier = Modifier
                    .padding(scaffoldPadding)
                    .padding(16.dp)
            ) {
                //Top Bar
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.CenterHorizontally),
                ) {
                    Text(
                        text = "Backlog Buster",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        fontSize = 30.sp
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    IconButton(onClick = {
                        menuExposed.value = true
                    }) {
                        Icon(Icons.Rounded.Menu, contentDescription = "menu")
                    }
                    Box(
                        contentAlignment = Alignment.TopEnd
                    ) {
                        //Dropdown menu items
                        //1. Select Games
                        //2. Sort by -> Opens another dropdown -> [Title, Rating, Date Added, Date Completed, Time to Beat] | [Asc/Dsc]
                        //3. Show/Hide Completed
                        DropdownMenu(
                            expanded = menuExposed.value,
                            onDismissRequest = { menuExposed.value = !menuExposed.value },
                        ) {
                            ExposedDropdownMenuBox(
                                expanded = sortsExposed.value,
                                onExpandedChange = {},
                            ) {
                                Row(
                                    modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable, true)
                                ) {
                                    Column {
                                        Text(
                                            text = "Sort by",
                                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                                        )
                                        Text(
                                            text = "${uiState.value.sortBy}",
                                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                                        )
                                    }
                                    IconButton(onClick = {
                                        sortsExposed.value = !sortsExposed.value
                                    }) {
                                        Icon(
                                            imageVector = if (!sortsExposed.value) Icons.AutoMirrored.Filled.KeyboardArrowRight else Icons.Filled.KeyboardArrowDown,
                                            contentDescription = null,
                                        )
                                    }
                                }
                                ExposedDropdownMenu(
                                    expanded = sortsExposed.value,
                                    onDismissRequest = { },
                                ) {
                                    SortType.entries.forEach { entry ->
                                        DropdownMenuItem(
                                            text = { Text(entry.name) },
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
                            ) {
                                Row(
                                    modifier = Modifier
                                        .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = "Filters",
                                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                                    )
                                    IconButton(
                                        onClick = { filtersExposed.value = !filtersExposed.value },
                                    ) {
                                        Icon(
                                            imageVector = if (!filtersExposed.value) Icons.AutoMirrored.Filled.KeyboardArrowRight else Icons.Filled.KeyboardArrowDown,
                                            contentDescription = null,
                                        )
                                    }
                                }
                                ExposedDropdownMenu(
                                    expanded = filtersExposed.value,
                                    onDismissRequest = { },
                                ) {
                                    Filters.entries.forEach { entry ->
                                        if (entry != Filters.Completed) {
                                            DropdownMenuItem(
                                                text = { Text(entry.name) },
                                                onClick = {
                                                    (homescreenModel::toggleFilter)(entry)
                                                }
                                            )
                                        }
                                    }
                                }
                            }
                            DropdownMenuItem(
                                onClick = homescreenModel::toggleReverse,
                                text = { Text("Reverse Order") },
                            )
                        }
                    }

                }
                //GamesList
                LazyColumn(
                    Modifier.weight(1f),
                    state = listState,
                ) {
                    items(
                        items = uiState.value.games,
                        key = { game ->
                            game.id
                        }
                    ) { game ->
                        val modifier = Modifier
                            .padding(start = 4.dp, end = 4.dp, top = 8.dp, bottom = 8.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(MaterialTheme.colorScheme.surfaceContainerLow)
                            .fillMaxWidth()
                            .clickable {
                                navigator.push(DetailsScreen(game.id))
                            }
                        val animatedModifier = Modifier
                            .animateItem()
                        val visible = (homescreenModel::isGameVisible)(game, uiState.value.filters)
                        GameRow(game, { (homescreenModel::toggleCompleted)(game) }, modifier, animatedModifier, visible)
                    }
                }
                //
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                ) {
                    Spacer(modifier = Modifier.weight(.33f))
                    Button(
                        onClick = homescreenModel::deleteGames,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.tertiary,
                        ),
                    ) {
                        Text(text = "Delete games")
                    }
                    FloatingActionButton(
                        onClick = {
                            navigator.push(AddScreen())
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Add, contentDescription = "Close"
                        )
                    }
                }
            }
        }
    }
}


