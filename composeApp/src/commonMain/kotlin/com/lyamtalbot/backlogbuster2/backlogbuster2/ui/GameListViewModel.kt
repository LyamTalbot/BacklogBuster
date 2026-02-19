package com.lyamtalbot.backlogbuster2.backlogbuster2.ui

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lyamtalbot.backlogbuster2.backlogbuster2.database.Game
import com.lyamtalbot.backlogbuster2.backlogbuster2.database.GameDao
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class GameListViewModel(private val gameDao: GameDao) : ViewModel() {

    private val _gameName = MutableStateFlow(TextFieldValue(""))
    private val _filters = MutableStateFlow(setOf<Filters>())
    private val _sort = MutableStateFlow(SortType.DateAdded)

    val uiState: StateFlow<UiState> = combine(
        gameDao.getAllGamesAsFlow(),
        _filters,
        _sort,
        _gameName
    ) { games, filters, sort, gameName ->
        UiState(
            games = games.filter{game -> isGameVisible(game, filters)},
            sortBy = sort,
            gameName = gameName
            )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UiState()
    )

    fun insertGame(gameName: String){
        viewModelScope.launch {
            gameDao.insert(Game(title = gameName))
        }
    }

    fun updateMovieName(newText: TextFieldValue){
        _gameName.value = newText
    }

    fun deleteGames(){
        viewModelScope.launch {
            gameDao.deleteGames()
        }
    }

    fun deleteGame(game: Game){
        viewModelScope.launch {
            gameDao.delete(game)
        }
    }

    fun toggleFavourite(game: Game){
        viewModelScope.launch {
            gameDao.update(game.copy(favourite = !game.favourite))
        }
    }

    fun toggleCompleted(game: Game){
        viewModelScope.launch {
            gameDao.update(game.copy(completed = !game.completed))
        }
    }

    fun togglePlaying(game: Game){
        viewModelScope.launch {
            gameDao.update(game.copy(playingNow = !game.playingNow))
        }
    }

    fun isGameVisible(game: Game, filters: Set<Filters>): Boolean{
        var visible = true
        if (filters.contains(Filters.Favourite) && !game.favourite){
            visible = false
        }
        if (filters.contains(Filters.Playing) && !game.playingNow){
            visible = false
        }
        if (!filters.contains(Filters.Completed) && game.completed){
            visible = false
        }
        return visible
    }

}