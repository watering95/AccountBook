package com.example.accountbook.screen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.data.Group
import com.example.accountbook.ui.theme.CardListTheme
import com.example.accountbook.ui.theme.CardTheme
import com.example.accountbook.viewmodel.AccountBookAppViewModelFactory
import com.example.accountbook.viewmodel.SetGroupScreenViewModel

@Composable
fun SetGroupScreen(
) {
    val viewModel: SetGroupScreenViewModel = viewModel(
        factory = AccountBookAppViewModelFactory(AppRoomDatabase.getInstance(LocalContext.current, rememberCoroutineScope()))
    )
    val items by viewModel.listOfItems.collectAsState()

    ShowItemCards(items)

    if(viewModel.aCardIsTaped.value) ItemEditDialog()
}

// 리스트 보여주기
@Composable
private fun ShowItemCards(
    items: List<Group>
) {
    CardListTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            LazyVerticalGrid(
                modifier = Modifier.padding(4.dp),
                columns = GridCells.Adaptive(minSize = 256.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(items) { item ->
                    val group by remember { mutableStateOf(item) }
                    val index by remember { mutableIntStateOf(items.indexOf(item)) }
                    val checkMode = remember {mutableStateOf(false)}

                    ItemCard(index = index, item = group, checkMode = checkMode)
                }
            }
        }
    }
}

// 카드에 아이템 정보 표시
@Composable
fun ItemCard(
    index: Int,
    item: Group,
    checkMode: MutableState<Boolean>,
    viewModel: SetGroupScreenViewModel = viewModel()
) {
    CardTheme {
        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Card(
                modifier = Modifier
                    .padding(4.dp)
                    .height(64.dp)
                    .pointerInput(Unit) {
                        detectTapGestures(
                            onTap = { viewModel.aCardIsTaped(item) },
                            onLongPress = { viewModel.aCardIsLongPressed() }
                        )
                    },
            ) {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            modifier = Modifier
                                .padding(16.dp)
                                .weight(1f),
                            text = item.name
                        )
                        if (viewModel.aCardIsLongPressed.value) Checkbox(
                            modifier = Modifier
                                .weight(1f)
                                .wrapContentWidth(Alignment.End),
                            checked = checkMode.value,
                            onCheckedChange = {
                                checkMode.value = it
                                viewModel.checkedACard(index, it)
                            }
                        ) else checkMode.value = false
                    }
                }
            }
        }
    }
}

// 선택된 아이템 정보 편집 화면
@Composable
private fun ItemEditDialog(
    viewModel: SetGroupScreenViewModel = viewModel()
) {
    Dialog(onDismissRequest = { viewModel.aCardIsTaped() }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 10.dp)
        ) {
            Column(modifier = Modifier.padding(all = 16.dp)) {
                val group = viewModel.selectedItem.value
                var name by remember {mutableStateOf(group.name)}
                val saveItem = {
                    group.name = name
                    if(group.uid == 0L) viewModel.insert(group) else viewModel.update(group)
                    viewModel.aCardIsTaped()
                }

                Text("Group Name")
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = name,
                    onValueChange = {name = it}
                )
                Row {
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = saveItem
                    ) {
                        Text("Save")
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Button(
                        modifier = Modifier.weight(1f),
                        onClick = { viewModel.aCardIsTaped() }
                    ) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}