package com.example.accountbook.viewmodel

import androidx.compose.runtime.*
import androidx.lifecycle.*
import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.data.Account
import com.example.accountbook.data.Group
import com.example.accountbook.repository.AccountRepositoryImpl
import com.example.accountbook.repository.GroupRepositoryImpl
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SetAccountScreenViewModel(private val repository: AccountRepositoryImpl) : ViewModel() {
    private val _title = mutableStateOf("Set Account")

    val title : State<String> = _title

    init {

    }

    fun changeTitle(title: String) {
        _title.value = title
    }


    fun insert(account: Account) = viewModelScope.launch {
        repository.insert(account)
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