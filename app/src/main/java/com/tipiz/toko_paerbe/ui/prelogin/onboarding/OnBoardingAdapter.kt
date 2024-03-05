package com.tipiz.toko_paerbe.ui.prelogin.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.ItemOnboardingBinding

class OnBoardingAdapter : RecyclerView.Adapter<OnBoardingAdapter.ViewHolder>() {

    private val listImage = listOf(
        R.drawable.onboard1,
        R.drawable.onboard2,
        R.drawable.onboard3,
    )

    inner class ViewHolder(binding: ItemOnboardingBinding) : RecyclerView.ViewHolder(binding.root) {
        val image = binding.ivItemOnboard
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemOnboardingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = listImage.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.image.setImageResource(listImage[position])
    }
}