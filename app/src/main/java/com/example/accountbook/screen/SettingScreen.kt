package com.example.accountbook.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.CommonScaffold
import com.example.accountbook.componant.RootTopBar
import com.example.accountbook.navigation.AppScreen
import com.example.accountbook.viewmodel.AccountBookAppViewModelFactory
import com.example.accountbook.viewmodel.SettingScreenViewModel

val listOfSetting = listOf(AppScreen.SetScreen.SetGroup, AppScreen.SetScreen.SetAccount, AppScreen.SetScreen.SetCategory, AppScreen.SetScreen.SetCreditCard)
@Composable
fun SettingScreen(
    navController: NavHostController,
    viewModel: SettingScreenViewModel = viewModel(
        factory = AccountBookAppViewModelFactory(AppRoomDatabase.getInstance(LocalContext.current, rememberCoroutineScope()))
    )
) {
    val topBar = @Composable { RootTopBar(title = AppScreen.BottomBar.Setting.title) }

    CommonScaffold(topBar = topBar, navController) {
        Screen(navController)
    }
}

@Composable
fun Screen(navController: NavHostController) {
    Column {
        listOfSetting.forEach {
            ListItem(
                headlineContent = { Text(it.title) },
                modifier = Modifier.clickable(onClick = { navController.navigate(it.route) })
            )
            HorizontalDivider()
        }
    }
}