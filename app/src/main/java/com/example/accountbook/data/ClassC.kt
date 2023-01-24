package com.example.accountbook.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ClassC(
    @PrimaryKey val uid: Int,
    @ColumnInfo(name = "idClassB") val idClassB: Int,
    @ColumnInfo(name = "name") val name : String
)
