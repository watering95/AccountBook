package com.example.accountbook.viewmodel

import android.widget.Toolbar
import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.accountbook.componant.MainTopBar

class MainViewModel : ViewModel() {
    private val _title = mutableStateOf("자산")
    val title : State<String> = _title

    fun changeTitle(title: String) {
        _title.value = title
    }
}