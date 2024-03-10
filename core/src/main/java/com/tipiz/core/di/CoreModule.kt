package com.tipiz.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.tipiz.core.domain.repository.prefrepo.PrefRepository
import com.tipiz.core.domain.repository.prefrepo.PrefRepositoryImpl
import com.tipiz.core.domain.usecase.PrefInteractor
import com.tipiz.core.domain.usecase.PrefUseCase
import com.tipiz.core.local.datasource.DataStoreDataSource
import com.tipiz.core.local.pref.PrefDataStoreHelper
import com.tipiz.core.local.pref.PrefDatastore
import com.tipiz.core.network.retrofit.ApiService
import com.tipiz.core.network.retrofit.NetworkClient
import com.tipiz.core.network.retrofit.interceptor.AuthInterceptor
import com.tipiz.core.network.retrofit.interceptor.SessionInterceptor
import com.tipiz.core.network.retrofit.interceptor.TokenInterceptor
import com.tipiz.core.utils.Constant.PrefDatastore.PREFS_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

object CoreModule {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFS_NAME)

    private val dataStoreModule = module {
        single { androidContext().dataStore }
        single<PrefDataStoreHelper> { PrefDatastore(get()) }
    }

    private val dataSourceModule = module {
        single { DataStoreDataSource(get()) }
    }

    private val repositoryModule = module {
        single<PrefRepository> { PrefRepositoryImpl(get()) }
    }

    private val useCase = module {
        single<PrefUseCase> { PrefInteractor(get()) }
    }

    private val networkModules = module {
        single { AuthInterceptor(get()) }
        single { SessionInterceptor(get()) }
        single { TokenInterceptor(get(), get()) }
        single { ChuckerInterceptor.Builder(androidContext()).build() }
        single { NetworkClient(get(), get(), get(), get()) }
        single<ApiService> { get<NetworkClient>().create() }
    }


    val modules: List<Module> = listOf(
        dataStoreModule,
        dataSourceModule,
        repositoryModule,
        useCase,
        networkModules

    )


}