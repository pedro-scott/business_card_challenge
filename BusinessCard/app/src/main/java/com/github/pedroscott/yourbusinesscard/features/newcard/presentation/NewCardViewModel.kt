package com.github.pedroscott.yourbusinesscard.features.newcard.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.pedroscott.yourbusinesscard.features.newcard.domain.NewCardUseCase
import com.github.pedroscott.yourbusinesscard.model.Card
import com.github.pedroscott.yourbusinesscard.model.CardViewParams
import kotlinx.coroutines.launch

class NewCardViewModel(
    private val newCardUseCase: NewCardUseCase
) : ViewModel() {

    fun createCard(cardViewParams: CardViewParams) {
        viewModelScope.launch {
            newCardUseCase.createCard(cardViewParams)
        }
    }

    fun updateCard(card: Card) {
        viewModelScope.launch {
            newCardUseCase.updateCard(card)
        }
    }

}