package com.example.accountbook.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.accountbook.ui.componant.CommonScaffold
import com.example.accountbook.ui.componant.TopBar
import com.example.accountbook.repository.Repository
import com.example.accountbook.viewmodel.Item
import com.example.accountbook.viewmodel.SetScreenViewModel
import com.example.accountbook.viewmodel.SetScreenViewModelFactory

typealias ClickHandler<T> = (Item<T>) -> Unit
typealias SelectHandler<T> = (Item<T>) -> Unit

@Composable
fun <T : Any>SetScreenComposable(
    navController: NavHostController,
    repository: Repository<T>,
    title: String,
    itemContentsComposable: @Composable (Item<T>)->Unit,
    viewModel: SetScreenViewModel<T> = viewModel(factory = SetScreenViewModelFactory(repository)),
    itemEditDialogComposable: @Composable (Item<T>, SetScreenViewModel<T>) -> Unit
) {
    val items by viewModel.items.collectAsState()
    val isPopupItemEditDialog by viewModel.isPopupItemEditDialog
    val topBar = @Composable { TopBar(title = title, onButtonNavigationClicked = {navController.popBackStack()}) }
    val onTap = { item: Item<T> ->
        when(isPopupItemEditDialog) {
            false -> viewModel.popupEditItemDialog(item)
            true -> viewModel.closeEditItemDialog()
        }
    }
    val onLongPress = {item: Item<T> -> }

    CommonScaffold(topBar, navController) {
        ShowAllItemsComposable(items, onTap, onLongPress, itemContentsComposable)

        if(isPopupItemEditDialog) {
            val selectedItem by viewModel.selectedItem.collectAsState()
            itemEditDialogComposable(selectedItem, viewModel)
//            ItemEditDialogComposable(item, {viewModel.closeEditItemDialog()}) { account ->
//                if (it.uid == 0L) viewModel.insert(it)
//                else viewModel.update(it)
        }
    }
}

@Composable
private fun <T>ShowAllItemsComposable(
    items: MutableList<Item<T>>,
    onTap: (Item<T>) -> Unit,
    onLongPress: SelectHandler<T>,
    itemContentsComposable: @Composable (Item<T>) -> Unit
) {
    // A surface container using the 'background' color from the theme
    Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
        LazyVerticalGrid(
            modifier = Modifier.padding(4.dp),
            columns = GridCells.Adaptive(minSize = 256.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            items(
                items = items,
                key = { item -> item.id },
            ) { item ->
                ItemCardComposable(item, onTap, onLongPress, itemContentsComposable)
            }
        }
    }
}

@Composable
private fun <T>ItemCardComposable(
    item: Item<T>,
    onTap : (Item<T>) -> Unit,
    onLongPress : SelectHandler<T>,
    itemContentsComposable: @Composable (Item<T>)->Unit
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
            itemContentsComposable(item)
        }
    }
}