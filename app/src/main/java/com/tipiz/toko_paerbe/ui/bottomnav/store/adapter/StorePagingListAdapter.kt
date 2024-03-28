package com.tipiz.toko_paerbe.ui.bottomnav.store.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tipiz.core.domain.model.products.DataProduct
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.ItemProductLinearBinding
import com.tipiz.toko_paerbe.ui.utils.currency

class StorePagingListAdapter(private val listener: OnAdapterListener) :
    PagingDataAdapter<DataProduct, StorePagingListAdapter.ProductHolderLinear>(DIFF_CALLBACK) {

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


    inner class ProductHolderLinear(private val binding: ItemProductLinearBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DataProduct, context: Context) {
            with(binding) {
                Glide.with(itemView.context)
                    .load(item.image)
                    .apply(
                        RequestOptions.placeholderOf(R.drawable.thumbnail_load_product)
                            .error(R.drawable.thumbnail_load_product)
                    )
                    .into(ivItemLinearImg)

                cvProductLinear.startAnimation(
                    AnimationUtils.loadAnimation(
                        itemView.context,
                        R.anim.anim_one
                    )
                )
                tvItemLinearProductName.text = item.productName
                tvItemLinearPrice.text = currency(item.productPrice)
                tvItemLinearStoreName.text = item.store
                tvItemLinearRatingSold.text = context.getString(R.string.sold)
                    .replace("%5.0%", item.productRating.toString())
                    .replace("%10%", item.sale.toString())
            }
        }
    }


    override fun onBindViewHolder(holder: ProductHolderLinear, position: Int) {

        val user = getItem(position)

        user?.let { holder.bind(it, holder.itemView.context) }

        holder.itemView.setOnClickListener {
            user?.let { it1 -> listener.onClick(it1) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductHolderLinear {
        val binding = ItemProductLinearBinding.inflate(LayoutInflater.from(parent.context))
        return ProductHolderLinear(binding)

    }

    interface OnAdapterListener {
        fun onClick(store: DataProduct)
    }

}