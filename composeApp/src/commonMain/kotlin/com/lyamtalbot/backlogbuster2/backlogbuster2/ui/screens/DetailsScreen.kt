package com.lyamtalbot.backlogbuster2.backlogbuster2.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import backlogbuster2.composeapp.generated.resources.Res
import backlogbuster2.composeapp.generated.resources.delete_game
import backlogbuster2.composeapp.generated.resources.edit_game
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.lyamtalbot.backlogbuster2.backlogbuster2.database.Game
import com.lyamtalbot.backlogbuster2.backlogbuster2.database.ratingsMap
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.components.GameDetails
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.components.NavbarBackButton
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.components.paddingOverride
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.getScreenModel
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.screenmodels.AddScreenModel
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.screenmodels.DetailsScreenModel
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.char
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource


data class DetailsScreen(val gameID: Int) : Screen {

    @Composable
    override fun Content() {
        val detailsScreenModel = getScreenModel<DetailsScreenModel>()
        val game = detailsScreenModel.getGame(gameID).filterNotNull().collectAsState(initial = Game())
        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            contentColor = MaterialTheme.colorScheme.primaryContainer
        ) { scaffoldPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(space = 10.dp),
                modifier = Modifier
                    .padding(scaffoldPadding)
                    .safeContentPadding()
                    .fillMaxWidth(),
            ) {
                NavbarBackButton(navigator)
                Spacer(modifier = Modifier.height(32.dp))
                GameDetails(game.value)
                Spacer(Modifier.height(32.dp))
                Button(
                    onClick = {
                        navigator.push(AddScreen(game.value))
                    }
                ){
                    Text(stringResource(Res.string.edit_game))
                }
                Button(onClick = {
                    detailsScreenModel.deleteGame(game.value.id)
                    navigator.pop()
                }){
                    Text(stringResource(Res.string.delete_game))
                }
            }
        }
    }
}
