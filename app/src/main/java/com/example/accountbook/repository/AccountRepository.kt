package com.example.accountbook.repository

import android.content.Context
import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.data.Account
import com.example.accountbook.data.Group
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    val allAccounts: Flow<List<Account>>
    suspend fun insert(account: Account)
}
class AccountRepositoryImpl(private val db : AppRoomDatabase): AccountRepository {
    override val allAccounts: Flow<List<Account>> = db.accountDao().getAll()
    override suspend fun insert(account: Account) = db.accountDao().insert(account)
}