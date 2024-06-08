package com.example.accountbook.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.*
import com.example.accountbook.repository.Repository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

data class Item<T>(val id: Int, val item: T, val selected: Boolean)
class SetScreenViewModel<T>(private val repository: Repository<T>) : ViewModel() {
    private var _items: MutableStateFlow<MutableList<Item<T>>> = MutableStateFlow(mutableListOf())
    private var _any: T = Any() as T
    private var _initItem = Item(0, _any, false)
    private var _isPopupItemEditDialog = mutableStateOf(false)
    private var _selectedItem = MutableStateFlow(_initItem)

    val items = _items
    val selectedItem = _selectedItem
    val isPopupItemEditDialog = _isPopupItemEditDialog

    init {
        getItems()
    }

    private fun getItems() = viewModelScope.launch {
        repository.getAll().collectLatest { items ->
            _items.value = items.mapIndexed { index, item ->
                Item(index+1, item, false)
            }.toMutableList()
        }
    }

    fun popupEditItemDialog(item: Item<T>) {
        _selectedItem.value = item
        _isPopupItemEditDialog.value = true
    }

    fun closeEditItemDialog() {
        _isPopupItemEditDialog.value = false
    }

    fun insert(t: T) = viewModelScope.launch {
        repository.insert(t)
    }
    fun update(t: T) = viewModelScope.launch {
        repository.update(t)
    }
    fun delete(t: T) = viewModelScope.launch {
        repository.delete(t)
    }
    fun delete(array: Array<T>) = viewModelScope.launch {
        repository.delete(array)
    }
}

