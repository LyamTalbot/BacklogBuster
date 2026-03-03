package com.lyamtalbot.backlogbuster2.backlogbuster2.ui.screenmodels

import androidx.compose.ui.text.input.TextFieldValue
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import cafe.adriel.voyager.core.model.ScreenModel
import cafe.adriel.voyager.core.model.screenModelScope
import com.lyamtalbot.backlogbuster2.backlogbuster2.database.Game
import com.lyamtalbot.backlogbuster2.backlogbuster2.database.GameDao
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.Filters
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.SortType
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.UiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.time.Clock

class HomescreenModel(private val gameDao: GameDao, private val dataStore: DataStore<Preferences>) : ScreenModel {

    private val _gameName = MutableStateFlow(TextFieldValue(""))

    private val _filters = MutableStateFlow(setOf<Filters>())

    private val _sort = MutableStateFlow(SortType.DateAdded)

    private val _reverseList = MutableStateFlow(false)

    private val _showCompleted = MutableStateFlow(false)

    private val filtersKey = stringSetPreferencesKey("filters")
    private val sortKey = stringPreferencesKey("sort")
    private val reverseListKey = booleanPreferencesKey("reverseList")
    private val showCompletedKey = booleanPreferencesKey("showCompleted")



    init {
        screenModelScope.launch(Dispatchers.IO){
            dataStore.data.collect { storeData ->
                _filters.update {
                    storeData.get(filtersKey).orEmpty().map{it -> Filters.valueOf(it)}.toSet()
                }
                _sort.update {
                    SortType.valueOf(storeData.get(sortKey)?: SortType.DateAdded.name)
                }
                _reverseList.update{
                    storeData[reverseListKey] ?: false
                }
                _showCompleted.update {
                    storeData[showCompletedKey] ?: false
                }
            }
        }
    }

    fun saveToDateStore(){
        screenModelScope.launch(Dispatchers.IO){
            dataStore.updateData {
                it.toMutablePreferences().apply{
                    set(filtersKey, _filters.value.map { it -> it.name }.toSet())
                    set(sortKey, _sort.value.name)
                    set(reverseListKey, _reverseList.value)
                    set(showCompletedKey, _showCompleted.value)
                }
            }
        }
    }

    val uiState: StateFlow<UiState> = combine(
        gameDao.getAllGamesAsFlow(),
        _filters,
        _sort,
        _reverseList,
        _showCompleted,

    ) { games, filters, sort, reverse, showCompleted ->
        saveToDateStore()
        UiState(
            games = if (!reverse) sortGames(games.filter { game -> isGameVisible(game, filters, showCompleted) }, sort) else sortGames(games.filter { game -> isGameVisible(game, filters, showCompleted) }, sort).reversed(),
            sortBy = sort,
            filters = filters,
            reverseList = reverse,
            showCompleted = showCompleted
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

    fun toggleShowCompleted() {
        _showCompleted.value = !_showCompleted.value
    }

    fun toggleReverse() {
        _reverseList.value = !_reverseList.value
    }
    fun toggleCompleted(game: Game,) {
        screenModelScope.launch {
            gameDao.update(game.copy(completed = !game.completed))
        }
    }

    fun toggleCompletedID(id: Int){
        screenModelScope.launch {
            val game = gameDao.getGameById(id) ?: return@launch
            val finishedAt = game.dateCompleted ?: Clock.System.now()
            gameDao.updateCompleted(id, !game.completed, finishedAt)
        }
    }

    fun deleteGames(gameIDs: Set<Int>) {
        screenModelScope.launch {
            gameDao.deleteGames(gameIDs)
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

    fun isGameVisible(game: Game, filters: Set<Filters>, showCompleted: Boolean): Boolean {
        if (filters.contains(Filters.Favourite) && !game.favourite) {
            return false

        }
        if (filters.contains(Filters.Playing) && !game.playingNow) {
            return false
        }
        if (!showCompleted && game.completed) {
            return false
        }
        return true
    }

    fun sortGames(games: List<Game>, sortType: SortType): List<Game>{
        return when (sortType) {
            SortType.Title -> games.sortedBy { it.title }
            SortType.DateCompleted -> games.sortedBy { it.dateCompleted }
            SortType.DateAdded -> games.sortedBy { it.dateCreated }
            SortType.Rating -> games.sortedByDescending { it.rating }
            SortType.TimeToFinish -> games.sortedByDescending { it.timeToBeat }
        }
    }

}