package com.lyamtalbot.backlogbuster2.backlogbuster2.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material.icons.outlined.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lyamtalbot.backlogbuster2.backlogbuster2.database.Game

val colourMap: Map<Int, Color> = mapOf(
    1 to Color(255, 0, 0, 229),
    2 to Color(225, 90, 0, 229),
    3 to Color(255, 170, 0, 229),
    4 to Color(200, 255, 0, 229),
    5 to Color(150, 255, 0, 229),
    6 to Color(0, 255, 0, 229),
)

@Composable
fun GameRow(
    game: Game,
    completionToggle: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val alpha = if (game.completed) 0.35f else 1f
    Row(
        modifier = modifier,
    ) {
        Row {
            IconButton(
                onClick =  completionToggle,
                modifier = Modifier
                    .align(Alignment.CenterVertically)
            )
            {
                Icon(
                    imageVector = if (game.completed) Icons.Filled.Circle else Icons.Outlined.Circle,
                    contentDescription = null,
                    tint = colourMap[game.rating] ?: Color.White
                )
            }
            Spacer(Modifier.width(8.dp))
            Text(
                text = game.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .alpha(alpha)
                    .align(Alignment.CenterVertically)
            )
        }
    }
}