package com.example.accountbook.componant

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun Spinner(
    list: List<String>,
    preselected: String,
    onSelectionChanged: (item: String) -> Unit,
) {
    var selected by remember { mutableStateOf(preselected) }
    var expanded by remember { mutableStateOf(false) }

    Box {
        Column {
            OutlinedTextField(value = selected, onValueChange = {})
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
            modifier = Modifier.matchParentSize()
                .background(Color.Transparent)
                .padding(10.dp)
                .clickable(
                    onClick = { expanded = !expanded }
                )
        )
    }
}
