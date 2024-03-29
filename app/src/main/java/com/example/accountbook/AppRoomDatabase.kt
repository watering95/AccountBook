package com.example.accountbook

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.accountbook.dao.*
import com.example.accountbook.data.*
import kotlinx.coroutines.CoroutineScope

@Database(entities = [Transaction::class, Account::class, Card::class, Group::class, Value::class, Category::class, Payment::class], version = 1, exportSchema = false)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun groupDao(): GroupDao
    abstract fun transactionDao(): TransactionDao
    abstract fun accountDao(): AccountDao
    abstract fun cardDao(): CardDao
    abstract fun valueDao(): ValueDao
    abstract fun categoryDao(): CategoryDao
    abstract fun PaymentDao(): PaymentDao

    companion object {
        @Volatile
        private var instance: AppRoomDatabase? = null

        fun getInstance(context: Context, scope: CoroutineScope): AppRoomDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context, scope).also {
                    instance = it
                }
            }
        }

        private fun buildDatabase(context: Context, scope: CoroutineScope): AppRoomDatabase {
            return Room.databaseBuilder(context, AppRoomDatabase::class.java, "AccountBook.db")
                .build()
        }
    }
}

