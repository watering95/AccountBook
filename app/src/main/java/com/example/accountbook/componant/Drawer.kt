package com.example.accountbook.componant

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.accountbook.navigation.AppScreen

@Composable
fun Drawer(
    drawerHeads: List<AppScreen>,
    drawerBodies: List<String>,
    itemClick: (String) -> Unit
) {
    ModalDrawerSheet {
        Column {
            drawerHeads.forEach { item ->
                DrawerHeader(item.route) {
                    itemClick(item.route)
                }
            }
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(drawerBodies) { item ->
                DrawerBody(item) {
                    itemClick(item)
                }
            }
        }
    }
}

@Composable
fun DrawerHeader(item: String, itemClick:(String) -> Unit) {
    NavigationListItem(item = item) {
        itemClick(item)
    }
}

@Composable
fun DrawerBody(item: String, itemClick: (String) -> Unit) {
    NavigationListItem(item = item) {
        itemClick(item)
    }
}

@Composable
fun NavigationListItem(item: String, itemClick:(String) -> Unit) {
    Text(
        modifier = Modifier
            .padding(16.dp)
            .clickable { itemClick(item) },
        text = item
    )
}