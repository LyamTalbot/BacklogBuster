package com.lyamtalbot.backlogbuster2.backlogbuster2.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import backlogbuster2.composeapp.generated.resources.Res
import backlogbuster2.composeapp.generated.resources.blank_rating
import backlogbuster2.composeapp.generated.resources.date_added
import backlogbuster2.composeapp.generated.resources.date_finished
import backlogbuster2.composeapp.generated.resources.genre
import backlogbuster2.composeapp.generated.resources.platform
import backlogbuster2.composeapp.generated.resources.rating
import backlogbuster2.composeapp.generated.resources.ttb
import com.lyamtalbot.backlogbuster2.backlogbuster2.database.Game
import com.lyamtalbot.backlogbuster2.backlogbuster2.database.ratingsMap
import org.jetbrains.compose.resources.stringResource

@Composable
fun GameDetails(game: Game) {
    Text(
        text = game.title,
        style = MaterialTheme.typography.titleLarge,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        fontSize = 30.sp,
        modifier = Modifier.fillMaxWidth()
    )
    Text(
        text = "${stringResource(Res.string.genre)}: ${game.getGenreString()}",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxWidth()
    )
    Text(
        text = "${stringResource(Res.string.platform)}: ${game.getPlatformString()}",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxWidth()
    )
    Text(
        text = "${stringResource(Res.string.rating)}: ${stringResource(ratingsMap.getOrElse(game.rating,
            { Res.string.blank_rating }))}",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxWidth()

    )
    Text(
        text = "${stringResource(Res.string.ttb)}: ${game.getTimeToBeatString()}",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxWidth()
    )
    Text(
        text = "${stringResource(Res.string.date_added)}: ${game.getCreatedTimeString()}",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxWidth()

    )
    Text(
        text = "${stringResource(Res.string.date_finished)}: ${game.getFinishTimeString()}",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxWidth()
    )
}