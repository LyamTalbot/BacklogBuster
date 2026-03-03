package com.lyamtalbot.backlogbuster2.backlogbuster2.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import com.lyamtalbot.backlogbuster2.backlogbuster2.database.Game
import com.lyamtalbot.backlogbuster2.backlogbuster2.database.ratingsMap
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.components.NavbarBackButton
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.getScreenModel
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.screenmodels.AddScreenModel

@OptIn(ExperimentalMaterial3Api::class)
data class AddScreen(val game: Game = Game()) : Screen {

    @Composable
    override fun Content() {

        val gameName = remember { mutableStateOf(TextFieldValue(game.title)) }
        val platform = remember { mutableStateOf(TextFieldValue(game.platform)) }
        val genre = remember { mutableStateOf(TextFieldValue(game.genre)) }
        val currentlyPlaying = remember { mutableStateOf(game.playingNow) }
        val rating = remember {mutableStateOf(game.rating)}
        val ratingDropDownText = remember {mutableStateOf(TextFieldValue(game.getRatingTextField()))}
        val finished = remember { mutableStateOf(game.completed) }
        val favourite = remember { mutableStateOf(game.favourite) }
        val timeToBeat = remember {mutableStateOf(TextFieldValue(game.getTimeToBeatString()))}

        val buttonActive = remember { mutableStateOf(false) }
        val ratingDropdown = remember {mutableStateOf(false)}

        val addScreenModel = getScreenModel<AddScreenModel>()

        val navigator = LocalNavigator.currentOrThrow

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            contentColor = MaterialTheme.colorScheme.primaryContainer,
        ) { scaffoldPadding ->
            val localFocusManager = LocalFocusManager.current
            val keyboardController = LocalSoftwareKeyboardController.current

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(15.dp),
                modifier = Modifier
                    .safeContentPadding()
                    .fillMaxWidth()
                    .pointerInput(Unit){
                        detectTapGestures(
                            onTap = {
                                keyboardController?.hide()
                                localFocusManager.clearFocus()
                            }
                        )
                    },
            ) {
                NavbarBackButton(navigator)
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
                    value = timeToBeat.value,
                    onValueChange = { timeToBeat.value = it },
                    label = { Text("Time to beat")}
                )
                ExposedDropdownMenuBox(
                    expanded = ratingDropdown.value,
                    onExpandedChange = { ratingDropdown.value = it},
                    modifier = Modifier
                        .fillMaxWidth()
                ){
                    TextField(
                        value = ratingDropDownText.value,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(expanded = ratingDropdown.value)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .menuAnchor(MenuAnchorType.PrimaryNotEditable, true),
                    )
                    ExposedDropdownMenu(
                        expanded = ratingDropdown.value,
                        onDismissRequest = {ratingDropdown.value = false}
                    ){
                        ratingsMap.map { (intRating, stringRating) ->
                            DropdownMenuItem(
                                text = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(15.dp, Alignment.Start),
                                    ){
                                        Text("$stringRating: $intRating")
                                    }
                                },
                                onClick = {
                                    rating.value = intRating
                                    ratingDropDownText.value = TextFieldValue("$stringRating: $intRating")
                                    ratingDropdown.value = false
                                }
                            )
                        }
                    }
                }
                //This row contains the checkbox for currently playing
                //I kinda hate it and can't make it look the way I want
                //Maybe a toggleable button instead
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                )
                {
                    Checkbox(
                        checked = currentlyPlaying.value,
                        onCheckedChange = { currentlyPlaying.value =  it},
                    )
                    Text(
                        text = "Playing now",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Checkbox(
                        checked = finished.value,
                        onCheckedChange = { finished.value = it},
                    )
                    Text(
                        text = "Finished",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp, Alignment.Start),
                    verticalAlignment = Alignment.CenterVertically,
                ){
                    Checkbox(
                        checked = favourite.value,
                        onCheckedChange = { favourite.value = it },
                    )
                    Text(
                        text = "Favourite",
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                    )
                }
                Button(
                    enabled = buttonActive.value,
                    onClick = {
                        val game =
                            Game(
                                id = game.id,
                                title = gameName.value.text.trim(),
                                platform = platform.value.text,
                                genre = genre.value.text,
                                rating = rating.value,
                                //Don't love this but i'll try to convert the string to an int
                                //if it can't be converted, say because the string is '--'
                                //it will return null and I can convert that to -1.
                                //I'll Probably will refactor the ratings away from raw ints to an enum class
                                //This will presumably require some kind of type converter from
                                //the room db to the enum class inside the game object
                                //so it'll take me an hour
                                timeToBeat = timeToBeat.value.text.toIntOrNull()?: -1,
                                dateCreated = game.dateCreated,
                                playingNow = currentlyPlaying.value,
                                completed = finished.value,
                                favourite = favourite.value,
                                dateCompleted = game.dateCompleted,
                                )
                        //Add the game to the list
                        navigator.pop()
                        addScreenModel.addGame(game)
                        //Pop this screen off the stack and navigate back to the last screen
                    },
                ) {
                    Text(if(game.id == 0)"Add Game" else "Save Game")
                }
            }
        }
    }
}