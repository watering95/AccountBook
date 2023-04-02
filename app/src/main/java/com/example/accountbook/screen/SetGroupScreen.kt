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
import com.example.accountbook.componant.SettingTopBar
import com.example.accountbook.data.Group
import com.example.accountbook.drawerBodies
import com.example.accountbook.drawerHeads
import com.example.accountbook.viewmodel.SetGroupScreenViewModel
import com.example.accountbook.viewmodel.SetGroupScreenViewModelFactory
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SetGroupScreen(
    db: AppRoomDatabase,
    screenValue: ScreenValue,
    viewModel: SetGroupScreenViewModel = viewModel(
        factory = SetGroupScreenViewModelFactory(db)
    )
) {
    val groups by viewModel.listOfGroup.collectAsState(initial = emptyList())
    val checked by viewModel.checked.collectAsState(initial = emptyList())

    SetGroupScreen(screenValue = screenValue, groups = groups, checked = checked)
}

@Composable
fun SetGroupScreen(
    screenValue: ScreenValue,
    groups: List<Group>,
    checked: List<Boolean>,
    viewModel: SetGroupScreenViewModel = viewModel()
) {
    val navController = screenValue.navController
    val scaffoldState = screenValue.scaffoldState
    val coroutineScope = screenValue.coroutineScope
    val openDrawer = screenValue.openDrawer
    val addGroup = { viewModel.aCardIsTaped() }

    Scaffold (
        scaffoldState = scaffoldState,
        topBar = { SettingTopBar(
            title = viewModel.title.value,
            onButtonNavigationClicked = openDrawer,
            onButtonAddClicked =  addGroup
        ) },
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
            LazyVerticalGrid(
                columns = GridCells.Adaptive(minSize = 256.dp)
            ) {
                items(groups) { item ->
                    val index = groups.indexOf(item)

                    GroupCard(
                        item = item,
                        checked = checked[index],
                    ) {

                    }
                }
            }
            if(viewModel.aCardIsTaped.value) EditGroupCardDialog()
        }
    }
}

@Composable
fun GroupCard(
    item: Group,
    checked: Boolean?,
    viewModel: SetGroupScreenViewModel = viewModel(),
    onCheckedChangeACheckBox: (Boolean) -> Unit) {

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
            if(viewModel.aCardIsLongPressed.value) checked?.let { Checkbox(checked = it, onCheckedChange = onCheckedChangeACheckBox) }
        }
    }
}
@Composable
fun EditGroupCardDialog(
    viewModel: SetGroupScreenViewModel = viewModel(),
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
                Log.d("Test","${group.uid}")
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