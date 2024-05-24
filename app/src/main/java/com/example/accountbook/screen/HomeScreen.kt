package com.example.accountbook.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.CommonScaffold
import com.example.accountbook.componant.RootTopBar
import com.example.accountbook.componant.Spinner
import com.example.accountbook.data.Account
import com.example.accountbook.data.Group
import com.example.accountbook.navigation.AppScreen
import com.example.accountbook.viewmodel.AccountBookAppViewModelFactory
import com.example.accountbook.viewmodel.HomeScreenViewModel

@Composable
fun HomeScreen(
    navController: NavHostController,
    viewModel: HomeScreenViewModel = viewModel(
        factory = AccountBookAppViewModelFactory(AppRoomDatabase.getInstance(LocalContext.current, rememberCoroutineScope()))
    )
) {
    val groups by viewModel.listOfGroups.collectAsState()
    val accounts by viewModel.listOfAccounts.collectAsState()

    val topBar = @Composable { RootTopBar(title = AppScreen.BottomBar.Home.title) }

    CommonScaffold(topBar, navController) {
        Screen(groups, accounts)
    }

}
@Composable
fun Screen(
    groups: List<Group>,
    accounts: List<Account>
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Column {

            val listGroup = mutableListOf("Total")
            groups.forEach { group ->
                listGroup.add(group.name)
            }

            Row {
                Spinner(list = listGroup, preselected = listGroup[0], onSelectionChanged = {})
                Column {
                    Row {
                        Text("총자산")
                        Text("   A")
                    }
                    Row {
                        Text("총평가액")
                        Text("   B")
                    }
                    Row {
                        Text("총수익율")
                        Text("   C")
                    }
                }
            }

            Card(
                Modifier
                    .border(1.dp, Color.Blue)
                    .fillMaxWidth()
                    .height(200.dp)
            ) {}

            Text("계좌")
            LazyRow {
                items(accounts) { item ->
                    AccountCard(item)
                }
            }

            Card(
                Modifier
                    .border(1.dp, Color.Red)
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
            }
//        val tabTitles = listOf("List", "Chart")
//        var tabIndex by remember { mutableStateOf(0) }

//            TabRow(selectedTabIndex = tabIndex) {
//                tabTitles.forEachIndexed { index, title ->
//                    Tab(selected = tabIndex == index,
//                        onClick = { tabIndex = index },
//                        text = { Text(text = title) })
//                }
//            }

//            when (tabIndex) {
//                0 -> ListScreen()
//                1 -> ChartScreen()
//            }
        }
    }
}

@Composable
fun AccountCard(item: Account) {
    Card(
        Modifier
            .border(width = 1.dp, color = Color.Black)
            .width(100.dp)
            .height(100.dp)
    ) {
        Column {
            Text(item.name)
            Text(item.company)
            Text(item.number)
        }
    }
}