package com.example.accountbook.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import com.example.accountbook.componant.Spinner

@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top
    ) {
        val tabTitles = listOf("List", "Chart")
        val options = listOf("A", "B")

        var tabIndex by remember { mutableStateOf(0) }

        Column {
            val list = listOf("A", "B", "C")

            Spinner(list = list, preselected = list[2], onSelectionChanged = {})
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

            TabRow(selectedTabIndex = tabIndex) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(selected = tabIndex == index,
                        onClick = { tabIndex = index },
                        text = { Text(text = title) })
                }
            }

            when (tabIndex) {
                0 -> ListScreen()
                1 -> ChartScreen()
            }
        }
    }
}

@Composable
fun ListScreen() {
    Text("1")
}

@Composable
fun ChartScreen() {
    Text("2")
}