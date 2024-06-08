package com.example.accountbook.screen.accountsetscreen

import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.data.Account
import com.example.accountbook.data.Group
import com.example.accountbook.data.AccountLog
import com.example.accountbook.repository.Repository
import kotlinx.coroutines.flow.Flow

interface AccountSetScreenRepository: Repository<Account> {
    val allAccounts: Flow<List<Account>>
    val allGroups: Flow<List<Group>>
    val allAccountLog: Flow<List<AccountLog>>
    suspend fun getGroup(id: Long): Group

}

class AccountSetScreenRepositoryImpl(private val db : AppRoomDatabase): AccountSetScreenRepository {
    override val allAccounts: Flow<List<Account>> = db.accountDao().getAll()
    override val allGroups: Flow<List<Group>> = db.groupDao().getAll()
    override val allAccountLog: Flow<List<AccountLog>> = db.accountLogDao().getAll()

    override suspend fun getGroup(id: Long) = db.groupDao().get(id)

    override suspend fun insert(item: Account) = db.accountDao().insert(item)

    override suspend fun update(item: Account)  = db.accountDao().update(item)

    override suspend fun delete(array: Array<Account>) = db.accountDao().delete(*array)
    override fun getAll() =db.accountDao().getAll()

    override suspend fun delete(item: Account) =  db.accountDao().delete(item)
}