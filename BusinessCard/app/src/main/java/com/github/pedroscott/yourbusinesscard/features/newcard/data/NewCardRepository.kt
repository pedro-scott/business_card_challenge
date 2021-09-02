package com.github.pedroscott.yourbusinesscard.features.newcard.data

import com.github.pedroscott.yourbusinesscard.db.CardDao
import com.github.pedroscott.yourbusinesscard.extension.toCardEntity
import com.github.pedroscott.yourbusinesscard.model.Card
import com.github.pedroscott.yourbusinesscard.model.CardViewParams

class NewCardRepository(
    private val cardDao: CardDao
) {

    suspend fun createCard(cardViewParams: CardViewParams) = cardDao.createCard(cardViewParams.toCardEntity())
    suspend fun updateCard(card: Card) =
        cardDao.updateCard(card.toCardEntity())

}