package com.example.accountbook

import androidx.compose.material.ScaffoldState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
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
import kotlinx.coroutines.CoroutineScope

val drawerHeads = listOf(NavItem.MainScreen.route, "Set Group","Set Account", "Set Card", "Set Category","DB")
val drawerBodies = listOf("Account1","Account2","Account3","Account4","Account5","Account6","Account1","Account2","Account3","Account4","Account5","Account6","Account1","Account2","Account3","Account4","Account5","Account6")

@Composable
fun AccountBookApp(
    navController: NavHostController = rememberNavController(),
    scaffoldState: ScaffoldState = rememberScaffoldState(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) {
    val screenValue = ScreenValue(navController, scaffoldState, coroutineScope)

    AccountBookNavHost(screenValue)
}

@Composable
fun AccountBookNavHost(
    screenValue: ScreenValue,
    viewModel: AccountBookAppViewModel = viewModel()
) {
    NavHost(navController = screenValue.navController, startDestination = NavItem.MainScreen.route) {
        drawerHeads.forEach { route ->
            composable(route) {
                val db = (LocalContext.current as MainActivity).db

                viewModel.changeTitle(route)

                when (route) {
                    NavItem.MainScreen.route -> MainScreen(screenValue)
                    "Set Group" -> SetGroupScreen(db, screenValue)
                    "Set Account" -> SetAccountScreen(db, screenValue)
                    "Set Card" -> SetCardScreen(db, screenValue)
                    "Set Category" -> SetCategoryScreen(db, screenValue)
                }
            }
        }
        drawerBodies.forEach { route ->
            composable(route) {
                viewModel.changeTitle(route)
                AccountScreen()
            }

        }
    }
}