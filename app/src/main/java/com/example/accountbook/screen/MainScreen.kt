package com.example.accountbook.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.accountbook.componant.RootTopBar
import com.example.accountbook.viewmodel.MainScreenViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.accountbook.componant.ScreenValue
import kotlinx.coroutines.*

@Composable
fun MainScreen(
    screenValue: ScreenValue,
    viewModel: MainScreenViewModel = viewModel()) {
    val navController = screenValue.navController
    val scaffoldState = screenValue.scaffoldState
    val coroutineScope = screenValue.coroutineScope
    val mainScreenNavController = rememberNavController()
    val openDrawer: ()->Unit = {
        coroutineScope.launch {
            scaffoldState.drawerState.open()
        }
    }

    Scaffold (
        topBar = {  },
    ) {
        Box(Modifier.padding(it)) {
        }
    }
}

