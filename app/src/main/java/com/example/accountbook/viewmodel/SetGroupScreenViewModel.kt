package com.example.accountbook.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import com.example.accountbook.data.Group

class SetGroupScreenViewModel : ViewModel() {
    private val _listGroup = mutableStateListOf("1" to false, "2" to false, "3" to false)
    private val _listOfGroup = mutableStateListOf<Pair<Group,Boolean>>(Group(1,"Group1",true) to false, Group(2,"Group2",true) to false)
    private val _selectMode = mutableStateOf(false)

    val listGroup = _listOfGroup
    val selectMode = _selectMode

    fun changeSelectMode() {
        _selectMode.value = !_selectMode.value
    }


}