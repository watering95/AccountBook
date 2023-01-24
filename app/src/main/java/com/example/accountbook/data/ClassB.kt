package com.example.accountbook.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ClassB(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "idClassA") val idClassA: Int,
    @ColumnInfo(name = "name") val name: String
)
