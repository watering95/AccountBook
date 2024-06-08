package com.example.accountbook.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.repository.AccountBookAppRepositoryImpl
import com.example.accountbook.screen.accountscreen.AccountScreenRepositoryImpl
import com.example.accountbook.screen.homescreen.HomeScreenRepositoryImpl
import com.example.accountbook.screen.settingscreen.SettingScreenRepositoryImpl
import com.example.accountbook.screen.accountscreen.AccountScreenViewModel
import com.example.accountbook.screen.homescreen.HomeScreenViewModel
import com.example.accountbook.screen.settingscreen.SettingScreenViewModel


class AccountBookAppViewModelFactory (private val db: AppRoomDatabase) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if(modelClass.isAssignableFrom(AccountBookAppViewModel::class.java)) {
            val repository = AccountBookAppRepositoryImpl(db)
            AccountBookAppViewModel(repository) as T
        } else if(modelClass.isAssignableFrom(AccountScreenViewModel::class.java)) {
            val repository = AccountScreenRepositoryImpl(db)
            AccountScreenViewModel(repository) as T
        } else if(modelClass.isAssignableFrom(HomeScreenViewModel::class.java)) {
            val repository = HomeScreenRepositoryImpl(db)
            HomeScreenViewModel(repository) as T
        } else if(modelClass.isAssignableFrom(SettingScreenViewModel::class.java)) {
            val repository = SettingScreenRepositoryImpl(db)
            SettingScreenViewModel(repository) as T
        } else {
            throw IllegalArgumentException("Failed to create ViewModel : ${modelClass.name}")
        }
    }
}