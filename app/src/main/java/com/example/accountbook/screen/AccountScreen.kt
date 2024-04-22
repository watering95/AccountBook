package com.example.accountbook.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.componant.Drawer
import com.example.accountbook.componant.ScreenValue
import com.example.accountbook.componant.TopBarForAccountScreen
import com.example.accountbook.data.AccountLog
import com.example.accountbook.drawerBodies
import com.example.accountbook.drawerHeads
import com.example.accountbook.ui.theme.CardListTheme
import com.example.accountbook.ui.theme.CardTheme
import com.example.accountbook.viewmodel.AccountBookAppViewModelFactory
import com.example.accountbook.viewmodel.AccountScreenViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AccountScreen(
    title: String,
    screenValue: ScreenValue,
) {
    val viewModel: AccountScreenViewModel = viewModel(
        factory = AccountBookAppViewModelFactory(AppRoomDatabase.getInstance(LocalContext.current, screenValue.coroutineScope))
    )
    val items by viewModel.listOfItems.collectAsState()
    Screen(items, title, screenValue)
}

@Composable
fun Screen(
    items: List<AccountLog>,
    title: String,
    screenValue: ScreenValue
) {
    val navController = screenValue.navController
    val scaffoldState = screenValue.scaffoldState
    val coroutineScope = screenValue.coroutineScope
    val openDrawer: ()->Unit = {
        coroutineScope.launch {
            scaffoldState.drawerState.open()
        }
    }
    val input: ()->Unit = {}
    val money: ()->Unit = {}

    Scaffold (
        scaffoldState = scaffoldState,
        topBar = { TopBarForAccountScreen(title = title, openDrawer, input, money) },
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
            ShowList(items)
        }
    }
}

@Composable
private fun ShowList(items: List<AccountLog>) {
    CardListTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
            LazyVerticalGrid(
                modifier = Modifier.padding(4.dp),
                columns = GridCells.Adaptive(minSize = 256.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(items) { item ->
                    ItemCard(item)
                }
            }
        }
    }
}

@Composable
fun ItemCard(item:AccountLog) {
    CardTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
            Card(
                modifier = Modifier
                    .padding(4.dp)
                    .height(64.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = {  }
                        )
                    },
            ) {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(16.dp)
                                .weight(1f),
                            text = item.date
                        )
                        Text(
                            modifier = Modifier
                                .padding(16.dp)
                                .weight(1f),
                            text = item.balance.toString()
                        )
                        Text(
                            modifier = Modifier
                                .padding(16.dp)
                                .weight(1f),
                            text = item.evaluation.toString()
                        )
                        Text(
                            modifier = Modifier
                                .padding(16.dp)
                                .weight(1f),
                            text = (item.evaluation/item.balance).toString()
                        )
                    }
                }
            }
        }
    }
}