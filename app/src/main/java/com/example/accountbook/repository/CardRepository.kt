package com.example.accountbook.repository

import com.example.accountbook.AppRoomDatabase
import com.example.accountbook.data.Account
import com.example.accountbook.data.Card
import com.example.accountbook.data.Group
import kotlinx.coroutines.flow.Flow

interface CardRepository {
    val allCards: Flow<List<Card>>
    val allAccounts: Flow<List<Account>>
    suspend fun insert(card: Card)
    suspend fun update(card: Card)
    suspend fun delete(card: Card)
    suspend fun delete(array: Array<Card>)
    suspend fun getAccount(id: Int): Account
}
class CardRepositoryImpl(private val db : AppRoomDatabase): CardRepository {
    override val allCards: Flow<List<Card>> = db.cardDao().getAll()
    override val allAccounts: Flow<List<Account>> = db.accountDao().getAll()
    override suspend fun insert(card: Card) = db.cardDao().insert(card)
    override suspend fun update(card: Card) = db.cardDao().update(card)
    override suspend fun delete(card: Card) = db.cardDao().delete(card)
    override suspend fun delete(array: Array<Card>) = db.cardDao().delete(*array)
    override suspend fun getAccount(id: Int) = db.accountDao().get(id)
}