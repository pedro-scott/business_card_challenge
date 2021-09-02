package com.github.pedroscott.yourbusinesscard.features.main.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.pedroscott.yourbusinesscard.features.main.domain.MainUseCase
import com.github.pedroscott.yourbusinesscard.model.Card
import kotlinx.coroutines.launch

class MainViewModel(
    private val mainUseCase: MainUseCase
) : ViewModel() {

    private val _onSuccessUpdateCardsList = MutableLiveData<List<Card>>()
    val onSuccessUpdateCardsList: LiveData<List<Card>> get() = _onSuccessUpdateCardsList

    private val _onSuccessSearchCards = MutableLiveData<List<Card>>()
    val onSuccessSearchCards: LiveData<List<Card>> get() = _onSuccessSearchCards

    fun updateCardsList() {
        viewModelScope.launch {
            mainUseCase.updateCardsList().let { cardList ->
                _onSuccessUpdateCardsList.postValue(cardList)
            }
        }
    }

    fun deleteCard(id: Long) {
        viewModelScope.launch {
            mainUseCase.deleteCard(id)
        }
    }

    fun searchCards(companyName: String) {
        viewModelScope.launch {
            mainUseCase.searchCards(companyName).let { cardList ->
                _onSuccessSearchCards.postValue(cardList)
            }
        }
    }

}