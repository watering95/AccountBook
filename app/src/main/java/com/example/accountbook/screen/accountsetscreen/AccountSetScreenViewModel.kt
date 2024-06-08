package com.example.accountbook.screen.accountsetscreen

import androidx.lifecycle.*
import com.example.accountbook.data.Account
import com.example.accountbook.data.Group
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

data class ItemAccount(val id: Int, val account: Account, val selected: Boolean)
class AccountSetScreenViewModel(private val repository: AccountSetScreenRepositoryImpl) : ViewModel() {
    private val _initAccount = Account(name="", company = "", number = "", use = false, idGroup = -1L, balance = 0.0)
    private val _initGroup = Group(name="")

    private val _initItem = ItemAccount(0, _initAccount, false)

    private var _items: MutableStateFlow<MutableList<ItemAccount>> = MutableStateFlow(mutableListOf())
    private val _selectedItem: MutableStateFlow<ItemAccount> = MutableStateFlow(_initItem)
    private val _selectedGroup: MutableStateFlow<Group> = MutableStateFlow(_initGroup)
    private var _isOneItemSelected = false

    val items = _items
    val selectedItem = _selectedItem
    val selectedGroup = _selectedGroup
    val isOneItemSelected = _isOneItemSelected

    val groups: MutableStateFlow<List<Group>> = MutableStateFlow(emptyList())

    init {
        getAllItems()
        getAllGroups()
    }

    fun popupEditItemDialog(item: ItemAccount) {
        _selectedItem.value = item
        _isOneItemSelected = true
    }

    fun closeEditItemDialog() {
        _isOneItemSelected = false
    }

    private fun getAllItems() {
        viewModelScope.launch {
            repository.allAccounts.collectLatest { accounts ->
                _items.value = accounts.mapIndexed { index, account ->
                    ItemAccount(index + 1, account, false)
                }.toMutableList()
            }
        }
    }
    private fun getAllGroups() {
        viewModelScope.launch {
            repository.allGroups.collectLatest {
                groups.value = it
            }
        }
    }

    private fun getGroup(id: Long) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                _selectedGroup.value = repository.getGroup(id)
            }
        }
    }

    fun toggleSelection(index: Int) {
        val currentList = _items.value
        val item = currentList[index]
        _items.value[index] = item.copy(selected = !item.selected)
    }

    fun insert(account: Account) = viewModelScope.launch {
        repository.insert(account)
    }
    fun update(account: Account) = viewModelScope.launch {
        repository.update(account)
    }
    fun delete(account: Account) = viewModelScope.launch {
        repository.delete(account)
    }
    fun delete(array: Array<Account>) = viewModelScope.launch {
        repository.delete(array)
    }
}