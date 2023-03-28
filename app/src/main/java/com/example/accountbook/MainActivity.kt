package com.example.accountbook

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.accountbook.componant.Drawer
import com.example.accountbook.componant.MainTopBar
import com.example.accountbook.componant.SettingTopBar
import com.example.accountbook.screen.*
import com.example.accountbook.ui.theme.AccountBookTheme
import com.example.accountbook.viewmodel.MainViewModel
import kotlinx.coroutines.*

class MainActivity : ComponentActivity() {
    private val scope = CoroutineScope(Job() + Dispatchers.Main)
    lateinit var db: AppRoomDatabase
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = AppRoomDatabase.getInstance(this, scope)
        setContent {
            AccountBookTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    MainScreenView()
                }
            }
        }
    }
}

@Composable
fun MainScreenView() {
    val navController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val openDrawer = {
        coroutineScope.launch {
            scaffoldState.drawerState.open()
        }
    }

    Scaffold (
        scaffoldState = scaffoldState,
        topBar = { NavigationTopbar(navController = navController, openDrawer = openDrawer)},
        bottomBar = { BottomNavigation(navController) },
        drawerContent = {
            Drawer { route ->
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
            NavigationGraph(navController = navController)
        }
    }
}

sealed class NavItem(var title: String, var icon: Int, var route: String) {
    object HomeScreen : NavItem("Home", R.drawable.ic_launcher_foreground, "Home")
    object BookScreen : NavItem("Book", R.drawable.ic_launcher_foreground, "Book")
}

val bottomScreens = listOf(NavItem.HomeScreen, NavItem.BookScreen)
val drawerHeads = listOf("Set Group","Set Account", "Set Card", "Set Category","DB")
val drawerBodies = listOf("Account1","Account2","Account3","Account4","Account5","Account6","Account1","Account2","Account3","Account4","Account5","Account6","Account1","Account2","Account3","Account4","Account5","Account6")

@Composable
fun BottomNavigation(navController: NavController) {
    BottomNavigation(backgroundColor = colorResource(id = R.color.teal_200), contentColor = Color.Black) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        bottomScreens.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title)},
                label = { Text(text = item.title, fontSize = 15.sp) },
                selectedContentColor = Color.Black,
                unselectedContentColor = Color.Black.copy(0.4f),
                alwaysShowLabel = true,
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) {
                                saveState = true
                            }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
@Composable
fun NavigationTopbar(navController: NavHostController, viewModel:MainViewModel = viewModel(), openDrawer: () -> Job) {
    NavHost(navController = navController, startDestination = NavItem.HomeScreen.route) {
        composable(NavItem.HomeScreen.route) {
            MainTopBar(
                title = viewModel.title.value,
                onButtonNavigationClicked = { openDrawer() }
            )
        }
        composable(NavItem.BookScreen.route) {
            MainTopBar(
                title = viewModel.title.value,
                onButtonNavigationClicked = { openDrawer() }
            )
        }
        drawerHeads.forEach { route ->
            composable(route) {
                when(route) {
                    "Set Group" -> SettingTopBar(
                        title = viewModel.title.value,
                        onButtonNavigationClicked = { openDrawer() },
                        onButtonSaveClicked = {},
                        onButtonDeleteClicked = {}
                    )
                    "Set Account" -> SettingTopBar(
                        title = viewModel.title.value,
                        onButtonNavigationClicked = { openDrawer() },
                        onButtonSaveClicked = {},
                        onButtonDeleteClicked = {}
                    )
                    "Set Card" -> SettingTopBar(
                        title = viewModel.title.value,
                        onButtonNavigationClicked = { openDrawer() },
                        onButtonSaveClicked = {},
                        onButtonDeleteClicked = {}
                    )
                    "Set Category" -> SettingTopBar(
                        title = viewModel.title.value,
                        onButtonNavigationClicked = { openDrawer() },
                        onButtonSaveClicked = {},
                        onButtonDeleteClicked = {}
                    )
                    "DB" -> MainTopBar(
                        title = viewModel.title.value,
                        onButtonNavigationClicked = { openDrawer() }
                    )
                }
            }
        }
        drawerBodies.forEach { route ->
            composable(route) {
                MainTopBar(
                    title = viewModel.title.value,
                    onButtonNavigationClicked = { openDrawer() }
                )
            }
        }
    }
}

@Composable
fun NavigationGraph(navController: NavHostController, viewModel: MainViewModel = viewModel()) {
    NavHost(navController = navController, startDestination = NavItem.HomeScreen.route) {
        composable(NavItem.HomeScreen.route) {
            viewModel.changeTitle(NavItem.HomeScreen.title)
            HomeScreen()
        }
        composable(NavItem.BookScreen.route) {
            viewModel.changeTitle(NavItem.BookScreen.title)
            BookScreen()
        }
        drawerHeads.forEach { route ->
            composable(route) {
                viewModel.changeTitle(route)
                when(route) {
                    "Set Group" -> SetGroupScreen((LocalContext.current as MainActivity).db)
                    "Set Account" -> SetAccountScreen()
                    "Set Card" -> SetCardScreen()
                    "Set Category" -> SetCategoryScreen()
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

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainScreenView()
}