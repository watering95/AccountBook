package com.example.accountbook.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable

private val DarkColorPalette = darkColors(
    primary = dark_primary,
    primaryVariant = dark_onPrimary,
    secondary = dark_primaryContainer
)

private val LightColorPalette = lightColors(
    primary = light_primary,
    primaryVariant = light_onPrimary,
    secondary = light_primaryContainer
)

private val CardListDarkColorPalette = darkColors(
    primary = dark_primary,
    primaryVariant = dark_onPrimary,
    secondary = dark_primaryContainer,
    surface = Cyan200
)

private val CardListLightColorPalette = lightColors(
    primary = Cyan50,
    primaryVariant = Cyan200,
    secondary = Cyan500,
    background = Green
)

private val CardDarkColorPalette = darkColors(
    primary = dark_primary,
    primaryVariant = dark_onPrimary,
    secondary = dark_primaryContainer
)

private val CardLightColorPalette = lightColors(
    primary = Yellow700,
    primaryVariant = Yellow500,
    secondary = Yellow500,
    background = Yellow50
)

@Composable
fun AccountBookTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
    )
}

@Composable
fun CardListTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        CardListDarkColorPalette
    } else {
        CardListLightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}

@Composable
fun CardTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        CardDarkColorPalette
    } else {
        CardLightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}