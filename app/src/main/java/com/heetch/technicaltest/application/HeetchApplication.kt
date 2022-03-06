package com.heetch.technicaltest.application

import android.app.Application
import com.heetch.data.di.dataModule
import com.heetch.domain.di.domainModule
import com.heetch.presentation.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class HeetchApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@HeetchApplication)
            val appModule = module {
                single { this@HeetchApplication }
            }
            modules(listOf(appModule, dataModule, domainModule, presentationModule))
        }
    }

}