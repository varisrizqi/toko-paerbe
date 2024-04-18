package com.tipiz.toko_paerbe.ui.bottomnav.store.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.LoadStateItemBinding

class LoadStateAdapterProduct : LoadStateAdapter<LoadStateAdapterProduct.LoadStateViewHolder>() {
    class LoadStateViewHolder(parent: ViewGroup) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.load_state_item, parent, false)
    ) {
        private val binding = LoadStateItemBinding.bind(itemView)
        private val progressBar: ProgressBar = binding.pbLoadStateItem

        fun bind(loadState: LoadState) {
            progressBar.isVisible = loadState is LoadState.Loading
        }

    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(parent)
    }
}