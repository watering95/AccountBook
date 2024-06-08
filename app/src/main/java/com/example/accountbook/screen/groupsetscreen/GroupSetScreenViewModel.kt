package com.example.accountbook.screen.groupsetscreen

import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.accountbook.data.Group
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class GroupSetScreenViewModel(private val repository: GroupSetScreenRepositoryImpl) : ViewModel() {
    private val _title = mutableStateOf("Set Group")
    private val _initItem = Group(name = "")

    private var _listOfItem: MutableStateFlow<List<Group>> = MutableStateFlow(emptyList())
    private val _checkedCards: MutableStateFlow<MutableList<Boolean>> = MutableStateFlow(mutableListOf())
    val title : State<String> = _title

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