package com.github.pedroscott.yourbusinesscard.features.main.domain

import com.github.pedroscott.yourbusinesscard.extension.toCard
import com.github.pedroscott.yourbusinesscard.features.main.data.MainRepository
import com.github.pedroscott.yourbusinesscard.model.Card

class MainUseCase(
    private val mainRepository: MainRepository
) {

    suspend fun updateCardsList() : List<Card> = mainRepository.updateCardsList().map { it.toCard() }.sortedByDescending { it.id }

    suspend fun deleteCard(id: Long) = mainRepository.deleteCard(id)

    suspend fun searchCards(companyName: String) : List<Card> = mainRepository.searchCards(companyName).map { it.toCard() }.sortedByDescending { it.id }

}