package com.example.accountbook.repository

import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.data.Account
import kotlinx.coroutines.flow.Flow

interface AccountBookAppRepository {
    val allAccounts: Flow<List<Account>>
}

class AccountBookAppRepositoryImpl(db: AppRoomDatabase) : AccountBookAppRepository {
    override val allAccounts: Flow<List<Account>> = db.accountDao().getAll()
}