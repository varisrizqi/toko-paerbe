package com.tipiz.toko_paerbe.ui.bottomnav.store.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.tipiz.core.domain.model.products.DataProduct
import com.tipiz.toko_paerbe.R
import com.tipiz.toko_paerbe.databinding.ItemProductGridBinding
import com.tipiz.toko_paerbe.databinding.ItemProductLinearBinding
import com.tipiz.toko_paerbe.ui.utils.currency

class ProductPagingAdapter(private val listener: OnPagingListener) :
    PagingDataAdapter<DataProduct, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    private var isGridLayout: Boolean = false
    fun setLayoutType(isGrid: Boolean) {
        isGridLayout = isGrid
    }

    inner class ViewHolderGrid(private val binding: ItemProductGridBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataProduct,context: Context) {
            with(binding) {
                cvProductGrid.startAnimation(
                    AnimationUtils.loadAnimation(
                        itemView.context,
                        R.anim.anim_one
                    )
                )
                tvItemGridProductName.text = data.productName
                tvItemGridPrice.text = currency(data.productPrice)
                tvItemGridStoreName.text = data.store
                binding.tvItemGridRatingSold.text = context.getString(R.string.sold)
                    .replace("%5.0%", data.productRating.toString())
                    .replace("%10%", data.sale.toString())
                Glide.with(context)
                    .load(data.image)
                    .placeholder(R.drawable.thumbnail_load_product)
                    .error(R.drawable.thumbnail_load_product)
                    .into(ivItemGridImg)

            }
        }
    }

    inner class ViewHolderLinear(private val binding: ItemProductLinearBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataProduct,context: Context) {
            with(binding) {
                cvProductLinear.startAnimation(
                    AnimationUtils.loadAnimation(
                        itemView.context,
                        R.anim.anim_one
                    )
                )
                tvItemLinearProductName.text = data.productName
                tvItemLinearPrice.text = currency(data.productPrice)
                tvItemLinearStoreName.text = data.store
                tvItemLinearRatingSold.text = context.getString(R.string.sold)
                    .replace("%5.0%", data.productRating.toString())
                    .replace("%10%", data.sale.toString())
                Glide.with(context)
                    .load(data.image)
                    .placeholder(R.drawable.thumbnail_load_product)
                    .error(R.drawable.thumbnail_load_product)
                    .into(ivItemLinearImg)
            }
        }
    }

//    private lateinit var onItemClickCallback: OnItemClickCallback
//
//    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
//        this.onItemClickCallback = onItemClickCallback
//    }
//
//    interface OnItemClickCallback {
//        fun onItemClicked(data: DataProduct)
//    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (isGridLayout) {
            false -> {
                val binding = ItemProductLinearBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ViewHolderLinear(binding)
            }

            true -> {
                val binding = ItemProductGridBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
                ViewHolderGrid(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)
        when (isGridLayout) {
            false -> {
                val viewHolderLinear = holder as ViewHolderLinear
//                viewHolderLinear.itemView.setOnClickListener {
//                    if (data != null) {
//                        onItemClickCallback.onItemClicked(
//                            data
//                        )
//                    }
//                }
//                if (data != null) {
//                    viewHolderLinear.bind(data)
//                }
                viewHolderLinear.itemView.setOnClickListener {
                    data?.let { it1 -> listener.onClick(it1) }
                }

                data?.let { holder.bind(it, holder.itemView.context) }



            }

            true -> {
                val viewHolderGrid = holder as ViewHolderGrid
                viewHolderGrid.itemView.setOnClickListener {
                    data?.let { it1 -> listener.onClick(it1) }
                }
                data?.let { holder.bind(it, holder.itemView.context) }
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataProduct>() {
            override fun areItemsTheSame(
                oldItem: DataProduct,
                newItem: DataProduct
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataProduct,
                newItem: DataProduct
            ): Boolean {
                return oldItem.productId == newItem.productId
            }
        }
    }

    interface OnPagingListener {
        fun onClick(store: DataProduct)
    }
}
