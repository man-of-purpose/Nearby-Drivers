package com.heetch.domain.di

import com.heetch.domain.usecase.drivers.GetNearbyDriversUseCase
import org.koin.core.component.KoinApiExtension
import org.koin.core.module.Module
import org.koin.dsl.module

@OptIn(KoinApiExtension::class)
val domainModule: Module = module(override = true) {

    // UseCase modules
    single { GetNearbyDriversUseCase(get()) }

}