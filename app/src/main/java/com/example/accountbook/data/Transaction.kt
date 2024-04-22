package com.example.accountbook.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "tbl_Transaction",
    foreignKeys = [
        ForeignKey(entity = Account::class, parentColumns = ["uid"], childColumns = ["idAccount"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = Category::class, parentColumns = ["uid"], childColumns = ["idCategory"], onDelete = ForeignKey.CASCADE),
        ForeignKey(entity = CreditCard::class, parentColumns = ["uid"], childColumns = ["idCreditCard"], onDelete = ForeignKey.CASCADE)
    ]
)
data class Transaction(
    @PrimaryKey(autoGenerate = true) val uid: Long = 0,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "idAccount") val idAccount: Long,
    @ColumnInfo(name = "amount") val amount: Double,
    @ColumnInfo(name = "content") val content: String?,
    @ColumnInfo(name = "idCategory") val idCategory: Long,
    @ColumnInfo(name = "idCreditCard") val idCreditCard: Long?
)
