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
import com.example.accountbook.data.Group
import com.example.accountbook.viewmodel.SetGroupScreenViewModel
import com.example.accountbook.viewmodel.SetGroupScreenViewModelFactory

@Composable
fun SetGroupScreen(
    db: AppRoomDatabase,
    viewModel: SetGroupScreenViewModel = viewModel(
        factory = SetGroupScreenViewModelFactory(db)
    )
) {
    val groups by viewModel.listOfGroup.collectAsState(initial = emptyList())
    val checked by viewModel.checked.collectAsState(initial = emptyList())

    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 256.dp)
        ) {
            items(groups) { item ->
                Log.d("Screen2", "$item")
                val index = groups.indexOf(item)

                GroupCard(
                    item = item,
                    checked = checked[index],
                ) {

                }
            }
        }
        if(viewModel.aCardIsTaped.value) EditGroupCardDialog { viewModel.aCardIsTaped() }
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
                    onTap = { viewModel.aCardIsTaped() },
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
fun EditGroupCardDialog(onDismissRequest: () -> Unit) {
    Dialog(onDismissRequest = onDismissRequest) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 10.dp)
        ) {
            Column(modifier = Modifier.padding(all = 16.dp)) {
                Text("Group Name")
                TextField(value = "", onValueChange = {})
                Row {
                    Button( onClick = { /*TODO*/ }) {
                        Text("Save")
                    }
                    Button(onClick = { /*TODO*/ }) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}