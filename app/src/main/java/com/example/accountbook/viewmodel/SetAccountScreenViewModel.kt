package com.example.accountbook.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.data.Account
import com.example.accountbook.data.Group
import com.example.accountbook.repository.AccountRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SetAccountScreenViewModel(private val repository: AccountRepositoryImpl) : ViewModel() {
    private val _title = mutableStateOf("Set Account")
    private val _initItem = Account(name="", company = "", number = "", idPayment = -1,use = false, idGroup = null)
    private val _initGroup = Group(name="")

    private var _listOfItems: MutableStateFlow<List<Account>> = MutableStateFlow(emptyList())
    private val _aCardIsLongPressed = mutableStateOf(false)
    private val _aCardIsTaped = mutableStateOf(false)
    private val _checkedCards: MutableStateFlow<MutableList<Boolean>> = MutableStateFlow(mutableListOf())
    private val _selectedItem = mutableStateOf(_initItem)
    private val _selectedGroup: MutableStateFlow<Group> = MutableStateFlow(_initGroup)

    val title : State<String> = _title
    val listOfItems = _listOfItems
    val checkedCards = _checkedCards
    val aCardIsLongPressed = _aCardIsLongPressed
    val aCardIsTaped = _aCardIsTaped
    val selectedItem = _selectedItem
    val listOfGroups: MutableStateFlow<List<Group>> = MutableStateFlow(emptyList())


    init {
        getAllItems()
        getAllGroups()
    }

    private fun getAllItems() {
        viewModelScope.launch {
            repository.allAccounts.collectLatest {
                _listOfItems.value = it
                _checkedCards.value = MutableList(it.size) {false}
            }
        }
    }
    private fun getAllGroups() {
        viewModelScope.launch {
            repository.allGroups.collectLatest {
                listOfGroups.value = it
            }
        }
    }

    fun getGroup(id: Int): Group {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                _selectedGroup.value = repository.getGroup(id)
            }
        }
        return _selectedGroup.value
    }

    fun aCardIsLongPressed() {
        _checkedCards.value = MutableList(_listOfItems.value.size) {false}
        _aCardIsLongPressed.value = !_aCardIsLongPressed.value
    }

    fun aCardIsTaped(account: Account = _selectedItem.value) {
        _selectedItem.value = account
        account.idGroup?.let { getGroup(it) }
        (!_aCardIsTaped.value).also { _aCardIsTaped.value = it }
    }

    fun checkedACard(index: Int, checked: Boolean) {
        _checkedCards.value[index] = checked
    }

    fun clearSelectedCards() {
        _selectedItem.value = _initItem
        _selectedGroup.value = _initGroup
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

class SetAccountScreenViewModelFactory(private val db: AppRoomDatabase) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SetAccountScreenViewModel::class.java)) {
            val repository = AccountRepositoryImpl(db)
            return SetAccountScreenViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Failed to create ViewModel : ${modelClass.name}")
        }
    }
}