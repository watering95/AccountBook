package com.example.accountbook.repository

import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.data.Account
import com.example.accountbook.data.Group
import kotlinx.coroutines.flow.Flow

interface HomeRepository {
    val allAccounts: Flow<List<Account>>
    val allGroups: Flow<List<Group>>
}

class HomeRepositoryImpl(private val db : AppRoomDatabase): HomeRepository {
    override val allAccounts: Flow<List<Account>> = db.accountDao().getAll()
    override val allGroups: Flow<List<Group>> = db.groupDao().getAll()
}