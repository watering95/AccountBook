package com.example.accountbook.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.screen.bookscreen.BookScreenComposable
import com.example.accountbook.screen.homescreen.HomeScreenComposable
import com.example.accountbook.screen.categorysetscreen.CategorySetScreenComposable
import com.example.accountbook.screen.creditcardsetscreen.CreditCardSetScreenComposable
import com.example.accountbook.screen.groupsetscreen.GroupSetScreenComposable
import com.example.accountbook.screen.settingscreen.SettingScreenComposable
import com.example.accountbook.screen.accountsetscreen.AccountSetScreenComposable

@Composable
fun RootNavHost(navController: NavHostController, db: AppRoomDatabase) {
    NavHost(navController = navController, startDestination = AppScreen.BottomBar.Home.route) {
        composable(
            route = AppScreen.BottomBar.Home.route
        ) {
            HomeScreenComposable(navController, db)
        }
        composable(
            route = AppScreen.BottomBar.Book.route
        ) {
            BookScreenComposable(navController)
        }
        composable(
            route = AppScreen.BottomBar.Setting.route
        ) {
            SettingScreenComposable(navController, db)
        }
        composable(
            route = AppScreen.SetScreen.GroupSet.route
        ) {
            GroupSetScreenComposable(navController, db)
        }
        composable(
            route = AppScreen.SetScreen.AccountSet.route
        ) {
            AccountSetScreenComposable(navController, db)
        }
        composable(
            route = AppScreen.SetScreen.CategorySet.route
        ) {
            CategorySetScreenComposable(navController, db)
        }
        composable(
            route = AppScreen.SetScreen.CreditCardSet.route
        ) {
            CreditCardSetScreenComposable(navController, db)
        }
    }
}



