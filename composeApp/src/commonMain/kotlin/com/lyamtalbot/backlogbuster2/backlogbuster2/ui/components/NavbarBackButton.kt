package com.lyamtalbot.backlogbuster2.backlogbuster2.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import backlogbuster2.composeapp.generated.resources.Res
import backlogbuster2.composeapp.generated.resources.back
import cafe.adriel.voyager.navigator.Navigator
import org.jetbrains.compose.resources.stringResource

@Composable
fun NavbarBackButton(navigator: Navigator) {
    Row {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.clickable { navigator.pop() }
        ) {
            Icon(imageVector = Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = null)
            Text(text = stringResource(Res.string.back), color = MaterialTheme.colorScheme.onPrimaryContainer)
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}