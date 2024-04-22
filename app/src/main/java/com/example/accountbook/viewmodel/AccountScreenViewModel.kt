package com.example.accountbook.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.accountbook.data.AccountLog
import com.example.accountbook.repository.AccountRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AccountScreenViewModel(private val repository: AccountRepositoryImpl): ViewModel() {
    private var _listOfItems: MutableStateFlow<List<AccountLog>> = MutableStateFlow(emptyList())

    val listOfItems = _listOfItems

    init {
        getAllItems()
    }

    private fun getAllItems() {
        viewModelScope.launch {
            repository.allAccountLog.collectLatest {
                _listOfItems.value = it
            }
        }
    }
}