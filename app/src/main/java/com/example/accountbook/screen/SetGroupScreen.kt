package com.example.accountbook.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.componant.Drawer
import com.example.accountbook.componant.ScreenValue
import com.example.accountbook.componant.TopBarWithAdd
import com.example.accountbook.componant.TopBarWithDelete
import com.example.accountbook.data.Group
import com.example.accountbook.drawerBodies
import com.example.accountbook.drawerHeads
import com.example.accountbook.ui.theme.CardListTheme
import com.example.accountbook.ui.theme.CardTheme
import com.example.accountbook.viewmodel.AccountBookAppViewModelFactory
import com.example.accountbook.viewmodel.SetGroupScreenViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SetGroupScreen(
    screenValue: ScreenValue
) {
    val viewModel: SetGroupScreenViewModel = viewModel(
        factory = AccountBookAppViewModelFactory(AppRoomDatabase.getInstance(LocalContext.current, screenValue.coroutineScope))
    )
    val items by viewModel.listOfItems.collectAsState()

    ItemScreen(screenValue = screenValue, items)
}

// 화면 구성
@Composable
fun ItemScreen(
    screenValue: ScreenValue,
    items: List<Group>,
    viewModel: SetGroupScreenViewModel = viewModel()
) {
    val navController = screenValue.navController
    val scaffoldState = screenValue.scaffoldState
    val coroutineScope = screenValue.coroutineScope
    val openDrawer = screenValue.openDrawer
    val addItem = {
        viewModel.aCardIsTaped(Group(name=""))
    }
    val deleteItems: ()-> Unit = {
        var selected = arrayOf<Group>()
        val iterator = viewModel.checkedCards.value.iterator()
        var index = 0
        while(iterator.hasNext()) {
            if(iterator.next()) {
                val new = selected.plus(items[index])
                selected = new
            }
            index++
        }
        viewModel.delete(selected)
        viewModel.aCardIsLongPressed()
    }

    Scaffold (
        scaffoldState = scaffoldState,
        topBar = {
            if(viewModel.aCardIsLongPressed.value)
                TopBarWithDelete(
                    title = viewModel.title.value,
                    onButtonNavigationClicked = openDrawer,
                    onButtonDeleteClicked = deleteItems
                )
            else
                TopBarWithAdd(
                    title = viewModel.title.value,
                    onButtonNavigationClicked = openDrawer,
                    onButtonAddClicked =  addItem
                )
        },
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
            ShowItemCards(items)
            if(viewModel.aCardIsTaped.value) ItemEditDialog()
        }
    }
}

// 리스트 보여주기
@Composable
private fun ShowItemCards(
    items: List<Group>
) {
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
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
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
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colors.background) {
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