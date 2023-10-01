package com.example.accountbook.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.data.Account
import com.example.accountbook.data.Card
import com.example.accountbook.repository.CardRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SetCardScreenViewModel(private val repository: CardRepositoryImpl) : ViewModel() {
    private val _title = mutableStateOf("Set Card")
    private val _initItem = Card(name="", company = "", number = "", idAccount = -1, idPayment = -1,use = false)
    private val _initAccount = Account(name="", company = "", number = "", idPayment = -1,use = false, idGroup = -1)

    private var _listOfItems: MutableStateFlow<List<Card>> = MutableStateFlow(emptyList())
    private val _aCardIsLongPressed = mutableStateOf(false)
    private val _aCardIsTaped = mutableStateOf(false)
    private val _checkedCards: MutableStateFlow<MutableList<Boolean>> = MutableStateFlow(mutableListOf())
    private val _selectedItem = mutableStateOf(_initItem)
    private val _selectedAccount: MutableStateFlow<Account> = MutableStateFlow(_initAccount)

    val title : State<String> = _title
    val listOfItems = _listOfItems
    val checkedCards = _checkedCards
    val aCardIsLongPressed = _aCardIsLongPressed
    val aCardIsTaped = _aCardIsTaped
    val selectedItem = _selectedItem
    val selectedAccount = _selectedAccount
    val listOfAccounts: MutableStateFlow<List<Account>> = MutableStateFlow(emptyList())


    init {
        getAllItems()
        getAllAccounts()
    }

    private fun getAllItems() {
        viewModelScope.launch {
            repository.allCards.collectLatest {
                _listOfItems.value = it
                _checkedCards.value = MutableList(it.size) {false}
            }
        }
    }

    private fun getAllAccounts() {
        viewModelScope.launch {
            repository.allAccounts.collectLatest {
                listOfAccounts.value = it
            }
        }
    }

    private fun getAccount(id: Int) {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                _selectedAccount.value = repository.getAccount(id)
            }
        }
    }

    fun aCardIsLongPressed() {
        _checkedCards.value = MutableList(_listOfItems.value.size) {false}
        _aCardIsLongPressed.value = !_aCardIsLongPressed.value
    }

    fun aCardIsTaped(card: Card = _selectedItem.value) {
        _selectedItem.value = card
        getAccount(card.idAccount)
        (!_aCardIsTaped.value).also { _aCardIsTaped.value = it }
    }

    fun checkedACard(index: Int, checked: Boolean) {
        _checkedCards.value[index] = checked
    }

    fun clearSelectedCards() {
        _selectedItem.value = _initItem
    }

    fun insert(card: Card) = viewModelScope.launch {
        repository.insert(card)
    }
    fun update(card: Card) = viewModelScope.launch {
        repository.update(card)
    }
    fun delete(card: Card) = viewModelScope.launch {
        repository.delete(card)
    }
    fun delete(array: Array<Card>) = viewModelScope.launch {
        repository.delete(array)
    }
}

class SetCardScreenViewModelFactory(private val db: AppRoomDatabase) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(SetCardScreenViewModel::class.java)) {
            val repository = CardRepositoryImpl(db)
            return SetCardScreenViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Failed to create ViewModel : ${modelClass.name}")
        }
    }
}