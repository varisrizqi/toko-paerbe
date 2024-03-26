package com.tipiz.toko_paerbe.ui.bottomnav.home

import androidx.navigation.fragment.findNavController
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.FragmentHomeBinding
import com.tipiz.toko_paerbe.ui.utils.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment :
    BaseFragment<FragmentHomeBinding, HomeViewModel>(FragmentHomeBinding::inflate) {
    override val viewModel: HomeViewModel by viewModel()

    override fun initView() {

        binding.btnLogout.text = getString(R.string.logout)
        binding.btnLogout.setOnClickListener {
            viewModel.clearSession()
            activity?.supportFragmentManager?.findFragmentById(R.id.container_main_nav_host)
                ?.findNavController()?.navigate(R.id.action_dashBoardFragment_to_loginFragment)
        }

    }

    override fun initViewModel() {

    }
}