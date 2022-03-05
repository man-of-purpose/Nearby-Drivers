package com.heetch.data.di

import com.heetch.data.api.drivers.DriversApiInterface
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.create

val dataModule: List<Module> = mutableListOf<Module>().apply {
    module {
        single<DriversApiInterface>{
            get<Retrofit>().create()
        }
    }
}