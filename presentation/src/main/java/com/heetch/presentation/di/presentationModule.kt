package com.heetch.presentation.di

import com.heetch.presentation.features.drivers.DriversListViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.component.KoinApiExtension
import org.koin.core.module.Module
import org.koin.dsl.module

@OptIn(KoinApiExtension::class)
val presentationModule: Module = module(override = true) {

    // ViewModel modules
    viewModel { DriversListViewModel(get(), get()) }

}