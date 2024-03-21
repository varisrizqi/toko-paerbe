package com.tipiz.toko_paerbe.di

import com.tipiz.toko_paerbe.ui.prelogin.register.RegisterViewModel
import com.tipiz.toko_paerbe.ui.prelogin.login.LoginViewModel
import com.tipiz.toko_paerbe.ui.prelogin.splashscreen.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module


object AppModules {

    private val viewModelModule = module {
        viewModelOf(::RegisterViewModel)
        viewModelOf(::SplashViewModel)
        viewModelOf(::LoginViewModel)

    }
    val modules: List<Module> = listOf(
        viewModelModule
    )
}