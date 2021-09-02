package com.github.pedroscott.yourbusinesscard.di

import com.github.pedroscott.yourbusinesscard.MyApp
import com.github.pedroscott.yourbusinesscard.features.main.data.MainRepository
import com.github.pedroscott.yourbusinesscard.features.newcard.data.NewCardRepository
import org.koin.dsl.module

object DataModule {

    val repositoryModules = module {

        single { MyApp.instance.database.cardDao() }

        single { MainRepository(get()) }
        single { NewCardRepository(get()) }

    }

}