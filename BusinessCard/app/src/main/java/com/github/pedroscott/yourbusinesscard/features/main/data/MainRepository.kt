package com.github.pedroscott.yourbusinesscard.features.main.data

import com.github.pedroscott.yourbusinesscard.db.CardDao
import com.github.pedroscott.yourbusinesscard.db.CardEntity

class MainRepository(private val cardDao: CardDao) {

     suspend fun updateCardsList(): List<CardEntity> = cardDao.updateCardsList()

     suspend fun searchCards(companyName: String): List<CardEntity> = cardDao.searchCards(companyName)

     suspend fun deleteCard(id: Long) = cardDao.deleteCard(id)
    
}