package com.tipiz.toko_paerbe.di

import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.tipiz.toko_paerbe.firebase.SetupFirebaseMessaging
import com.tipiz.toko_paerbe.ui.bottomnav.cart.CartViewModel
import com.tipiz.toko_paerbe.ui.bottomnav.transaction.TransactionViewModel
import com.tipiz.toko_paerbe.ui.bottomnav.checkout.CheckoutViewModel
import com.tipiz.toko_paerbe.ui.bottomnav.dashboard.DashBoardViewModel
import com.tipiz.toko_paerbe.ui.bottomnav.home.HomeViewModel
import com.tipiz.toko_paerbe.ui.bottomnav.notification.NotificationViewModel
import com.tipiz.toko_paerbe.ui.bottomnav.store.StoreViewModel
import com.tipiz.toko_paerbe.ui.bottomnav.status.StatusViewModel
import com.tipiz.toko_paerbe.ui.bottomnav.store.bottomsheet.BottomSheetViewModel
import com.tipiz.toko_paerbe.ui.bottomnav.store.detail.DetailViewModel
import com.tipiz.toko_paerbe.ui.bottomnav.wishlist.WishlistViewModel
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
        viewModelOf(::CartViewModel)
        viewModelOf(::CheckoutViewModel)
        viewModelOf(::NotificationViewModel)
        viewModelOf(::StatusViewModel)
        viewModelOf(::TransactionViewModel)
    }

    private val firebaseModule = module {
        single { Firebase.analytics }
        single { Firebase.remoteConfig }
        single { FirebaseMessaging.getInstance() }
        single { SetupFirebaseMessaging() }
    }
    val modules: List<Module> = listOf(
        viewModelModule,
        firebaseModule
    )
}