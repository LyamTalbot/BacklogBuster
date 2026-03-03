package com.lyamtalbot.backlogbuster2.backlogbuster2.ui

import androidx.compose.ui.text.input.TextFieldValue
import com.lyamtalbot.backlogbuster2.backlogbuster2.database.Game

data class UiState (
    val gameName: TextFieldValue = TextFieldValue(""),
    val games: List<Game> = emptyList(),
    val sortBy: SortType = SortType.DateAdded,
    val filters: Set<Filters> = emptySet(),
    val reverseList: Boolean = false,
    val showCompleted: Boolean = false
)
