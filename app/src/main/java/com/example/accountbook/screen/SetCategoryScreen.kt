package com.example.accountbook.screen

import androidx.compose.foundation.background
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
import androidx.compose.runtime.mutableStateListOf
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
import com.example.accountbook.componant.ScreenValue
import com.example.accountbook.componant.Spinner
import com.example.accountbook.data.Category
import com.example.accountbook.ui.theme.CardListTheme
import com.example.accountbook.ui.theme.CardTheme
import com.example.accountbook.viewmodel.AccountBookAppViewModelFactory
import com.example.accountbook.viewmodel.SetCategoryScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun SetCategoryScreen(
) {
    val viewModel: SetCategoryScreenViewModel = viewModel(
        factory = AccountBookAppViewModelFactory(AppRoomDatabase.getInstance(LocalContext.current, rememberCoroutineScope()))
    )
    val categories = viewModel.listOfItems.collectAsState()

    ItemScreen(items = categories.value)
    if(viewModel.aCardIsTaped.value) ShowItemCards(items = categories.value)
}

@Composable
fun ItemScreen(
    items: List<Category>,
    viewModel: SetCategoryScreenViewModel = viewModel()
) {
    val addItem = {
        viewModel.clearSelectedCards()
        viewModel.aCardIsTaped()
    }
    val deleteItems: ()-> Unit = {
        var selected = arrayOf<Category>()
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
private fun ShowItemCards(
    items: List<Category>
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
                    val category = remember { mutableStateOf(item) }
                    val index = remember { mutableIntStateOf(items.indexOf(item)) }
                    val checkMode = remember { mutableStateOf(false) }

                    ItemCard(index = index.intValue, item = category.value, checkMode = checkMode)
                }
            }
        }
    }
}

@Composable
fun ItemCard(
    index: Int,
    item: Category,
    checkMode: MutableState<Boolean>,
    viewModel: SetCategoryScreenViewModel = viewModel()
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

@Composable
private fun ItemEditDialog(
    viewModel: SetCategoryScreenViewModel = viewModel()
) {
    val categoriesDepth0 = viewModel.categoriesDepth0.collectAsState()
    val categoriesDepth1 = viewModel.categoriesDepth1.collectAsState()

    Dialog(onDismissRequest = { viewModel.aCardIsTaped() }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 10.dp)
        ) {
            Column(modifier = Modifier.padding(all = 16.dp)) {
                val category = viewModel.selectedItem.value
                var level0 = remember{ "" }
                var level1 = remember{ "" }

                val list0 = remember { mutableStateListOf("") }
                list0.clear()
                list0.add("")
                categoriesDepth0.value.forEach {
                    if(it.uid != category.uid) list0.add(it.name)
                }
                val list1 = remember { mutableStateListOf("") }
                list1.clear()
                list1.add("")
                categoriesDepth1.value.forEach {
                    if(it.uid != category.uid) list1.add(it.name)
                }

                var name by remember { mutableStateOf(category.name) }
                var idParent = category.pid
                val depth = remember { mutableIntStateOf(category.depth) }  // 0: Level1, 1: Level2, 2: Level3

                val saveItem = {
                    category.name = name
                    category.pid = idParent
                    category.depth = depth.intValue
                    if(category.uid == 0L) viewModel.insert(category) else viewModel.update(category)
                    viewModel.aCardIsTaped()
                }

                Text("Category Name")
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = name,
                    onValueChange = {name = it})

                if(depth.intValue >= 0) {
                    val preSelected0 =
                        if(category.pid == -1L) list0[0]
                        else viewModel.preSelected0.value

                    level0 = preSelected0

                    Text("Category1")
                    Spinner(
                        list = list0,
                        preselected = preSelected0,
                        onSelectionChanged = { selected ->
                            if(selected != "") {
                                val cat = categoriesDepth0.value[list0.indexOf(selected)-1]
                                if(depth.intValue == 0) depth.intValue =  1
                                if(depth.intValue == 1) idParent = cat.uid
                            } else {
                                depth.intValue = 0
                                idParent = -1
                            }
                            level0 = selected
                        }
                    )
                }
                if(depth.intValue >= 1) {
                    val preSelected1 =
                        if(category.pid == -1L) list0[0]
                        else viewModel.preSelected1.value

                    level1 = preSelected1

                    Text("Category2")
                    Spinner(
                        list = list1,
                        preselected = preSelected1,
                        onSelectionChanged = { selected ->
                            if(selected != "") {
                                val cat = categoriesDepth1.value[list1.indexOf(selected)-1]
                                if(depth.intValue == 1) depth.intValue = 2
                                if(depth.intValue == 2) idParent = cat.uid
                            } else {
                                depth.intValue = 1
                                idParent = categoriesDepth0.value[list0.indexOf(level0)-1].uid
                            }
                            level1 = selected
                        }
                    )
                }

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