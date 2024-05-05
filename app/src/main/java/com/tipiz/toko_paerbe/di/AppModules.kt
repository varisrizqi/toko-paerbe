package com.tipiz.toko_paerbe.di

import com.tipiz.toko_paerbe.ui.bottomnav.dashboard.DashBoardViewModel
import com.tipiz.toko_paerbe.ui.bottomnav.home.HomeViewModel
import com.tipiz.toko_paerbe.ui.bottomnav.store.StoreViewModel
import com.tipiz.toko_paerbe.ui.bottomnav.store.bottomsheet.BottomSheetViewModel
import com.tipiz.toko_paerbe.ui.bottomnav.store.detail.DetailViewModel
import com.tipiz.toko_paerbe.ui.bottomnav.wishlist.WishlistViewModel
import com.tipiz.toko_paerbe.ui.bottomnav.chart.ChartViewModel
import com.tipiz.toko_paerbe.ui.prelogin.login.LoginViewModel
import com.tipiz.toko_paerbe.ui.prelogin.profile.ProfileViewModel
import com.tipiz.toko_paerbe.ui.prelogin.register.RegisterViewModel
import com.tipiz.toko_paerbe.ui.prelogin.splashscreen.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.Module
import org.koin.dsl.module


object AppModules {

    private val viewModelModule = module {
        viewModelOf(::RegisterViewModel)
        viewModelOf(::SplashViewModel)
        viewModelOf(::LoginViewModel)
        viewModelOf(::DashBoardViewModel)
        viewModelOf(::ProfileViewModel)
        viewModelOf(::HomeViewModel)
        viewModelOf(::StoreViewModel)
        viewModelOf(::WishlistViewModel)
        viewModelOf(::DetailViewModel)
        viewModelOf(::BottomSheetViewModel)
        viewModelOf(::ChartViewModel)

    }
    val modules: List<Module> = listOf(
        viewModelModule
    )
}