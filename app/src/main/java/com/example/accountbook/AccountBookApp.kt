package com.example.accountbook

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.accountbook.viewmodel.AccountBookAppViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.accountbook.componant.ScreenValue
import com.example.accountbook.navigation.NavItem
import com.example.accountbook.screen.*
import com.example.accountbook.viewmodel.AccountBookAppViewModelFactory
import kotlinx.coroutines.CoroutineScope

val drawerHeads = listOf(NavItem.MainScreen, NavItem.SetGroupScreen,
    NavItem.SetAccountScreen, NavItem.SetCategoryScreen, NavItem.SetCreditCardScreen)
val drawerBodies = mutableListOf<String>()

@Composable
fun AccountBookApp(
    navController: NavHostController = rememberNavController(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    AccountBookNavigationGraph(ScreenValue(navController, scaffoldState, coroutineScope))
}

@Composable
fun AccountBookNavigationGraph(
    screenValue: ScreenValue
) {
    val viewModel: AccountBookAppViewModel = viewModel(
        factory = AccountBookAppViewModelFactory(AppRoomDatabase.getInstance(LocalContext.current, screenValue.coroutineScope))
    )
    val accounts by viewModel.listOfAccounts.collectAsState()

    drawerBodies.clear()
    accounts.forEach {
        drawerBodies.add(it.name)
    }

    NavHost(navController = screenValue.navController, startDestination = NavItem.MainScreen.route) {
        drawerHeads.forEach { route ->
            composable(route.route) {
                viewModel.changeTitle(route.title)

                when (route.route) {
                    NavItem.MainScreen.route -> MainScreen(screenValue)
                    NavItem.SetGroupScreen.route -> SetGroupScreen(screenValue)
                    NavItem.SetAccountScreen.route -> SetAccountScreen(screenValue)
                    NavItem.SetCreditCardScreen.route -> SetCreditCardScreen(screenValue)
                    NavItem.SetCategoryScreen.route -> SetCategoryScreen(screenValue)
                }
            }
        }
        drawerBodies.forEach { route ->
            composable(route) {
                AccountScreen(title = route, screenValue)
            }
        }
    }
}