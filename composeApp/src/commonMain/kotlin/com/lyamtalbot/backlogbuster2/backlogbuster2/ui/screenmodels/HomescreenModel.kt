package com.lyamtalbot.backlogbuster2.backlogbuster2.ui.screenmodels

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.StateScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.lyamtalbot.backlogbuster2.backlogbuster2.database.Game
import com.lyamtalbot.backlogbuster2.backlogbuster2.database.GameDao
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.Filters
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.SortType
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.UiState
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel

class HomescreenModel(private val gameDao: GameDao) : ScreenModel {

    private val _gameName = MutableStateFlow(TextFieldValue(""))

    private val _filters = MutableStateFlow(setOf<Filters>())

    private val _sort = MutableStateFlow(SortType.DateAdded)

    private val _reverseList = MutableStateFlow(false)

    //@TODO: Define the colours
    //Or do I want to just do this as an array?
    //Very bad
    //Bad
    //Below average
    //Above average
    //Good
    //Great
    private val _colourArray = arrayOf(
        Color(255, 0,0,229),
        Color(255,90,0,229),
        Color(255,170,0,229),
        Color(255,230,0,229),
        Color(180,255,0,229),
        Color(0,255,0,255,))

    val uiState: StateFlow<UiState> = combine(
        gameDao.getAllGamesAsFlow(),
        _filters,
        _sort,
        _reverseList

    ) { games, filters, sort, reverse ->
        UiState(
            games = sortGames(games.filter { game -> isGameVisible(game, filters) }, sort),
            sortBy = sort,
            filters = filters,
            reverseList = reverse
        )
    }.stateIn(
        scope = screenModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UiState()
    )


    fun toggleFilter(filter: Filters) {
        if(_filters.value.contains(filter)) {
            _filters.value = _filters.value.minus(filter)
        } else {
            _filters.value = _filters.value.plus(filter)
        }
    }

    fun changeSort(sortType: SortType) {
        _sort.value = sortType
    }

    fun toggleReverse() {
        _reverseList.value = !_reverseList.value
    }
    fun toggleCompleted(game: Game) {
        screenModelScope.launch {
            gameDao.update(game.copy(completed = !game.completed))
        }
    }

    fun deleteGames() {
        screenModelScope.launch {
            gameDao.deleteGames()
        }
    }

    fun updateGameName(newText: TextFieldValue) {
        screenModelScope.launch {
            _gameName.value = newText
        }
    }

    fun insertGame(gameName: String) {
        screenModelScope.launch {
            gameDao.insert(Game(title = gameName))
        }
    }

    fun isGameVisible(game: Game, filters: Set<Filters>): Boolean {
        var visible = true
        if (filters.contains(Filters.Favourite) && !game.favourite) {
            visible = false
        }
        if (filters.contains(Filters.Playing) && !game.playingNow) {
            visible = false
        }
        if (filters.contains(Filters.Completed) && game.completed) {
            visible = false
        }
        return visible
    }

    fun sortGames(games: List<Game>, sortType: SortType): List<Game>{
        return when (sortType) {
            SortType.Title -> games.sortedBy { it.title }
            SortType.DateCompleted -> games.sortedByDescending { it.dateCompleted }
            SortType.DateAdded -> games.sortedByDescending { it.dateCreated }
            SortType.Rating -> games.sortedByDescending { it.rating }
            SortType.TimeToFinish -> games.sortedByDescending { it.timeToBeat }
        }
    }

}