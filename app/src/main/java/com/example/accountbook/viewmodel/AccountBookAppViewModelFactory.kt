package com.example.accountbook.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.repository.AccountBookRepositoryImpl
import com.example.accountbook.repository.AccountRepositoryImpl
import com.example.accountbook.repository.CategoryRepositoryImpl
import com.example.accountbook.repository.CreditCardRepositoryImpl
import com.example.accountbook.repository.GroupRepositoryImpl
import com.example.accountbook.repository.HomeRepositoryImpl

class AccountBookAppViewModelFactory(private val db: AppRoomDatabase) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(AccountBookAppViewModel::class.java)) {
            val repository = AccountBookRepositoryImpl(db)
            AccountBookAppViewModel(repository) as T
        } else if(modelClass.isAssignableFrom(AccountScreenViewModel::class.java)) {
            val repository = AccountRepositoryImpl(db)
            AccountScreenViewModel(repository) as T
        } else if(modelClass.isAssignableFrom(HomeScreenViewModel::class.java)) {
            val repository = HomeRepositoryImpl(db)
            HomeScreenViewModel(repository) as T
        } else if(modelClass.isAssignableFrom(SetAccountScreenViewModel::class.java)) {
            val repository = AccountRepositoryImpl(db)
            SetAccountScreenViewModel(repository) as T
        } else if(modelClass.isAssignableFrom(SetCategoryScreenViewModel::class.java)) {
            val repository = CategoryRepositoryImpl(db)
            SetCategoryScreenViewModel(repository) as T
        }  else if(modelClass.isAssignableFrom(SetCreditCardScreenViewModel::class.java)) {
            val repository = CreditCardRepositoryImpl(db)
            SetCreditCardScreenViewModel(repository) as T
        } else if(modelClass.isAssignableFrom(SetGroupScreenViewModel::class.java)) {
            val repository = GroupRepositoryImpl(db)
            SetGroupScreenViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Failed to create ViewModel : ${modelClass.name}")
        }
    }
}