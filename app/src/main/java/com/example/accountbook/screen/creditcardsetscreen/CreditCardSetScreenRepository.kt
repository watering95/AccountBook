package com.example.accountbook.screen.creditcardsetscreen

import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.data.Account
import com.example.accountbook.data.CreditCard
import com.example.accountbook.repository.Repository
import kotlinx.coroutines.flow.Flow

interface CreditCardSetScreenRepository: Repository<CreditCard> {
    val allCreditCards: Flow<List<CreditCard>>
    val allAccounts: Flow<List<Account>>

    suspend fun getAccount(id: Long): Account
}
class CreditCardSetScreenRepositoryImpl(private val db : AppRoomDatabase):
    CreditCardSetScreenRepository {
    override val allCreditCards: Flow<List<CreditCard>> = db.creditCardDao().getAll()
    override val allAccounts: Flow<List<Account>> = db.accountDao().getAll()
    override suspend fun insert(item: CreditCard) = db.creditCardDao().insert(item)
    override suspend fun update(item: CreditCard) = db.creditCardDao().update(item)
    override suspend fun delete(item: CreditCard) = db.creditCardDao().delete(item)
    override suspend fun delete(array: Array<CreditCard>) = db.creditCardDao().delete(*array)
    override fun getAll(): Flow<List<CreditCard>> {
        TODO("Not yet implemented")
    }

    override suspend fun getAccount(id: Long) = db.accountDao().get(id)
}