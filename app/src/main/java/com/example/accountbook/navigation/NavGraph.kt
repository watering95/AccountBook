package com.example.accountbook.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.accountbook.screen.BookScreen
import com.example.accountbook.screen.HomeScreen
import com.example.accountbook.screen.SetAccountScreen
import com.example.accountbook.screen.SetCategoryScreen
import com.example.accountbook.screen.SetCreditCardScreen
import com.example.accountbook.screen.SetGroupScreen
import com.example.accountbook.screen.SettingScreen

@Composable
fun RootNavHost(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = AppScreen.BottomBar.Home.route) {
        composable(
            route = AppScreen.BottomBar.Home.route
        ) {
            HomeScreen()
        }
        composable(
            route = AppScreen.BottomBar.Book.route
        ) {
            BookScreen()
        }
        composable(
            route = AppScreen.BottomBar.Setting.route
        ) {
            SettingScreen(navHostController)
        }
        composable(
            route = AppScreen.SetScreen.SetGroup.route
        ) {
            SetGroupScreen()
        }
        composable(
            route = AppScreen.SetScreen.SetAccount.route
        ) {
            SetAccountScreen()
        }
        composable(
            route = AppScreen.SetScreen.SetCategory.route
        ) {
            SetCategoryScreen()
        }
        composable(
            route = AppScreen.SetScreen.SetCreditCard.route
        ) {
            SetCreditCardScreen()
        }

    }
}



