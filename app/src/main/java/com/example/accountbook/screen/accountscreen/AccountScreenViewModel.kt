package com.example.accountbook.screen.accountscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.accountbook.data.AccountLog
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AccountScreenViewModel(private val repository: AccountScreenRepositoryImpl): ViewModel() {

    private var _items: MutableStateFlow<MutableList<AccountLog>> = MutableStateFlow(mutableListOf())
    val items = _items

    private fun getAllItems() {
        viewModelScope.launch {
            repository.allAccountLogs.collectLatest { accountLogs ->
                _items.value = accountLogs.toMutableList()
            }
        }
    }
}