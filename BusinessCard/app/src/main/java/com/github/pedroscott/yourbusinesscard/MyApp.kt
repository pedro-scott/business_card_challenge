package com.github.pedroscott.yourbusinesscard

import android.app.Application
import com.github.pedroscott.yourbusinesscard.db.AppDataBase
import com.github.pedroscott.yourbusinesscard.di.AppComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp : Application() {

    companion object {
        lateinit var instance: MyApp
    }

    val database by lazy {
        AppDataBase.getDatabase(this)
    }

    override fun onCreate() {
        super.onCreate()

        instance = this

        startKoin {
            androidLogger()
            androidContext(this@MyApp)

            modules(AppComponent.getAllModules())
        }
    }

}