package com.example.accountbook.screen.accountscreen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.data.AccountLog
import com.example.accountbook.viewmodel.AccountBookAppViewModelFactory

@Composable
fun AccountScreenComposable(
    title: String
) {
    val viewModel: AccountScreenViewModel = viewModel(
        factory = AccountBookAppViewModelFactory(AppRoomDatabase.getInstance(LocalContext.current, rememberCoroutineScope()))
    )
    val items by viewModel.items.collectAsState()
    Screen(items, title)
}

@Composable
fun Screen(
    items: List<AccountLog>,
    title: String,
) {

    val input: ()->Unit = {}
    val money: ()->Unit = {}

}

@Composable
private fun ShowList(items: List<AccountLog>) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            LazyVerticalGrid(
                modifier = Modifier.padding(4.dp),
                columns = GridCells.Adaptive(minSize = 256.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(items) { item ->
                    ItemCardComposable(item)
                }
            }
        }
}

@Composable
fun ItemCardComposable(item:AccountLog) {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Card(
                modifier = Modifier
                    .padding(4.dp)
                    .height(64.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { }
                        )
                    },
            ) {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
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