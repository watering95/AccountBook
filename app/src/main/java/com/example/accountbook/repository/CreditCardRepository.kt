package com.example.accountbook.repository

import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.data.Account
import com.example.accountbook.data.CreditCard
import kotlinx.coroutines.flow.Flow

interface CreditCardRepository {
    val allCreditCards: Flow<List<CreditCard>>
    val allAccounts: Flow<List<Account>>
    suspend fun insert(creditCard: CreditCard)
    suspend fun update(creditCard: CreditCard)
    suspend fun delete(creditCard: CreditCard)
    suspend fun delete(array: Array<CreditCard>)
    suspend fun getAccount(id: Long): Account
}
class CreditCardRepositoryImpl(private val db : AppRoomDatabase): CreditCardRepository {
    override val allCreditCards: Flow<List<CreditCard>> = db.creditCardDao().getAll()
    override val allAccounts: Flow<List<Account>> = db.accountDao().getAll()
    override suspend fun insert(creditCard: CreditCard) = db.creditCardDao().insert(creditCard)
    override suspend fun update(creditCard: CreditCard) = db.creditCardDao().update(creditCard)
    override suspend fun delete(creditCard: CreditCard) = db.creditCardDao().delete(creditCard)
    override suspend fun delete(array: Array<CreditCard>) = db.creditCardDao().delete(*array)
    override suspend fun getAccount(id: Long) = db.accountDao().get(id)
}