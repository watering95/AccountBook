package com.example.accountbook.componant

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun TopBar(title: String = "", buttonIcon: ImageVector, onButtonClicked: () -> Unit) {

    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = { onButtonClicked() } ) {
                Icon(buttonIcon, contentDescription = "Menu")
            }
        }
    )
}