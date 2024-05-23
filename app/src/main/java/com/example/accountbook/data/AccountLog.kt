package com.example.accountbook.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

//@DatabaseView("select name AS date, idGroup AS principal, balance AS evaluation from tbl_account ","view_AccountLog")
@Entity(
    tableName = "tbl_AccountLog",
    foreignKeys = [
        ForeignKey(entity = Account::class, parentColumns = ["uid"], childColumns = ["idAccount"], onDelete = ForeignKey.CASCADE)
    ]
)
data class AccountLog(
    @PrimaryKey(autoGenerate = true) val uId: Long = 0,
    @ColumnInfo(name = "idAccount") val idAccount: Long,
    @ColumnInfo(name = "date") val date:String,
    @ColumnInfo(name = "balance") val balance:Double,
    @ColumnInfo(name = "evaluation") val evaluation:Double)
