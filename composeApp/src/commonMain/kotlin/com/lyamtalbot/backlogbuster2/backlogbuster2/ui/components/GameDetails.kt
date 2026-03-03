package com.lyamtalbot.backlogbuster2.backlogbuster2.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.lyamtalbot.backlogbuster2.backlogbuster2.database.Game
import com.lyamtalbot.backlogbuster2.backlogbuster2.database.ratingsMap

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
        text = "Genre: ${game.getGenreString()}",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxWidth()
    )
    Text(
        text = "Platform: ${game.getPlatformString()}",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxWidth()
    )
    Text(
        text = "Rating: ${ratingsMap[game.rating]}",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxWidth()

    )
    Text(
        text = "Time to beat: ${game.getTimeToBeatString()}",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxWidth()
    )
    Text(
        text = "Date added: ${game.getCreatedTimeString()}",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxWidth()

    )
    Text(
        text = "Date finished: ${game.getFinishTimeString()}",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.onPrimaryContainer,
        modifier = Modifier.fillMaxWidth()
    )
}