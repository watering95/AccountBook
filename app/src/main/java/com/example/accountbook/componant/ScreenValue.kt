package com.example.accountbook.componant

import androidx.compose.material.ScaffoldState
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class ScreenValue(
    var navController: NavHostController,
    var scaffoldState: ScaffoldState,
    var coroutineScope: CoroutineScope
) {
    val openDrawer: ()->Unit = {
        coroutineScope.launch {
            scaffoldState.drawerState.open()
        }
    }
}

