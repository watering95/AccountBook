package com.example.accountbook.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.accountbook.componant.Drawer
import com.example.accountbook.componant.MainTopBar
import com.example.accountbook.navigation.BottomNavigation
import com.example.accountbook.viewmodel.MainScreenViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.accountbook.MainActivity
import com.example.accountbook.componant.ScreenValue
import com.example.accountbook.drawerBodies
import com.example.accountbook.drawerHeads
import com.example.accountbook.navigation.NavItem
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
        scaffoldState = scaffoldState,
        topBar = { MainTopBar(title = viewModel.title.value, openDrawer) },
        bottomBar = { BottomNavigation(mainScreenNavController) },
        drawerContent = {
            Drawer(
                drawerHeads = drawerHeads,
                drawerBodies = drawerBodies
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
        Box(Modifier.padding(it)) {
            NavigationGraph(mainScreenNavController)
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, viewModel: MainScreenViewModel = viewModel()) {
    NavHost(navController = navController, startDestination = NavItem.HomeScreen.route) {
        composable(NavItem.HomeScreen.route) {
            val db = (LocalContext.current as MainActivity).db

            viewModel.changeTitle(NavItem.HomeScreen.route)
            HomeScreen(db)
        }
        composable(NavItem.BookScreen.route) {
            viewModel.changeTitle(NavItem.BookScreen.route)
            BookScreen()
        }
    }
}