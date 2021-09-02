package com.github.pedroscott.yourbusinesscard.di

import com.github.pedroscott.yourbusinesscard.features.main.presentation.MainViewModel
import com.github.pedroscott.yourbusinesscard.features.newcard.presentation.NewCardFragment
import com.github.pedroscott.yourbusinesscard.features.newcard.presentation.NewCardViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object AppModule {

    val viewModelModules = module {

        viewModel { MainViewModel(get()) }
        viewModel { NewCardViewModel(get()) }

    }

}