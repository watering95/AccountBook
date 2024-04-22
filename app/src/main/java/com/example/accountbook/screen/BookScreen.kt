package com.example.accountbook.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun BookScreen() {
    Column {
        val configuration = LocalConfiguration.current
        val screenWidth = configuration.screenWidthDp

        val listSpending = listOf("12", "22", "32", "42", "52", "62")
        val listIncome = listOf("13", "23", "33", "43", "53", "63")

        Row {
            Column {
                Text("Date")
                Card(
                    Modifier
                        .border(1.dp, Color.Blue)
                        .width(screenWidth.dp / 2)
                        .height(50.dp)
                ) {}
            }
            Column {
                Text("Monthly Income")
                Text("Monthly Spending")
            }
        }

        Text("Daily Spending")
        LazyRow {
            items(listSpending) { item ->
                SpendingCard(item)
            }
        }

        Text("Daily Income")
        LazyRow {
            items(listIncome) { item ->
                IncomeCard(item)
            }
        }
    }
}

@Composable
fun IncomeCard(item: String) {
    Card(
        Modifier
            .border(width = 1.dp, color = Color.Black)
            .width(100.dp)
            .height(100.dp)
    ) {
        Text(item)
    }
}
@Composable
fun SpendingCard(item: String) {
    Card(
        Modifier
            .border(width = 1.dp, color = Color.Black)
            .width(100.dp)
            .height(100.dp)
    ) {
        Text(item)
    }
}