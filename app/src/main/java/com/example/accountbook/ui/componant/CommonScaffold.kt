package com.example.accountbook.ui.componant

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.accountbook.navigation.AppScreen

@Composable
fun CommonScaffold(
    topBar: @Composable () -> Unit,
    navController: NavHostController,
    screen: @Composable () -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        topBar = topBar,
        bottomBar = @Composable {
            val bottomItems = listOf(AppScreen.BottomBar.Home, AppScreen.BottomBar.Book, AppScreen.BottomBar.Setting)

            NavigationBar {
                bottomItems.forEachIndexed { index, item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.route) },
                        label = { Text(item.route) },
                        selected = currentRoute == item.route,
                        onClick = {
                            navigate(navController, item.route)
                        }
                    )
                }
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            screen()
        }
    }
}

fun navigate(navController: NavHostController, route: String) {
    navController.navigate(route) {
        navController.graph.startDestinationRoute?.let { route ->
            popUpTo(route) {
                inclusive = true
                saveState = true
            }
        }
        launchSingleTop = true
        restoreState = true
    }
}