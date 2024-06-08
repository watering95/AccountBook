package com.example.accountbook.screen.accountsetscreen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.ui.componant.Spinner
import com.example.accountbook.data.Account
import com.example.accountbook.data.Group
import com.example.accountbook.navigation.AppScreen
import com.example.accountbook.screen.SetScreenComposable
import com.example.accountbook.viewmodel.Item
import com.example.accountbook.viewmodel.SetScreenViewModel

@Composable
fun AccountSetScreenComposable(navController: NavHostController, db: AppRoomDatabase) {
    val accountSetScreenRepository = AccountSetScreenRepositoryImpl(db)
    val title = AppScreen.SetScreen.AccountSet.title
    val accountContentsComposable = @Composable { item: Item<Account> ->
        val accountItem = item.item
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f),
                    text = accountItem.name
                )
            }
        }
    }

    SetScreenComposable(navController, accountSetScreenRepository, title, accountContentsComposable) { item: Item<*>, viewModel: SetScreenViewModel<Account> ->

    }
}
@Composable
fun ItemEditDialogComposable(
    account: Account,
    onDismissRequest: () -> Unit,
    saveOrUpdate: (Account) -> Unit,
    viewModel: AccountSetScreenViewModel = viewModel()
) {
    val groups = viewModel.groups.collectAsState()

    Dialog(onDismissRequest = { onDismissRequest() }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 10.dp)
        ) {
            Column(modifier = Modifier.padding(all = 16.dp)) {
                val list = mutableListOf("No Group")
                groups.value.forEach { group ->
                    list.add(group.name)
                }
                var name by remember { mutableStateOf(account.name) }
                var company by remember { mutableStateOf(account.company) }
                var number by remember { mutableStateOf(account.number) }
                var idGroup = account.idGroup
                val preSelected =
                    if(idGroup == -1L) "No Group"
                    else TODO()

                val saveItem = {
                    account.name = name
                    account.company = company
                    account.number = number
                    account.idGroup = idGroup
                    saveOrUpdate(account)
                    onDismissRequest()
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
                        idGroup = if(selected == "No Group") -1 else groups.value[list.indexOf(selected) - 1].uid
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
                        onClick = { onDismissRequest() }
                    ) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}

@Composable
fun AccountItemCardComposable(
    item: Item<Account>,
    onTap: (Item<Account>) -> Unit,
    onLongPress: (Item<Account>) -> Unit
) {
    val backgroundColor = if(item.selected) Color.Blue else Color.Yellow

    // A surface container using the 'background' color from the theme
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        Card(
            modifier = Modifier
                .padding(4.dp)
                .height(64.dp)
                .background(color = backgroundColor)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onTap = { onTap(item) },
                        onLongPress = { onLongPress(item) }
                    )
                },
        ) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        modifier = Modifier
                            .padding(16.dp)
                            .weight(1f),
                        text = item.item.name
                    )
                }
            }
        }

    }
}

@Preview(showBackground = true)
@Composable
fun AccountSetScreenPreview() {
//    ItemCardComposable()
}