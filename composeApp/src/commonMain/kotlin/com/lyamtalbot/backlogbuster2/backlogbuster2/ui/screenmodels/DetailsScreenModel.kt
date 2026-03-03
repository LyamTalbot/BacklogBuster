package com.lyamtalbot.backlogbuster2.backlogbuster2.ui.screenmodels

import androidx.compose.runtime.collectAsState
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.lyamtalbot.backlogbuster2.backlogbuster2.database.Game
import com.lyamtalbot.backlogbuster2.backlogbuster2.database.GameDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch

class DetailsScreenModel(private val gameDao: GameDao) : ScreenModel {

    fun getGame(gameID: Int): Flow<Game?> {
        return gameDao.gameByIdFlow(gameID)
    }

    fun deleteGame(gameID: Int): Unit {
        screenModelScope.launch {
            gameDao.deleteById(gameID)
        }
    }
}