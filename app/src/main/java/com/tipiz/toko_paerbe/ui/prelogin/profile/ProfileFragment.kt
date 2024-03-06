package com.tipiz.toko_paerbe.ui.prelogin.profile

import com.tipiz.toko_paerbe.databinding.FragmentProfileBinding
import com.tipiz.toko_paerbe.ui.prelogin.splashscreen.SplashViewModel
import com.tipiz.toko_paerbe.ui.utils.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileFragment : BaseFragment<FragmentProfileBinding, SplashViewModel>(FragmentProfileBinding::inflate){
    override val viewModel: SplashViewModel by viewModel()

    override fun initView() {

    }

    override fun initViewModel() {

    }
}