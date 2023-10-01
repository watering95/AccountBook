package com.example.accountbook.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tbl_Card")
data class Card(
    @PrimaryKey(autoGenerate = true) val uid: Int = 0,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "company") var company: String,
    @ColumnInfo(name = "number") var number: String,
    @ColumnInfo(name = "idAccount") var idAccount: Int,
    @ColumnInfo(name = "idPayment") val idPayment: Int,
    @ColumnInfo(name = "use") val use: Boolean
)
