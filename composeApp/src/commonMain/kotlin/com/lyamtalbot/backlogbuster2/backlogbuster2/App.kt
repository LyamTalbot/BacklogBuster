package com.lyamtalbot.backlogbuster2.backlogbuster2

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import cafe.adriel.voyager.navigator.Navigator
import cafe.adriel.voyager.navigator.NavigatorDisposeBehavior
import com.example.compose.darkScheme
import com.example.compose.lightScheme
import com.lyamtalbot.backlogbuster2.backlogbuster2.database.GameDatabase
import com.lyamtalbot.backlogbuster2.backlogbuster2.database.getGameDatabase
import com.lyamtalbot.backlogbuster2.backlogbuster2.ui.screens.HomeScreen

import kotlinx.serialization.Serializable


@Composable
fun App() {
    val colours = if (isSystemInDarkTheme()) darkScheme else lightScheme
    MaterialTheme(
        colorScheme = colours
    ) {
        Navigator(screen = HomeScreen())
    }
}