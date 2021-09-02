package com.github.pedroscott.yourbusinesscard.db

import androidx.room.*

@Dao
interface CardDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun createCard(card: CardEntity)

    @Query(value = "SELECT * FROM card")
    suspend fun updateCardsList() : List<CardEntity>

    @Query(value = "SELECT * FROM card WHERE company_name LIKE :companyName")
    suspend fun searchCards(companyName: String) : List<CardEntity>

    @Update
    suspend fun updateCard(card: CardEntity)

    @Query(value = "DELETE FROM card WHERE id = :id")
    suspend fun deleteCard(id: Long)

}