package com.example.designsystem.component

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.LocalAbsoluteTonalElevation
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.designsystem.theme.CosmeaTheme

@Composable
fun Background(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit,
) {
    val color = MaterialTheme.colorScheme.surface
    Surface(
        color = color,
        modifier = modifier.fillMaxSize(),
    ) {
        CompositionLocalProvider(LocalAbsoluteTonalElevation provides 0.dp) {
            content()
        }
    }
}

@Preview
@Composable
fun BackgroundPreviewLight() {
    CosmeaTheme {
        Background {
        }
    }
}

@Preview
@Composable
fun BackgroundPreviewDark() {
    CosmeaTheme(darkTheme = true, dynamicColor = false) {
        Background {
        }
    }
}