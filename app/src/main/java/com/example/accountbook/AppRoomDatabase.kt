package com.example.accountbook

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.accountbook.dao.*
import com.example.accountbook.data.*
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [Transaction::class, Account::class, CreditCard::class, Group::class, Category::class,AccountLog::class],
//  views = [],
    version = 1, exportSchema = false
)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun groupDao(): GroupDao
    abstract fun transactionDao(): TransactionDao
    abstract fun accountDao(): AccountDao
    abstract fun creditCardDao(): CreditCardDao
    abstract fun categoryDao(): CategoryDao
    abstract fun accountLogDao(): AccountLogDao

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

