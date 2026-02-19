package ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.runtime.Composable
import com.example.compose.darkScheme
import com.example.compose.lightScheme

@Composable
fun Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colours = if (darkTheme) darkScheme else lightScheme
    MaterialTheme(
        colorScheme = colours,
        content = content
    )
}