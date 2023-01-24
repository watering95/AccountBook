package com.example.accountbook.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Value(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "idAccount") val idAccount: Int,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "price") val price: Int
)
