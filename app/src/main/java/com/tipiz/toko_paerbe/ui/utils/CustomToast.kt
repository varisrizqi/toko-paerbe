package com.tipiz.toko_paerbe.ui.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.core.view.isVisible
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.CustomToastBinding

object CustomToast {

    @SuppressLint("RestrictedApi")
    fun showSnackBar(context: Context, root: View, text: String){
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = CustomToastBinding.inflate(inflater)
        val snackBar = Snackbar.make(root, "", Snackbar.LENGTH_INDEFINITE)

        val snackbarLayout = snackBar.view as Snackbar.SnackbarLayout
        binding.tvSnackbar.text = text

        snackbarLayout.apply {
            //apply untuk merubah value (gravity jd top)
            //run/with -> set listener dll
            val lp = layoutParams as FrameLayout.LayoutParams
            lp.gravity = Gravity.TOP
            layoutParams = lp
        }

        snackbarLayout.addView(binding.root,0)

        snackBar.apply {
            view.setBackgroundColor(Color.TRANSPARENT)
            animationMode = BaseTransientBottomBar.ANIMATION_MODE_FADE

            val slideDownAnim = AnimationUtils.loadAnimation(context, R.anim.slide_down)
            val slideUpAnim = AnimationUtils.loadAnimation(context, R.anim.slide_up)

            view.startAnimation(slideDownAnim)

            slideDownAnim.setAnimationListener(object : Animation.AnimationListener{
                override fun onAnimationStart(p0: Animation?) {}

                override fun onAnimationEnd(p0: Animation?) {
                    view.startAnimation(slideUpAnim)
                }

                override fun onAnimationRepeat(p0: Animation?) {}
            })

            slideUpAnim.setAnimationListener(object : Animation.AnimationListener{
                override fun onAnimationStart(p0: Animation?) {}

                override fun onAnimationEnd(p0: Animation?) {
                    view.isVisible = false
                    dismiss()
                }

                override fun onAnimationRepeat(p0: Animation?) {}

            })
            show()
        }
    }
}