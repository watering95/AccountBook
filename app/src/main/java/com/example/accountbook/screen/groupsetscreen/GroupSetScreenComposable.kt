package com.example.accountbook.screen.groupsetscreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.data.Group
import com.example.accountbook.navigation.AppScreen
import com.example.accountbook.screen.SetScreenComposable
import com.example.accountbook.viewmodel.Item
import com.example.accountbook.viewmodel.SetScreenViewModel

@Composable
fun GroupSetScreenComposable(
    navController: NavHostController,
    db: AppRoomDatabase,
    ) {
    val groupSetScreenRepository = GroupSetScreenRepositoryImpl(db)
    val title = AppScreen.SetScreen.GroupSet.title

    val groupContentsComposable = @Composable { item: Item<Group> ->
        val groupItem = item.item
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    modifier = Modifier
                        .padding(16.dp)
                        .weight(1f),
                    text = groupItem.name
                )
            }
        }
    }

    SetScreenComposable(
        navController,
        groupSetScreenRepository,
        title,
        groupContentsComposable
    ) { item: Item<Group>, viewModel: SetScreenViewModel<Group> ->
        val group = item.item
        val saveItem = {
            viewModel.insert(group)
            viewModel.closeEditItemDialog()
        }

        GroupItemEditDialogComposable(group, saveItem) { viewModel.popupEditItemDialog(Any() as Item<Group>) }
    }
}

// 선택된 아이템 정보 편집 화면
@Composable
private fun GroupItemEditDialogComposable(
    group: Group,
    saveItem: () -> Unit,
    popupEditItemDialog: () -> Unit
) {
    Dialog(onDismissRequest = { popupEditItemDialog() }) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 10.dp)
        ) {
            Column(modifier = Modifier.padding(all = 16.dp)) {
                var name by remember {mutableStateOf(group.name)}
                 Text("Group Name")
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(),
                    value = name,
                    onValueChange = {name = it}
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
                        onClick = { popupEditItemDialog() }
                    ) {
                        Text("Cancel")
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GroupSetScreenPreview() {
//    GroupItemEditDialogComposable()
}