package com.github.pedroscott.yourbusinesscard.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "card")
data class CardEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    @ColumnInfo(name = "company_name") val companyName: String,
    val phone: String,
    val email: String,
    @ColumnInfo(name = "color_background") val colorBackground: Int,
    @ColumnInfo(name = "color_text") val colorText: Int,
    val logo: String
)