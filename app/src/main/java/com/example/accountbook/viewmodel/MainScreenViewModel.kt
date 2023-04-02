package com.example.accountbook.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class MainScreenViewModel : ViewModel() {
    private val _title = mutableStateOf("자산")
    val title : State<String> = _title

    fun changeTitle(title: String) {
        _title.value = title
    }
}