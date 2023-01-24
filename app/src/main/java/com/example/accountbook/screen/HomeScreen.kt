package com.example.accountbook.screen

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.accountbook.componant.Spinner

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        Column {
            val listGroup = listOf("A", "B", "C")
            val listAccounts = listOf("1", "2", "3", "4", "5", "6")

            Row {
                Spinner(list = listGroup, preselected = listGroup[2], onSelectionChanged = {})
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
                Modifier.border(1.dp,Color.Blue).fillMaxWidth().height(200.dp)
            ) {}

            Text("계좌")
            LazyRow {
                items(listAccounts) { item ->
                    AccountCard(item)
                }
            }

            Card(
                Modifier.border(1.dp,Color.Red).fillMaxWidth().height(200.dp)
            ) {}
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
fun AccountCard(item: String) {
    Card(
        Modifier.border(width = 1.dp, color= Color.Black).width(100.dp).height(100.dp)
    ) {
        Text(item)
    }
}