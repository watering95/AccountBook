package com.example.accountbook.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_Card")
data class Card(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "company") val company: String,
    @ColumnInfo(name = "number") val number: String,
    @ColumnInfo(name = "idAccount") val idAccount: Int,
    @ColumnInfo(name = "idPayment") val idPayment: Int,
    @ColumnInfo(name = "use") val use: Boolean
)
