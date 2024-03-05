package com.tipiz.toko_paerbe.di

import com.tipiz.toko_paerbe.ui.prelogin.splashscreen.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module

object AppModules {

    private val viewModelModule = module {
        viewModelOf(::SplashViewModel)
    }

    val modules: List<Module> = listOf(
        viewModelModule
    )
}