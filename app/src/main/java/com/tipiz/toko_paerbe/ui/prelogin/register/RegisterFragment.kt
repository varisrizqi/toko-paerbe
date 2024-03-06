package com.tipiz.toko_paerbe.ui.prelogin.register

import com.tipiz.toko_paerbe.databinding.FragmentRegisterBinding
import com.tipiz.toko_paerbe.ui.prelogin.splashscreen.SplashViewModel
import com.tipiz.toko_paerbe.ui.utils.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterFragment:BaseFragment<FragmentRegisterBinding, SplashViewModel>(FragmentRegisterBinding::inflate){
    override val viewModel: SplashViewModel by viewModel()

    override fun initView() {

    }

    override fun initViewModel() {

    }
}