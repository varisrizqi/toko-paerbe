package com.tipiz.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.tipiz.core.domain.repository.TokoRepository
import com.tipiz.core.domain.repository.TokoRepositoryImpl
import com.tipiz.core.domain.usecase.TokoInteractor
import com.tipiz.core.domain.usecase.TokoUseCase
import com.tipiz.core.data.local.datasource.LocalDataStore
import com.tipiz.core.data.local.datastore.PrefDataStoreHelper
import com.tipiz.core.data.local.datastore.PrefDatastore
import com.tipiz.core.data.network.datasource.RemoteDataSource
import com.tipiz.core.data.network.retrofit.ApiService
import com.tipiz.core.data.network.retrofit.NetworkClient
import com.tipiz.core.data.network.retrofit.interceptor.AuthInterceptor
import com.tipiz.core.data.network.retrofit.interceptor.SessionInterceptor
import com.tipiz.core.data.network.retrofit.interceptor.TokenInterceptor
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
        single { LocalDataStore(get()) }
        single { RemoteDataSource(get()) }

    }

    private val repositoryModule = module {
        single<TokoRepository> { TokoRepositoryImpl(get(), get()) }
    }

    private val useCase = module {
        single<TokoUseCase> { TokoInteractor(get()) }
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