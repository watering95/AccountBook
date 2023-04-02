package com.example.accountbook.componant

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.*

@Composable
fun MainTopBar(title: String = "", onButtonNavigationClicked: () -> Unit) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = { onButtonNavigationClicked() } ) {
                Icon(Icons.Filled.Menu, contentDescription = "Menu")
            }
        }
    )
}

@Composable
fun SettingTopBar(title: String = "", onButtonNavigationClicked: () -> Unit, onButtonAddClicked: () -> Unit = {}) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = { onButtonNavigationClicked() } ) {
                Icon(Icons.Filled.Menu, contentDescription = "Menu")
            }
        },
        actions = {
            IconButton(onClick = { onButtonAddClicked() }) {
                Icon(Icons.Filled.Add, "Add")
            }
        }
    )
}