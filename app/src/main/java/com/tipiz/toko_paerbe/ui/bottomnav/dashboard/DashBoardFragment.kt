package com.tipiz.toko_paerbe.ui.bottomnav.dashboard

import androidx.lifecycle.lifecycleScope
import com.tipiz.toko_paerbe.databinding.FragmentDashBoardBinding
import com.tipiz.toko_paerbe.ui.utils.BaseFragment
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class DashBoardFragment :
    BaseFragment<FragmentDashBoardBinding, DashBoardViewModel>(FragmentDashBoardBinding::inflate) {
    override val viewModel: DashBoardViewModel by viewModel()

    override fun initView() {
        setUi()
    }

    override fun initViewModel() {

    }

    private fun setUi() {
        lifecycleScope.launch {
            val username = viewModel.getUserName()
            binding.toolbar.title = username
        }
    }


}