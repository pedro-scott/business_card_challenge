package com.github.pedroscott.yourbusinesscard.di

import com.github.pedroscott.yourbusinesscard.di.AppModule.viewModelModules
import com.github.pedroscott.yourbusinesscard.di.DataModule.repositoryModules
import com.github.pedroscott.yourbusinesscard.di.DomainModule.useCaseModules
import org.koin.core.module.Module

object AppComponent {

    fun getAllModules() : List<Module> = listOf(
        *getViewModelModules(),
        *getDomainModules(),
        *getDataModules()
    )

    private fun getViewModelModules() : Array<Module> = arrayOf(viewModelModules)
    private fun getDomainModules() : Array<Module> = arrayOf(useCaseModules)
    private fun getDataModules() : Array<Module> = arrayOf(repositoryModules)

}