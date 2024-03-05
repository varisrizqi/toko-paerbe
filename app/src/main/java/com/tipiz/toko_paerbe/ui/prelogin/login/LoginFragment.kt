package com.tipiz.toko_paerbe.ui.prelogin.login

import com.tipiz.toko_paerbe.databinding.FragmentLoginBinding
import com.tipiz.toko_paerbe.ui.prelogin.splashscreen.SplashViewModel
import com.tipiz.toko_paerbe.ui.utils.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginFragment :BaseFragment<FragmentLoginBinding, SplashViewModel>(FragmentLoginBinding::inflate){
    override val viewModel: SplashViewModel by viewModel()

    override fun initView() {

    }

    override fun initViewModel() {

    }
}