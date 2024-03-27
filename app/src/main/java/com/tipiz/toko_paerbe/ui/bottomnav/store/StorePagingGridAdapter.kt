package com.tipiz.toko_paerbe.ui.bottomnav.store

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tipiz.core.domain.model.products.DataProduct
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.ItemProductGridBinding
import com.tipiz.toko_paerbe.ui.utils.currency

class StorePagingGridAdapter(private val listener: OnAdapterListener) :
    PagingDataAdapter<DataProduct, StorePagingGridAdapter.ProductHolder>(DIFF_CALLBACK) {

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataProduct>() {
            override fun areItemsTheSame(oldItem: DataProduct, newItem: DataProduct): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: DataProduct, newItem: DataProduct): Boolean {
                return oldItem == newItem
            }
        }


    }

    class ProductHolder(private val binding: ItemProductGridBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataProduct, context: Context) {
            Glide.with(itemView.context)
                .load(item.image)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.thumbnail_load_product).error(R.drawable.thumbnail_load_product)
                )
                .into(binding.imgStore)


            binding.tvTitle.text = item.productName
            binding.tvPrice.text = currency(item.productPrice)
            binding.tvStore.text = item.store
            binding.tvRating.text = context.getString(R.string.sold)
                .replace("%5.0%", item.productRating.toString())
                .replace("%10%", item.sale.toString())
        }
    }


    override fun onBindViewHolder(holder: ProductHolder, position: Int) {

        val user = getItem(position)

        user?.let { holder.bind(it, holder.itemView.context) }

        holder.itemView.setOnClickListener {
            user?.let { it1 -> listener.onClick(it1) }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolder {
        val binding = ItemProductGridBinding.inflate(LayoutInflater.from(parent.context))
        return ProductHolder(binding)
    }

    interface OnAdapterListener {
        fun onClick(store: DataProduct)
    }

}