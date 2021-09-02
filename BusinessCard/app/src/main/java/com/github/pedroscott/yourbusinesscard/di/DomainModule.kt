package com.github.pedroscott.yourbusinesscard.di

import com.github.pedroscott.yourbusinesscard.features.main.domain.MainUseCase
import com.github.pedroscott.yourbusinesscard.features.newcard.domain.NewCardUseCase
import org.koin.dsl.module

object DomainModule {

    val useCaseModules = module {

        single { MainUseCase(get()) }
        single { NewCardUseCase(get()) }

    }

}