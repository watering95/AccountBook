package com.example.accountbook

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.accountbook.dao.*
import com.example.accountbook.data.*
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Transaction::class, Account::class, Card::class, Group::class, Value::class, Category::class, Payment::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao
    abstract fun accountDao(): AccountDao
    abstract fun cardDao(): CardDao
    abstract fun groupDao(): GroupDao
    abstract fun valueDao(): ValueDao
    abstract fun categoryDao(): CategoryDao
    abstract fun paymentDao(): PaymentDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, "AccountBook.db")
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}