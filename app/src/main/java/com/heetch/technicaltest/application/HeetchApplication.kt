package com.heetch.technicaltest.application

import android.app.Application
import com.heetch.data.di.dataModule
import com.heetch.domain.di.domainModule
import com.heetch.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.error.KoinAppAlreadyStartedException
import org.koin.core.module.Module
import org.koin.dsl.module
import timber.log.Timber

class HeetchApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        try {
            startKoin {
                androidContext(applicationContext)
                val modules = mutableListOf<Module>().apply {
                    module {
                        single { this@HeetchApplication }
                    }
                    addAll(dataModule)
                    addAll(domainModule)
                    addAll(presentationModule)
                }
                modules(modules)
            }
        } catch (error: KoinAppAlreadyStartedException) {
            Timber.e(error.localizedMessage)
        }
    }

}