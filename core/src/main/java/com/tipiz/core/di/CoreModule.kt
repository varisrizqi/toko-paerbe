package com.tipiz.core.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.tipiz.core.domain.repository.onboardingrepo.OnBoardingRepository
import com.tipiz.core.domain.repository.onboardingrepo.OnBoardingRepositoryImpl
import com.tipiz.core.local.datasource.DataStoreDataSource
import com.tipiz.core.local.pref.PrefDatastore
import com.tipiz.core.utils.Constant.PrefDatastore.PREFS_NAME
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.dsl.module

object CoreModule {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFS_NAME)

    private val dataStoreModule = module {
        single { androidContext().dataStore }
        single { PrefDatastore(get()) }
    }



    private val dataSourceModule = module {
        single { DataStoreDataSource(get()) }
    }

    private val repositoryModule = module {
        single<OnBoardingRepository> { OnBoardingRepositoryImpl(get()) }
    }


    val modules: List<Module> = listOf(
        dataStoreModule,
        dataSourceModule,
        repositoryModule

    )


}