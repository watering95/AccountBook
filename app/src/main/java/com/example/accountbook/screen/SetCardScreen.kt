package com.example.accountbook.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Checkbox
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.componant.Drawer
import com.example.accountbook.componant.ScreenValue
import com.example.accountbook.componant.Spinner
import com.example.accountbook.componant.TopBarWithAdd
import com.example.accountbook.componant.TopBarWithDelete
import com.example.accountbook.data.Card
import com.example.accountbook.drawerBodies
import com.example.accountbook.drawerHeads
import com.example.accountbook.ui.theme.CardListTheme
import com.example.accountbook.ui.theme.CardTheme
import com.example.accountbook.viewmodel.SetCardScreenViewModel
import com.example.accountbook.viewmodel.SetCardScreenViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SetCardScreen(
    db: AppRoomDatabase,
    screenValue: ScreenValue,
    viewModel: SetCardScreenViewModel = viewModel(
        factory = SetCardScreenViewModelFactory(db)
    )
) {
    val cards by viewModel.listOfItems.collectAsState()

    ItemScreen(screenValue = screenValue, items = cards)
}

@Composable
fun ItemScreen(
    screenValue: ScreenValue,
    items: List<Card>,
    viewModel: SetCardScreenViewModel = viewModel()
) {
    val navController = screenValue.navController
    val scaffoldState = screenValue.scaffoldState
    val coroutineScope = screenValue.coroutineScope
    val openDrawer: ()->Unit = {
        coroutineScope.launch {
            scaffoldState.drawerState.open()
        }
    }
    val addItem = {
        viewModel.clearSelectedCards()
        viewModel.aCardIsTaped()
    }
    val deleteItems: ()-> Unit = {
        var selected = arrayOf<Card>()
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

@Composable
private fun ShowItemCards(
    items: List<Card>
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
                    val card by remember { mutableStateOf(item) }
                    val index by remember { mutableIntStateOf(items.indexOf(item)) }
                    val checkMode = remember { mutableStateOf(false) }

                    ItemCard(index = index, item = card, checkMode = checkMode)
                }
            }
        }
    }
}

@Composable
fun ItemCard(
    index: Int,
    item: Card,
    checkMode: MutableState<Boolean>,
    viewModel: SetCardScreenViewModel = viewModel()
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

@Composable
private fun ItemEditDialog(
    viewModel: SetCardScreenViewModel = viewModel()
) {
    val accounts by viewModel.listOfAccounts.collectAsState()

    Dialog(onDismissRequest = { viewModel.aCardIsTaped() }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 10.dp)
        ) {
            Column(modifier = Modifier.padding(all = 16.dp)) {
                val card = viewModel.selectedItem.value
                val selectedAccount = viewModel.selectedAccount.collectAsState()
                val list = mutableListOf<String>()
                accounts.forEach { account ->
                    list.add(account.name)
                }
                var name by remember { mutableStateOf(card.name) }
                var company by remember { mutableStateOf(card.company) }
                var number by remember { mutableStateOf(card.number) }
                var idAccount = card.idAccount
                val preSelected =
                        if(idAccount == -1) list[0]
                        else selectedAccount.value.name
                val saveItem = {
                    card.name = name
                    card.company = company
                    card.number = number
                    card.idAccount = idAccount
                    if(card.uid == 0) viewModel.insert(card) else viewModel.update(card)
                    viewModel.aCardIsTaped()
                }

                Text("Card Name")
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = name,
                    onValueChange = {name = it})
                Text("Company")
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = company,
                    onValueChange = {company = it})
                Text("Card Number")
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = number,
                    onValueChange = {number = it})
                Text("Account")
                Spinner(
                    list = list,
                    preselected = preSelected,
                    onSelectionChanged = { selected ->
                        idAccount = accounts[list.indexOf(selected)].uid
                    }
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
