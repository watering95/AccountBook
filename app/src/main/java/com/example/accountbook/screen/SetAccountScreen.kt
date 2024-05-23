package com.example.accountbook.screen

import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.componant.Spinner
import com.example.accountbook.data.Account
import com.example.accountbook.data.Group
import com.example.accountbook.ui.theme.CardListTheme
import com.example.accountbook.ui.theme.CardTheme
import com.example.accountbook.viewmodel.AccountBookAppViewModelFactory
import com.example.accountbook.viewmodel.SetAccountScreenViewModel

@Composable
fun SetAccountScreen(
) {
    val viewModel: SetAccountScreenViewModel = viewModel(
        factory = AccountBookAppViewModelFactory(AppRoomDatabase.getInstance(LocalContext.current, rememberCoroutineScope()))
    )
    val accounts by viewModel.listOfItems.collectAsState()

    ShowItemCards(items = accounts)

    val groups by viewModel.listOfGroups.collectAsState()
    val account = viewModel.selectedItem.value
    val selectedGroup = viewModel.selectedGroup.collectAsState()

    if(viewModel.aCardIsTaped.value)
        ItemEditDialog(account, selectedGroup, groups, { viewModel.aCardIsTaped() }) { account ->
            if(account.uid == 0L) viewModel.insert(account)
            else viewModel.update(account)
        }
}

@Composable
private fun ItemEditDialog(
    account: Account,
    selectedGroup: State<Group>,
    groups: List<Group>,
    aCardIsTaped: () -> Unit,
    saveOrUpdate: (Account) -> Unit
) {
    Dialog(onDismissRequest = aCardIsTaped ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 10.dp)
        ) {
            Column(modifier = Modifier.padding(all = 16.dp)) {
                val list = mutableListOf("No Group")
                groups.forEach { group ->
                    list.add(group.name)
                }
                var name by remember { mutableStateOf(account.name) }
                var company by remember { mutableStateOf(account.company) }
                var number by remember { mutableStateOf(account.number) }
                var idGroup = account.idGroup
                val preSelected =
                    if(idGroup == -1L) "No Group"
                    else selectedGroup.value.name

                val saveItem = {
                    account.name = name
                    account.company = company
                    account.number = number
                    account.idGroup = idGroup
                    saveOrUpdate(account)
                    aCardIsTaped()
                }

                Text("Account Name")
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
                Text("Account Number")
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = number,
                    onValueChange = {number = it})
                Text("Group")
                Spinner(
                    list = list,
                    preselected = preSelected,
                    onSelectionChanged = { selected ->
                        idGroup = if(selected == "No Group") -1 else groups[list.indexOf(selected) - 1].uid
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
                        onClick = aCardIsTaped
                    ) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}

@Composable
private fun ShowItemCards(
    items: List<Account>,
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
                    val account by remember { mutableStateOf(item) }
                    val index by remember { mutableIntStateOf(items.indexOf(item)) }
                    val checkMode = remember { mutableStateOf(false) }

                    ItemCard(index = index, item = account, checkMode = checkMode)
                }
            }
        }
    }
}

@Composable
fun ItemCard(
    index: Int,
    item: Account,
    checkMode: MutableState<Boolean>,
    viewModel: SetAccountScreenViewModel = viewModel()
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