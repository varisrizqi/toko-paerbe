package com.tipiz.toko_paerbe.ui.bottomnav.store.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.ItemOnboardingBinding

class DetailSectionsPagerLayout(private val imageProduct: List<String>) :
    RecyclerView.Adapter<DetailSectionsPagerLayout.DetailSectionsPagerLayoutViewHolder>() {


    inner class DetailSectionsPagerLayoutViewHolder(private val binding: ItemOnboardingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(imageUrl: String) {

            Glide.with(itemView.context)
                .load(imageUrl)
                .fitCenter()
                .apply(
                    RequestOptions.placeholderOf(R.drawable.img_alert).error(R.drawable.img_alert)
                )
                .into(binding.ivItemOnboard)
        }


    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailSectionsPagerLayoutViewHolder {
        val binding =
            ItemOnboardingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailSectionsPagerLayoutViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DetailSectionsPagerLayoutViewHolder, position: Int) {
        val image = imageProduct[position]
        holder.bind(image)

    }

    override fun getItemCount(): Int = imageProduct.size
}