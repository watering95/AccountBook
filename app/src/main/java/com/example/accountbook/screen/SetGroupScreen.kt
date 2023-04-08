package com.example.accountbook.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
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
import com.example.accountbook.viewmodel.SetGroupScreenViewModel
import com.example.accountbook.viewmodel.SetGroupScreenViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@Composable
fun SetGroupScreen(
    db: AppRoomDatabase,
    screenValue: ScreenValue,
    viewModel: SetGroupScreenViewModel = viewModel(
        factory = SetGroupScreenViewModelFactory(db)
    )
) {
    val groups = viewModel.listOfGroup.collectAsState(initial = emptyList())

    SetGroupScreen(screenValue = screenValue, groups = groups.value)
}

@Composable
fun SetGroupScreen(
    screenValue: ScreenValue,
    groups: List<Group>,
    viewModel: SetGroupScreenViewModel = viewModel()
) {
    val navController = screenValue.navController
    val scaffoldState = screenValue.scaffoldState
    val coroutineScope = screenValue.coroutineScope
    val openDrawer = screenValue.openDrawer
    val addGroup = {
        viewModel.clearSelectedGroup()
        viewModel.aCardIsTaped()
    }
    val deleteGroups: ()-> Unit = {
        var selected = arrayOf<Group>()
        val iterator = viewModel.checkedCards.value.iterator()
        var index = 0
        while(iterator.hasNext()) {
            if(iterator.next()) {
                val new = selected.plus(groups[index])
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
                    onButtonDeleteClicked = deleteGroups
                )
            else
                TopBarWithAdd(
                    title = viewModel.title.value,
                    onButtonNavigationClicked = openDrawer,
                    onButtonAddClicked =  addGroup
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
            ShowGroups()
            if(viewModel.aCardIsTaped.value) EditGroupCardDialog()
        }
    }
}

@Composable
fun ShowGroups(
    viewModel: SetGroupScreenViewModel = viewModel()
) {
    val groups = viewModel.listOfGroup.collectAsState(initial = emptyList())

    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 256.dp)
    ) {
        items(groups.value) { item ->
            val group = remember { mutableStateOf(item) }
            val index = remember { mutableStateOf(groups.value.indexOf(item)) }
            val checkMode = remember {mutableStateOf(false)}

            GroupCard(index = index.value, item = group.value, checkMode = checkMode)
        }
    }
}

@Composable
fun GroupCard(
    index: Int,
    item: Group,
    checkMode: MutableState<Boolean>,
    viewModel: SetGroupScreenViewModel = viewModel()
) {
    Card(
        Modifier
            .border(width = 1.dp, color = Color.Black)
            .height(128.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = { viewModel.aCardIsTaped(item) },
                    onLongPress = { viewModel.aCardIsLongPressed() }
                )
            }
    ) {
        Row {
            Text(item.name)
            if(viewModel.aCardIsLongPressed.value) Checkbox(
                checked = checkMode.value,
                onCheckedChange = {
                    checkMode.value = it
                    viewModel.checkedACard(index, it)
                }
            ) else checkMode.value = false
        }
    }
}
@Composable
fun EditGroupCardDialog(
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
                val group = viewModel.selectedGroup.value
                var text by remember {mutableStateOf(group.name)}
                val addGroup = {
                    group.use = true
                    group.name = text
                    if(group.uid == 0) viewModel.insert(group) else viewModel.update(group)
                    viewModel.aCardIsTaped()
                }

                Text("Group Name")
                TextField(value = text, onValueChange = {text = it})
                Row {
                    Button(onClick = addGroup) {
                        Text("Save")
                    }
                    Button(onClick = { viewModel.aCardIsTaped() }) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}