package com.lyamtalbot.backlogbuster2.backlogbuster2.ui.screenmodels

import androidx.compose.ui.text.input.TextFieldValue

data class AddUIState(
    val gameName: TextFieldValue = TextFieldValue(""),
    val gameGenre: TextFieldValue = TextFieldValue(""),
    val gamePlatform: TextFieldValue = TextFieldValue(""),
    val currentlyPlaying: Boolean = false,
    val buttonActive: Boolean = false,
)
