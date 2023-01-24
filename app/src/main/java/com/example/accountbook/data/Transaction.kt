package com.example.accountbook.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Transaction(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "idAccount") val idAccount: Int,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "idClassA") val idClassA: Int,
    @ColumnInfo(name = "idClassB") val idClassB: Int,
    @ColumnInfo(name = "idClassC") val idClassC: Int?
)
