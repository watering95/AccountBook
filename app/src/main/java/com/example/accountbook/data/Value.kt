package com.example.accountbook.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_Value")
data class Value(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "idAccount") val idAccount: Int,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "price") val price: Int
)
