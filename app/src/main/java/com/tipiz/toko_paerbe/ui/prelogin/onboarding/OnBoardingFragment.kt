package com.tipiz.toko_paerbe.ui.prelogin.onboarding

import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.FragmentOnBoardingBinding
import com.tipiz.toko_paerbe.ui.prelogin.splashscreen.SplashViewModel
import com.tipiz.toko_paerbe.ui.utils.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel


class OnBoardingFragment :
    BaseFragment<FragmentOnBoardingBinding, SplashViewModel>(FragmentOnBoardingBinding::inflate) {

    override val viewModel: SplashViewModel by viewModel()

    override fun initView() {
        val adapter = OnBoardingAdapter()
        binding.vp2Onboard.adapter = adapter

        TabLayoutMediator(binding.tlOnboard, binding.vp2Onboard) { _, _ -> }.attach()
        viewModel.setOnBoarding(true)
        binding.vp2Onboard.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.btnOnboardNext.visibility =
                    if (position == adapter.itemCount - 1) View.GONE else View.VISIBLE
            }
        })

        val textNext = getString(R.string.next)
        binding.btnOnboardNext.text = textNext
        binding.btnOnboardNext.setOnClickListener {
            binding.vp2Onboard.currentItem += 1
        }

        val textJoin = getString(R.string.join)
        binding.btnOnboardJoin.text = textJoin
        binding.btnOnboardJoin.setOnClickListener {
//            viewModel.setOnBoarding(true)
            findNavController().navigate(R.id.action_onBoardingFragment_to_registerFragment)
        }

        val textSkip = getString(R.string.skip)
        binding.btnOnboardSkip.text = textSkip
        binding.btnOnboardSkip.setOnClickListener {
            findNavController().navigate(R.id.action_onBoardingFragment_to_loginFragment)


        }


    }

    override fun initViewModel() {
//        viewModel.setOnBoarding(true)
    }
}