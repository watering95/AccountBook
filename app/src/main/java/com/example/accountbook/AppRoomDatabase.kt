package com.example.accountbook

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.accountbook.dao.*
import com.example.accountbook.data.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

//@Database(entities = [Transaction::class, Account::class, Card::class, Group::class, Value::class, Category::class, Payment::class], version = 1)
@Database(entities = [Group::class], version = 1, exportSchema = false)
abstract class AppRoomDatabase : RoomDatabase() {
    abstract fun groupDao(): GroupDao

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

