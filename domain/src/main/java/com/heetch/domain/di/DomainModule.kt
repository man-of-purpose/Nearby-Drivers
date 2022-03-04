package com.heetch.domain.di

import org.koin.core.module.Module
import org.koin.dsl.module

val domainModule: List<Module> = mutableListOf<Module>().apply {
    module {
        //TODO: add use case modules
    }
}