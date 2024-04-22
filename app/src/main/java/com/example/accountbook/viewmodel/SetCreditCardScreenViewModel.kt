package com.example.accountbook.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.accountbook.data.Account
import com.example.accountbook.data.CreditCard
import com.example.accountbook.repository.CreditCardRepositoryImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SetCreditCardScreenViewModel(private val repository: CreditCardRepositoryImpl) : ViewModel() {
    private val _title = mutableStateOf("Set Card")
    private val _initItem = CreditCard(name="", company = "", number = "", idAccount = -1, use = false)
    private val _initAccount = Account(name="", company = "", number = "", balance = 0.0, use = false, idGroup = -1)

    private var _listOfItems: MutableStateFlow<List<CreditCard>> = MutableStateFlow(emptyList())
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
            repository.allCreditCards.collectLatest {
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

    private fun getAccount(id: Long) {
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

    fun aCardIsTaped(creditCard: CreditCard = _selectedItem.value) {
        _selectedItem.value = creditCard
        getAccount(creditCard.idAccount)
        (!_aCardIsTaped.value).also { _aCardIsTaped.value = it }
    }

    fun checkedACard(index: Int, checked: Boolean) {
        _checkedCards.value[index] = checked
    }

    fun clearSelectedCards() {
        _selectedItem.value = _initItem
    }

    fun insert(creditCard: CreditCard) = viewModelScope.launch {
        repository.insert(creditCard)
    }
    fun update(creditCard: CreditCard) = viewModelScope.launch {
        repository.update(creditCard)
    }
    fun delete(creditCard: CreditCard) = viewModelScope.launch {
        repository.delete(creditCard)
    }
    fun delete(array: Array<CreditCard>) = viewModelScope.launch {
        repository.delete(array)
    }
}