package com.example.accountbook.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.accountbook.data.Group
import com.example.accountbook.viewmodel.SetGroupScreenViewModel
import kotlinx.coroutines.launch

@Composable
fun SetGroupScreen() {
    val viewModel = viewModel<SetGroupScreenViewModel>()

    Box(
        modifier = Modifier
            .background(MaterialTheme.colors.primary)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 256.dp)
        ) {
            items(viewModel.listGroup) { item ->
                val index = viewModel.listGroup.indexOf(item)
                GroupCard(
                    item = item,
                ) {
                    viewModel.listGroup[index] = Pair(item.first, !item.second)
                }
            }
        }
    }
}

@Composable
fun GroupCard(item: Pair<Group, Boolean>, onCheckedChange: (Boolean) -> Unit) {
    val viewModel = viewModel<SetGroupScreenViewModel>()
    val context = LocalContext.current
    Card(
        Modifier
            .border(width = 1.dp, color = Color.Black)
            .height(128.dp)
            .pointerInput(Unit) {
                detectTapGestures(
                    onTap = {
                        Toast.makeText(context, item.first.name, Toast.LENGTH_SHORT).show()
                    },
                    onLongPress = { viewModel.changeSelectMode() }
                )
            }
    ) {
        Row {
            Text(item.first.name)
            if(viewModel.selectMode.value) Checkbox(checked = item.second, onCheckedChange = onCheckedChange)
        }
    }
}