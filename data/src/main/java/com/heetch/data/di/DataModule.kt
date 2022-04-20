package com.heetch.data.di

import com.google.gson.GsonBuilder
import com.heetch.data.BuildConfig
import com.heetch.data.api.drivers.DriversApiInterface
import com.heetch.data.location.LocationManager
import com.heetch.data.Constants
import com.heetch.data.repository.drivers.AddressRepositoryImpl
import com.heetch.data.repository.drivers.DriversRepositoryImpl
import com.heetch.domain.repository.drivers.AddressRepository
import com.heetch.domain.repository.drivers.DriversRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinApiExtension
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import java.util.concurrent.TimeUnit

@OptIn(KoinApiExtension::class)
val dataModule: Module = module(override = true) {

    // Location modules
    single { LocationManager(androidContext()) }

    // Networking modules
    single {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(10, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    single {
        val gson = GsonBuilder()
            .serializeNulls()
            .create()
        val callerAdapterFactory = RxJava2CallAdapterFactory.create()

        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .addCallAdapterFactory(callerAdapterFactory)
            .client(get())
            .build()
    }

    // API modules
    single<DriversApiInterface> {
        get<Retrofit>().create()
    }

    // Repository modules
    single<DriversRepository> {
        DriversRepositoryImpl(get(), get())
    }
    single<AddressRepository> {
        AddressRepositoryImpl(get())
    }

}