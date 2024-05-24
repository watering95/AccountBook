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
fun RootNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = AppScreen.BottomBar.Home.route) {
        composable(
            route = AppScreen.BottomBar.Home.route
        ) {
            HomeScreen(navController)
        }
        composable(
            route = AppScreen.BottomBar.Book.route
        ) {
            BookScreen(navController)
        }
        composable(
            route = AppScreen.BottomBar.Setting.route
        ) {
            SettingScreen(navController)
        }
        composable(
            route = AppScreen.SetScreen.SetGroup.route
        ) {
            SetGroupScreen(navController)
        }
        composable(
            route = AppScreen.SetScreen.SetAccount.route
        ) {
            SetAccountScreen(navController)
        }
        composable(
            route = AppScreen.SetScreen.SetCategory.route
        ) {
            SetCategoryScreen(navController)
        }
        composable(
            route = AppScreen.SetScreen.SetCreditCard.route
        ) {
            SetCreditCardScreen(navController)
        }
    }
}



