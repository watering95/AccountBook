package com.example.accountbook.repository

import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.data.Account
import kotlinx.coroutines.flow.Flow

interface AccountBookRepository {
    val allAccounts: Flow<List<Account>>
}

class AccountBookRepositoryImpl(db: AppRoomDatabase) : AccountBookRepository {
    override val allAccounts: Flow<List<Account>> = db.accountDao().getAll()
}