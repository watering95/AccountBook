package com.example.accountbook.screen.settingscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.ui.componant.CommonScaffold
import com.example.accountbook.ui.componant.TopBar
import com.example.accountbook.navigation.AppScreen
import com.example.accountbook.viewmodel.AccountBookAppViewModelFactory

val listOfSetting = listOf(AppScreen.SetScreen.GroupSet, AppScreen.SetScreen.AccountSet, AppScreen.SetScreen.CategorySet, AppScreen.SetScreen.CreditCardSet)
@Composable
fun SettingScreenComposable(
    navController: NavHostController,
    db: AppRoomDatabase,
    viewModel: SettingScreenViewModel = viewModel(
        factory = AccountBookAppViewModelFactory(db)
    )
) {
    val topBar = @Composable { TopBar(title = AppScreen.BottomBar.Setting.title) }

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