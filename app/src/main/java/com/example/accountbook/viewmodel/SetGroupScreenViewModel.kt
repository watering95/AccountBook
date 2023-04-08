package com.example.accountbook.viewmodel

import android.util.Log
import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.data.Group
import com.example.accountbook.repository.GroupRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SetGroupScreenViewModel(private val repository: GroupRepositoryImpl) : ViewModel() {
    private val _title = mutableStateOf("Set Group")
    private var _listOfGroup: MutableStateFlow<List<Group>> = MutableStateFlow(emptyList())
    private val _checkedCards: MutableStateFlow<MutableList<Boolean>> = MutableStateFlow(mutableListOf())
    private val _aCardIsLongPressed = mutableStateOf(false)
    private val _aCardIsTaped = mutableStateOf(false)
    private val _selectedGroup = mutableStateOf(Group(name = "", use = false))

    val title : State<String> = _title
    val listOfGroup = _listOfGroup
    val checkedCards = _checkedCards
    val aCardIsLongPressed = _aCardIsLongPressed
    val aCardIsTaped = _aCardIsTaped
    val selectedGroup = _selectedGroup

    init {
        getAllGroups()
    }

    fun getAllGroups() {
        viewModelScope.launch {
            repository.allGroups.collectLatest {
                _listOfGroup.value = it
                _checkedCards.value = MutableList(it.size) {false}
            }
        }
    }
    fun aCardIsLongPressed() {
        _checkedCards.value = MutableList(_listOfGroup.value.size) {false}
        _aCardIsLongPressed.value = !_aCardIsLongPressed.value
    }

    fun aCardIsTaped(group: Group = _selectedGroup.value) {
        _selectedGroup.value = group
        (!_aCardIsTaped.value).also { _aCardIsTaped.value = it }
    }

    fun checkedACard(index: Int, checked: Boolean) {
        _checkedCards.value[index] = checked
    }

    fun clearSelectedGroup() {
        _selectedGroup.value = Group(name = "", use = false)
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

class SetGroupScreenViewModelFactory(private val db: AppRoomDatabase) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SetGroupScreenViewModel::class.java)) {
            val repository = GroupRepositoryImpl(db)
            return SetGroupScreenViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Failed to create ViewModel : ${modelClass.name}")
        }
    }
}