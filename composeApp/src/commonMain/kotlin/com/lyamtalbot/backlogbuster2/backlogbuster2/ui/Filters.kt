package com.lyamtalbot.backlogbuster2.backlogbuster2.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.VideogameAsset
import androidx.compose.material.icons.filled.VideogameAssetOff
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.ui.graphics.vector.ImageVector
import backlogbuster2.composeapp.generated.resources.Res
import backlogbuster2.composeapp.generated.resources.completed
import backlogbuster2.composeapp.generated.resources.favourite_checkbox
import backlogbuster2.composeapp.generated.resources.play_checkbox
import org.jetbrains.compose.resources.StringResource


enum class Filters(val filterString: StringResource) {
    Favourite(Res.string.favourite_checkbox),
    Completed(Res.string.completed),
    Playing(Res.string.play_checkbox)
}