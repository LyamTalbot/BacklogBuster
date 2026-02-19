package com.lyamtalbot.backlogbuster2.backlogbuster2.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyamtalbot.backlogbuster2.backlogbuster2.database.Game
import com.lyamtalbot.backlogbuster2.backlogbuster2.database.GameDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn

class DetailsViewModel(private val gameDao: GameDao, private val savedStateHandle: SavedStateHandle) : ViewModel() {
    val gameId: Int = savedStateHandle["gameId"]!!
    val game: StateFlow<Game?> = gameDao.gameByIdFlow(gameId)
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            initialValue = null
        )
}