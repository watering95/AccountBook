package com.example.accountbook.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_Transaction")
data class Transaction(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "price") val price: Int,
    @ColumnInfo(name = "idPayment") val idPayment: Int,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "idCategory") val idCategory: Int
)
