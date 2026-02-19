package com.lyamtalbot.backlogbuster2.backlogbuster2.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.lyamtalbot.backlogbuster2.backlogbuster2.database.Game
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.getScreenModel
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.screenmodels.AddScreenModel

class AddScreen() : Screen {

    @Composable
    override fun Content() {

        val gameName = remember { mutableStateOf(TextFieldValue("")) }
        val platform = remember { mutableStateOf(TextFieldValue("")) }
        val genre = remember { mutableStateOf(TextFieldValue("")) }
        val rating = remember { mutableStateOf(TextFieldValue("")) }
        val currentlyPlaying = remember { mutableStateOf(false) }
        val buttonActive = remember { mutableStateOf(false) }

        val addScreenModel = getScreenModel<AddScreenModel>()

        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            contentColor = MaterialTheme.colorScheme.primaryContainer,
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier
                    .safeContentPadding()
                    .padding(10.dp)
                    .fillMaxWidth(),
            ) {
                Text(
                    text = "Another one goes on the pile, eh?",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer,
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = gameName.value,
                    onValueChange = {
                        gameName.value = it
                    },
                    label = { Text("Enter title") },
                    supportingText = {
                        if (gameName.value.text.isBlank()) {
                            Text(
                                text = "Please enter a title",
                                color = Color.Red
                            )
                            buttonActive.value = false
                        } else {
                            buttonActive.value = true
                        }
                    }
                )
                //@TODO: Change this to a dropdown with the platform list
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = platform.value,
                    onValueChange = { platform.value = it },
                    label = { Text("Enter platform") }
                )
                //@TODO: Probably another dropdown list
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = genre.value,
                    onValueChange = { genre.value = it },
                    label = { Text("Enter genre") }

                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = rating.value,
                    onValueChange = { rating.value = it },
                    supportingText = {
                        if (!rating.value.text.isBlank()) {
                            val ratingValue = rating.value.text.toIntOrNull()
                            if (ratingValue == null || ratingValue !in 1..6) {
                                Text(
                                    text = "Please enter a number between 1 and 6"
                                )
                            }
                        }
                    },
                    label = { Text("Enter rating") }
                )
                //This row contains the checkbox for currently playing
                //I kinda hate it and can't make it look the way I want
                //Maybe a toggleable button instead
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically,
                )
                {
                    Checkbox(
                        checked = currentlyPlaying.value,
                        onCheckedChange = { currentlyPlaying.value = !currentlyPlaying.value },
                    )
                    Text(
                        text = "Playing now",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
                Button(
                    enabled = buttonActive.value,
                    onClick = {
                        val game =
                            Game(
                                title = gameName.value.text,
                                platform = platform.value.text,
                                genre = genre.value.text,
                                rating = rating.value.text.toIntOrNull()?: -1,
                                playingNow = currentlyPlaying.value
                                )
                        //Add the game to the list
                        addScreenModel.addGame(game)
                        //Pop this screen off the stack and navigate back to the last screen
                        navigator.pop()
                    },
                ) {
                    Text("Add Game")
                }
            }
        }
    }
}