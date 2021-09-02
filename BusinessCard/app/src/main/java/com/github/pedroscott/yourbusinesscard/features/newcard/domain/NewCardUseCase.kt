package com.github.pedroscott.yourbusinesscard.features.newcard.domain

import com.github.pedroscott.yourbusinesscard.features.newcard.data.NewCardRepository
import com.github.pedroscott.yourbusinesscard.model.Card
import com.github.pedroscott.yourbusinesscard.model.CardViewParams

class NewCardUseCase(
    private val newCardRepository: NewCardRepository
) {

    suspend fun createCard(cardViewParams: CardViewParams) = newCardRepository.createCard(cardViewParams)
    suspend fun updateCard(card: Card) = newCardRepository.updateCard(card)

}