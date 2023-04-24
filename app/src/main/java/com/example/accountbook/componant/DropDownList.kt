package com.example.accountbook.componant

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

@Composable
fun Spinner(
    list: List<String>,
    preselected: String,
    onSelectionChanged: (item: String) -> Unit,
) {
    var selected by remember { mutableStateOf(preselected) }
    var expanded by remember { mutableStateOf(false) }
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp

    Box {
        Column {
            OutlinedTextField(
                value = selected,
                modifier = Modifier
                    .width(screenWidth.dp / 2)
                    .height(60.dp),
                onValueChange = {})
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                list.forEach { entry ->
                    DropdownMenuItem(
                        modifier = Modifier.fillMaxWidth(),
                        onClick = {
                            selected = entry
                            expanded = false
                            onSelectionChanged(selected)
                        }
                    ) {
                        Text(
                            text = entry
                        )
                    }
                }
            }
        }
        Spacer(
            modifier = Modifier
                .matchParentSize()
                .background(Color.Transparent)
                .padding(10.dp)
                .clickable(
                    onClick = { expanded = !expanded }
                )
        )
    }
}
