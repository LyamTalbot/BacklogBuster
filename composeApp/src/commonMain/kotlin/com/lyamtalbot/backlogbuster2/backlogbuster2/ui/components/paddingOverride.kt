package com.lyamtalbot.backlogbuster2.backlogbuster2.ui.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun paddingOverride() : Modifier {
    val insets = WindowInsets.safeContent.asPaddingValues()
    val minPad = 16.dp


    val start = maxOf(insets.calculateStartPadding(LocalLayoutDirection.current), minPad)
    val end = maxOf(insets.calculateEndPadding(LocalLayoutDirection.current), minPad)
    val top = maxOf(insets.calculateTopPadding(), minPad)
    val bottom = maxOf(insets.calculateBottomPadding(), minPad)

    return Modifier.padding(start = start, top = top, end = end, bottom = bottom)
}
