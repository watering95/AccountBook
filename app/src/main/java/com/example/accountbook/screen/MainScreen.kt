package com.example.accountbook.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.accountbook.viewmodel.MainScreenViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen(
    viewModel: MainScreenViewModel = viewModel()) {
    val mainScreenNavController = rememberNavController()
    val openDrawer: ()->Unit = {

    }

    Scaffold (
        topBar = {  },
    ) {
        Box(Modifier.padding(it)) {
        }
    }
}

