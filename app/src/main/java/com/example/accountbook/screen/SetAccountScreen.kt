package com.example.accountbook.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.componant.Drawer
import com.example.accountbook.componant.MainTopBar
import com.example.accountbook.componant.ScreenValue
import com.example.accountbook.drawerBodies
import com.example.accountbook.drawerHeads
import com.example.accountbook.navigation.BottomNavigation
import com.example.accountbook.viewmodel.SetAccountScreenViewModelFactory
import com.example.accountbook.viewmodel.SetAccountScreenViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SetAccountScreen(
    db: AppRoomDatabase,
    screenValue: ScreenValue,
    viewModel: SetAccountScreenViewModel = viewModel(
        factory = SetAccountScreenViewModelFactory(db)
    )
) {
    val navController = screenValue.navController
    val scaffoldState = screenValue.scaffoldState
    val coroutineScope = screenValue.coroutineScope
    val openDrawer: ()->Unit = {
        coroutineScope.launch {
            scaffoldState.drawerState.open()
        }
    }

    Scaffold (
        scaffoldState = scaffoldState,
        topBar = { MainTopBar(title = viewModel.title.value, openDrawer) },
        drawerContent = {
            Drawer(
                drawerBodies = drawerBodies,
                drawerHeads = drawerHeads
            ) { route ->
                coroutineScope.launch {
                    delay(250)
                    scaffoldState.drawerState.close()
                }
                navController.navigate(route) {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    launchSingleTop = true
                    restoreState = true
                }
            }
        }
    ) {
        Box(
            modifier = Modifier
                .padding(it)
                .background(MaterialTheme.colors.primary)
        ) {
            Text(
                text = "Set Account Screen",
                style = MaterialTheme.typography.h6,
                textAlign = TextAlign.Center,
                color = Color.White,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}