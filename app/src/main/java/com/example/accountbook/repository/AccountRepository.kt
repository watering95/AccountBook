package com.example.accountbook.repository

import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.data.Account
import com.example.accountbook.data.Group
import kotlinx.coroutines.flow.Flow

interface AccountRepository {
    val allAccounts: Flow<List<Account>>
    val allGroups: Flow<List<Group>>
    suspend fun insert(account: Account)
    suspend fun update(account: Account)
    suspend fun delete(account: Account)
    suspend fun delete(array: Array<Account>)
    suspend fun getGroup(id: Int): Group
}
class AccountRepositoryImpl(private val db : AppRoomDatabase): AccountRepository {
    override val allAccounts: Flow<List<Account>> = db.accountDao().getAll()
    override val allGroups: Flow<List<Group>> = db.groupDao().getAll()
    override suspend fun insert(account: Account) = db.accountDao().insert(account)
    override suspend fun update(account: Account) = db.accountDao().update(account)
    override suspend fun delete(account: Account) = db.accountDao().delete(account)
    override suspend fun delete(array: Array<Account>) = db.accountDao().delete(*array)
    override suspend fun getGroup(id: Int) = db.groupDao().get(id)
}