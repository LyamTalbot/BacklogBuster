//package com.lyamtalbot.backlogbuster2.backlogbuster2.ui
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.lazy.LazyColumn
//import androidx.compose.foundation.lazy.items
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material3.Button
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.MaterialTheme
//import androidx.compose.material3.OutlinedTextField
//import androidx.compose.material3.Scaffold
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.getValue
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.draw.clip
//import androidx.compose.ui.text.input.TextFieldValue
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import com.lyamtalbot.backlogbuster2.backlogbuster2.database.Game
//import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.components.GameRow
//import org.koin.compose.viewmodel.koinViewModel
//
//
//@Composable
//fun HomeScreenComposeNav(onGameClick: (Int) -> Unit, viewModel: GameListViewModel = koinViewModel()) {
//    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
//
//
//    HomeScreenComposeNav(
//        games = uiState.games,
//        gameName = uiState.gameName,
//        onCompletedPressed = viewModel::toggleCompleted,
//        onUpdateGameName = viewModel::updateMovieName,
//        onGameClick = onGameClick,
//        onAddGame = viewModel::insertGame,
////        onDeleteGames = viewModel::deleteGames()
//    )
//}
//
//
//@Composable
//fun HomeScreenComposeNav(
//    games: List<Game>,
//    gameName: TextFieldValue,
//    onUpdateGameName: (TextFieldValue) -> Unit,
//    onCompletedPressed: (Game) -> Unit,
//    onGameClick: (Int) -> Unit,
//    onAddGame: (String) -> Unit,
//    onDeleteGames: () -> Unit
//) {
//
//}