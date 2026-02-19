package com.lyamtalbot.backlogbuster2.backlogbuster2.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.lyamtalbot.backlogbuster2.backlogbuster2.database.Game
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.getScreenModel
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.screenmodels.DetailsScreenModel
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime


data class DetailsScreen(val gameID: Int) : Screen {

    @Composable
    override fun Content() {
        val detailsScreenModel = getScreenModel<DetailsScreenModel>()
        val game = detailsScreenModel.getGame(gameID).collectAsState(initial = Game())
        val padWitdh = 15
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            contentColor = MaterialTheme.colorScheme.primaryContainer
        ) { scaffoldPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(space = 10.dp),
                modifier = Modifier
                    .padding(scaffoldPadding)
                    .padding(start = 40.dp, end = 40.dp, bottom = 10.dp, top = 10.dp)
                    .fillMaxWidth(),
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                Text(
                    text = game.value.title.padEnd(padWitdh),
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    fontSize = 30.sp,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Genre: ${game.value.getGenreString()}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Platform: ${game.value.getPlatformString()}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Rating: ${game.value.getRatingString()}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.fillMaxWidth()

                )
                Text(
                    text = "Date added: ${game.value.getCreatedTimeString()}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.fillMaxWidth()

                )
                Text(
                    text = "Date finished: ${game.value.getFinishTimeString()}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                    modifier = Modifier.fillMaxWidth()
                )
                Button(onClick = {
                    (detailsScreenModel::deleteGame)(game.value.id)
                    navigator.pop()
                }){
                    Text("Delete game")
                }
            }
        }
    }
}
