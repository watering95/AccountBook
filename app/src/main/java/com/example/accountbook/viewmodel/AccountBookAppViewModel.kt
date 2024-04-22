package com.example.accountbook.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.accountbook.data.Account
import com.example.accountbook.repository.AccountBookRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AccountBookAppViewModel(private val repository: AccountBookRepositoryImpl): ViewModel() {
    private val _title = mutableStateOf("자산")
    val title : State<String> = _title
    val listOfAccounts: MutableStateFlow<List<Account>> = MutableStateFlow(emptyList())

    fun changeTitle(title: String) {
        _title.value = title
    }

    init {
        getAllAccounts()
    }

    private fun getAllAccounts() {
        viewModelScope.launch {
            repository.allAccounts.collectLatest {
                listOfAccounts.value = it
            }
        }
    }
}


