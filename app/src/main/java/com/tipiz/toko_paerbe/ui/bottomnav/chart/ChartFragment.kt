package com.tipiz.toko_paerbe.ui.bottomnav.chart

import com.tipiz.toko_paerbe.databinding.FragmentChartBinding
import com.tipiz.toko_paerbe.ui.utils.BaseFragmentBottomNav
import org.koin.androidx.viewmodel.ext.android.viewModel

class ChartFragment : BaseFragmentBottomNav<FragmentChartBinding,ChartViewModel>(FragmentChartBinding::inflate) {
    override val viewModel: ChartViewModel by viewModel()

    override fun initView() {
        TODO("Not yet implemented")
    }

    override fun initViewModel() {
        TODO("Not yet implemented")
    }
}