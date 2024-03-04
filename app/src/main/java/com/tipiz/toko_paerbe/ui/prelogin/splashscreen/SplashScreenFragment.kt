package com.tipiz.toko_paerbe.ui.prelogin.splashscreen

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.tipiz.toko_paerbe.databinding.FragmentSplashScreenBinding
import com.tipiz.toko_paerbe.ui.utils.BaseFragment
import com.tipiz.toko_paerbe.ui.utils.Constant.ANIMATION_DELAY
import com.tipiz.toko_paerbe.ui.utils.Constant.ANIMATION_START
import com.tipiz.toko_paerbe.ui.utils.Constant.GREEN_TRANSLATION_Y
import com.tipiz.toko_paerbe.ui.utils.Constant.RED_ROTATION
import com.tipiz.toko_paerbe.ui.utils.Constant.RED_TRANSLATION_X
import com.tipiz.toko_paerbe.ui.utils.Constant.RED_TRANSLATION_Y
import com.tipiz.toko_paerbe.ui.utils.Constant.YELLOW_ROTATION
import com.tipiz.toko_paerbe.ui.utils.Constant.YELLOW_TRANSLATION_X
import com.tipiz.toko_paerbe.ui.utils.Constant.YELLOW_TRANSLATION_Y
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

@SuppressLint("CustomSplashScreen")
class SplashScreenFragment :
    BaseFragment<FragmentSplashScreenBinding, SplashViewModel>(FragmentSplashScreenBinding::inflate) {
    override val viewModel: SplashViewModel by viewModel() //ktx

    override fun initView() {
        animation()
        lifecycleScope.launch {
            delay(ANIMATION_DELAY)
        }

    }

    override fun initViewModel() {

    }

    private fun animation() {
        val yellowAnimator = ObjectAnimator.ofFloat(
            binding.splashScreenYellow,
            View.ROTATION,
            ANIMATION_START,
            YELLOW_ROTATION
        )
        val redAnimator = ObjectAnimator.ofFloat(
            binding.splashScreenRed,
            View.ROTATION,
            ANIMATION_START,
            RED_ROTATION,
        )
        val redAnimatorTrans = ObjectAnimator.ofFloat(
            binding.splashScreenRed,
            View.TRANSLATION_X,
            ANIMATION_START,
            RED_TRANSLATION_X
        )
        val yellowAnimatorTrans = ObjectAnimator.ofFloat(
            binding.splashScreenYellow,
            View.TRANSLATION_X,
            ANIMATION_START,
            YELLOW_TRANSLATION_X
        )
        val redAnimatorTrans2 = ObjectAnimator.ofFloat(
            binding.splashScreenRed,
            View.TRANSLATION_Y,
            ANIMATION_START,
            RED_TRANSLATION_Y
        )
        val yellowAnimatorTrans2 = ObjectAnimator.ofFloat(
            binding.splashScreenYellow,
            View.TRANSLATION_Y,
            ANIMATION_START,
            YELLOW_TRANSLATION_Y
        )
        val greenAnimator = ObjectAnimator.ofFloat(
            binding.splashScreenGreen,
            View.TRANSLATION_Y,
            ANIMATION_START,
            GREEN_TRANSLATION_Y
        )

        val animatorSet = AnimatorSet()
        animatorSet.playTogether(
            yellowAnimator,
            yellowAnimatorTrans,
            yellowAnimatorTrans2,
            redAnimator,
            redAnimatorTrans,
            redAnimatorTrans2,
            greenAnimator
        )
        animatorSet.duration = ANIMATION_DELAY
        animatorSet.start()
    }


}