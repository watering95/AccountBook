package com.example.accountbook.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.accountbook.data.Group
import com.example.accountbook.repository.GroupRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SetGroupScreenViewModel(private val repository: GroupRepositoryImpl) : ViewModel() {
    private val _title = mutableStateOf("Set Group")
    private val _initItem = Group(name = "")

    private var _listOfItem: MutableStateFlow<List<Group>> = MutableStateFlow(emptyList())
    private val _checkedCards: MutableStateFlow<MutableList<Boolean>> = MutableStateFlow(mutableListOf())
    private var _aCardIsLongPressed = mutableStateOf(false)
    private var _aCardIsTaped = mutableStateOf(false)
    private var _selectedItem = mutableStateOf(_initItem)

    val title : State<String> = _title
    val listOfItems = _listOfItem
    val checkedCards = _checkedCards
    val aCardIsLongPressed = _aCardIsLongPressed
    val aCardIsTaped = _aCardIsTaped
    val selectedItem = _selectedItem

    init {
        getAllItems()
    }

    private fun getAllItems() {
        viewModelScope.launch {
            repository.allGroups.collectLatest {
                _listOfItem.value = it
                _checkedCards.value = MutableList(it.size) {false}
            }
        }
    }
    fun aCardIsLongPressed() {
        _checkedCards.value = MutableList(_listOfItem.value.size) {false}
        _aCardIsLongPressed.value = !_aCardIsLongPressed.value
    }

    fun aCardIsTaped(group: Group = _selectedItem.value) {
        _selectedItem.value = group
        (!_aCardIsTaped.value).also { _aCardIsTaped.value = it }
    }

    fun checkedACard(index: Int, checked: Boolean) {
        _checkedCards.value[index] = checked
    }

    fun insert(group: Group) = viewModelScope.launch {
        repository.insert(group)
    }

    fun update(group: Group) = viewModelScope.launch {
        repository.update(group)
    }

    fun delete(group: Group) = viewModelScope.launch {
        repository.delete(group)
    }
    fun delete(array: Array<Group>) = viewModelScope.launch {
        repository.delete(array)
    }
}