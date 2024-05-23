package com.example.accountbook

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.accountbook.componant.RootTopBar
import com.example.accountbook.componant.SettingTopBar
import com.example.accountbook.navigation.AppScreen
import com.example.accountbook.navigation.RootNavHost
import com.example.accountbook.viewmodel.AccountBookAppViewModel
import com.example.accountbook.viewmodel.AccountBookAppViewModelFactory

@Composable
fun AccountBookApp() {
    val viewModel: AccountBookAppViewModel = viewModel(
        factory = AccountBookAppViewModelFactory(AppRoomDatabase.getInstance(LocalContext.current, rememberCoroutineScope()))
    )
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val bottomItems = listOf(AppScreen.BottomBar.Home, AppScreen.BottomBar.Book, AppScreen.BottomBar.Setting)

    val topBar = @Composable {
        when(currentRoute) {
            AppScreen.BottomBar.Home.route -> RootTopBar(title = viewModel.title.value)
            AppScreen.BottomBar.Book.route -> RootTopBar(title = viewModel.title.value)
            AppScreen.BottomBar.Setting.route -> RootTopBar(title = viewModel.title.value)
            AppScreen.SetScreen.SetGroup.route -> SettingTopBar(title = AppScreen.SetScreen.SetGroup.title, onButtonNavigationClicked = { navController.popBackStack() })
            AppScreen.SetScreen.SetAccount.route -> SettingTopBar(title = AppScreen.SetScreen.SetAccount.title, onButtonNavigationClicked = { navController.popBackStack() })
            AppScreen.SetScreen.SetCategory.route -> SettingTopBar(title = AppScreen.SetScreen.SetCategory.title, onButtonNavigationClicked = { navController.popBackStack() })
            AppScreen.SetScreen.SetCreditCard.route -> SettingTopBar(title = AppScreen.SetScreen.SetCreditCard.title, onButtonNavigationClicked = { navController.popBackStack() })
        }
 
    }
    val bottomBar = @Composable {
        NavigationBar {
            bottomItems.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = { Icon(item.icon, contentDescription = item.route) },
                    label = { Text(item.route) },
                    selected = currentRoute == item.route,
                    onClick = {
                        viewModel.changeTitle(item.title)
                        navigate(navController, item.route)
                    }
                )
            }
        }
    }

    CommonScaffold(topBar, bottomBar) {
        RootNavHost(navController)
    }
}

@Composable
fun CommonScaffold(
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
    screen: @Composable () -> Unit
) {
    Scaffold(
        topBar = topBar,
        bottomBar = bottomBar
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