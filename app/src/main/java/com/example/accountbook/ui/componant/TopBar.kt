@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.accountbook.ui.componant

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Input
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Money
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.*

@Composable
fun TopBar(title: String = "") {
    CenterAlignedTopAppBar(title = { Text(title) })
}

@Composable
fun TopBar(title: String = "", onButtonNavigationClicked: () -> Unit, onButtonAddClicked: () -> Unit = {}) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = { onButtonNavigationClicked() } ) {
                Icon(Icons.Filled.ArrowBackIosNew, contentDescription = "Back")
            }
        },
        actions = {
            IconButton(onClick = { onButtonAddClicked() }) {
                Icon(Icons.Filled.Add, "Add")
            }
        }
    )
}

@Composable
fun TopBarWithDelete(title: String = "", onButtonNavigationClicked: () -> Unit, onButtonDeleteClicked: () -> Unit = {}) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = { onButtonNavigationClicked() } ) {
                Icon(Icons.Filled.Menu, contentDescription = "Menu")
            }
        },
        actions = {
            IconButton(onClick = { onButtonDeleteClicked() }) {
                Icon(Icons.Filled.Delete, "Delete")
            }
        }
    )
}

@Composable
fun TopBarForAccountScreen(title: String = "", onButtonNavigationClicked: () -> Unit, onButtonInputClicked: () -> Unit = {}, onButtonMoneyClicked: () -> Unit = {}) {
    TopAppBar(
        title = { Text(text = title) },
        navigationIcon = {
            IconButton(onClick = { onButtonNavigationClicked() } ) {
                Icon(Icons.Filled.Menu, contentDescription = "Menu")
            }
        },
        actions = {
            IconButton(onClick = { onButtonInputClicked() }) {
                Icon(Icons.AutoMirrored.Filled.Input, "Delete")
            }
            IconButton(onClick = { onButtonMoneyClicked() }) {
                Icon(Icons.Filled.Money, "Delete")
            }
        }
    )
}