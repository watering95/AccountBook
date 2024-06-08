package com.example.accountbook.screen.homescreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.accountbook.data.Account
import com.example.accountbook.data.Group
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class HomeScreenViewModel(private val repository: HomeScreenRepositoryImpl) : ViewModel() {
    val listOfGroups: MutableStateFlow<List<Group>> = MutableStateFlow(emptyList())
    val listOfAccounts: MutableStateFlow<List<Account>> = MutableStateFlow(emptyList())

    init {
        getAllGroups()
        getAllAccounts()
    }

    private fun getAllGroups() {
        viewModelScope.launch {
            repository.allGroups.collectLatest {
                listOfGroups.value = it
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
}