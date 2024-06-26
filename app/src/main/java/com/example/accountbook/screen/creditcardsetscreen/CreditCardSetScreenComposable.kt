package com.example.accountbook.screen.creditcardsetscreen

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
import androidx.navigation.NavHostController
import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.ui.componant.Spinner
import com.example.accountbook.data.CreditCard
import com.example.accountbook.data.Group
import com.example.accountbook.navigation.AppScreen
import com.example.accountbook.screen.SetScreenComposable
import com.example.accountbook.viewmodel.Item
import com.example.accountbook.viewmodel.SetScreenViewModel

@Composable
fun CreditCardSetScreenComposable(navController: NavHostController, db: AppRoomDatabase) {
    val creditCardSetScreenRepository = CreditCardSetScreenRepositoryImpl(db)
    val title = AppScreen.SetScreen.CreditCardSet.title
    val creditCardContentsComposable = @Composable { item: Item<CreditCard> ->
        val creditCardItem = item.item
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f),
                    text = creditCardItem.name
                )
            }
        }
    }

    SetScreenComposable(navController, creditCardSetScreenRepository, title, creditCardContentsComposable) { item: Item<*>, viewModel: SetScreenViewModel<CreditCard> ->

    }
}

@Composable
fun ItemScreen(
    items: List<CreditCard>,
    viewModel: CreditCardSetScreenViewModel = viewModel()
) {
    val addItem = {
        viewModel.clearSelectedCards()
        viewModel.aCardIsTaped()
    }
    val deleteItems: ()-> Unit = {
        var selected = arrayOf<CreditCard>()
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
}

@Composable
private fun ShowAllItemsComposable(
    items: List<CreditCard>
) {

        // A surface container using the 'background' color from the theme
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            LazyVerticalGrid(
                modifier = Modifier.padding(4.dp),
                columns = GridCells.Adaptive(minSize = 256.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(items) { item ->
                    val creditCard by remember { mutableStateOf(item) }
                    val index by remember { mutableIntStateOf(items.indexOf(item)) }
                    val checkMode = remember { mutableStateOf(false) }

                    ItemCardComposable(index = index, item = creditCard, checkMode = checkMode)
                }
            }
        }

}

@Composable
fun ItemCardComposable(
    index: Int,
    item: CreditCard,
    checkMode: MutableState<Boolean>,
    viewModel: CreditCardSetScreenViewModel = viewModel()
) {

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

@Composable
private fun ItemEditDialogComposable(
    viewModel: CreditCardSetScreenViewModel = viewModel()
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
                val creditCard = viewModel.selectedItem.value
                val selectedAccount = viewModel.selectedAccount.collectAsState()
                val list = mutableListOf<String>()
                accounts.forEach { account ->
                    list.add(account.name)
                }
                var name by remember { mutableStateOf(creditCard.name) }
                var company by remember { mutableStateOf(creditCard.company) }
                var number by remember { mutableStateOf(creditCard.number) }
                var idAccount = creditCard.idAccount
                val preSelected =
                        if(idAccount == -1L) list[0]
                        else selectedAccount.value.name
                val saveItem = {
                    creditCard.name = name
                    creditCard.company = company
                    creditCard.number = number
                    creditCard.idAccount = idAccount
                    if(creditCard.uid == 0L) viewModel.insert(creditCard) else viewModel.update(creditCard)
                    viewModel.aCardIsTaped()
                }

                Text("CreditCard Name")
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
                Text("CreditCard Number")
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
