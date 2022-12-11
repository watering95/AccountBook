package com.example.accountbook.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign

@Composable
fun AccountScreen() {
    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
    ) {
        Text(
            text = "AccountScreen",
            style = MaterialTheme.typography.h6,
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}